/*
 * Copyright [2025] [Enjoy-iot]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.enjoyiot.module.eiot.service.shadow;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.dal.dataobject.shadow.DeviceShadowDO;
import com.enjoyiot.module.eiot.dal.mysql.shadow.DeviceShadowMapper;
import com.enjoyiot.module.eiot.dal.redis.RedisKeyConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.enjoyiot.module.eiot.api.enums.ErrorCodeConstants.*;

/**
 * 设备影子 Service 实现类
 *
 * @author EnjoyIot
 */
@Service
@Validated
@Slf4j
public class DeviceShadowServiceImpl implements DeviceShadowService {

    @Resource
    private DeviceShadowMapper deviceShadowMapper;

    @Resource
    private DeviceApi deviceApi;

    @Resource
    private org.springframework.cache.CacheManager cacheManager;


    @Override
    @Cacheable(cacheNames = RedisKeyConstants.DEVICE_SHADOW, key = "#deviceId", unless = "#result == null")
    public DeviceShadowDO getByDeviceId(Long deviceId) {
        return deviceShadowMapper.selectByDeviceId(deviceId);
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeviceShadowDO createShadow(Long deviceId) {
        DeviceShadowDO shadow = deviceShadowMapper.selectByDeviceId(deviceId);
        if (shadow != null) {
            return shadow;
        }

        // 获取设备信息
        DeviceInfo deviceInfo = deviceApi.getDeviceInfoFromCache(deviceId);
        if (deviceInfo == null) {
            throw ServiceExceptionUtil.exception(DEVICE_NOT_EXISTS);
        }

        // 创建影子
        shadow = new DeviceShadowDO();
        shadow.setDeviceId(deviceId);
        shadow.setProductKey(deviceInfo.getProductKey());
        shadow.setDn(deviceInfo.getDn());
        shadow.setDesired("{}");
        shadow.setReported("{}");
        shadow.setMetadata("{}");
        shadow.setVersion(0L);
        shadow.setTenantId(deviceInfo.getTenantId());
        
        deviceShadowMapper.insert(shadow);
        log.info("创建设备影子成功: deviceId={}, tenantId={}", deviceId, deviceInfo.getTenantId());

        return shadow;
    }

    @CacheEvict(cacheNames = RedisKeyConstants.DEVICE_SHADOW, key = "#deviceId")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDesired(Long deviceId, Map<String, Object> desired, Long version) {
        // 查询数据库
        DeviceShadowDO shadow = deviceShadowMapper.selectByDeviceId(deviceId);
        if (shadow == null) {
            throw ServiceExceptionUtil.exception(DEVICE_SHADOW_NOT_EXISTS);
        }

        // 版本校验：传入的 version 必须 >= 当前版本
        if (version != null && version < shadow.getVersion()) {
            throw ServiceExceptionUtil.exception(DEVICE_SHADOW_VERSION_CONFLICT);
        }

        // 直接使用传入的期望状态（完全替换，不合并）
        Map<String, Object> newDesired = desired != null ? desired : new HashMap<>();

        // 重建元数据（时间戳，单位：毫秒）
        Map<String, Object> metadata = parseJson(shadow.getMetadata());
        Map<String, Object> desiredMetadata = new HashMap<>();
        long timestamp = System.currentTimeMillis();
        for (String key : newDesired.keySet()) {
            Map<String, Object> propertyMeta = new HashMap<>();
            propertyMeta.put("timestamp", timestamp);
            desiredMetadata.put(key, propertyMeta);
        }
        metadata.put("desired", desiredMetadata);

        // 计算新版本号
        Long newVersion = version != null ? version : shadow.getVersion() + 1;

        // 构建更新对象
        DeviceShadowDO updateShadow = new DeviceShadowDO();
        updateShadow.setId(shadow.getId());
        updateShadow.setDesired(JSONUtil.toJsonStr(newDesired));
        updateShadow.setMetadata(JSONUtil.toJsonStr(metadata));
        updateShadow.setVersion(newVersion);
        updateShadow.setLastDesiredTime(LocalDateTime.now());

        // 使用乐观锁更新：WHERE id = ? AND version < ?
        int updateCount = deviceShadowMapper.updateByIdWithVersion(updateShadow, newVersion);
        if (updateCount == 0) {
            // 更新失败，说明版本冲突
            throw ServiceExceptionUtil.exception(DEVICE_SHADOW_VERSION_CONFLICT);
        }



        log.info("更新设备影子期望状态成功: deviceId={}, oldVersion={}, newVersion={}", 
            deviceId, shadow.getVersion(), newVersion);
    }

    @CacheEvict(cacheNames = RedisKeyConstants.DEVICE_SHADOW, key = "#deviceId")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long updateReported(Long deviceId, Map<String, Object> reported, Long version) {

        DeviceShadowDO shadow = deviceShadowMapper.selectByDeviceId(deviceId);
        
        // 版本校验：如果传入了 version，必须大于当前版本
        if (version != null && version <= shadow.getVersion()) {
            throw ServiceExceptionUtil.exception(DEVICE_SHADOW_VERSION_CONFLICT);
        }

        // 解析当前 reported 状态
        Map<String, Object> currentReported = parseJson(shadow.getReported());
        
        // 合并新的 reported（增量更新）
        if (reported != null) {
            currentReported.putAll(reported);
        }

        // 更新元数据（时间戳，单位：毫秒）
        Map<String, Object> metadata = parseJson(shadow.getMetadata());
        Map<String, Object> reportedMetadata = (Map<String, Object>) metadata.getOrDefault("reported", new HashMap<>());
        long timestamp = System.currentTimeMillis();
        
        // 只更新本次上报的属性的元数据
        if (reported != null) {
            for (String key : reported.keySet()) {
                Map<String, Object> propertyMeta = new HashMap<>();
                propertyMeta.put("timestamp", timestamp);
                reportedMetadata.put(key, propertyMeta);
            }
        }
        metadata.put("reported", reportedMetadata);

        // 计算新版本号
        Long newVersion = version != null ? version : shadow.getVersion() + 1;

        // 构建更新对象
        DeviceShadowDO updateShadow = new DeviceShadowDO();
        updateShadow.setId(shadow.getId());
        updateShadow.setReported(JSONUtil.toJsonStr(currentReported));
        updateShadow.setMetadata(JSONUtil.toJsonStr(metadata));
        updateShadow.setVersion(newVersion);
        updateShadow.setLastReportedTime(LocalDateTime.now());

        // 使用乐观锁更新：WHERE id = ? AND version < ?
        int updateCount = deviceShadowMapper.updateByIdWithVersion(updateShadow, newVersion);
        if (updateCount == 0) {
            // 更新失败，说明版本冲突
            throw ServiceExceptionUtil.exception(DEVICE_SHADOW_VERSION_CONFLICT);
        }


        log.info("设备上报状态成功: deviceId={}, reported={}, oldVersion={}, newVersion={}", 
            deviceId, reported, shadow.getVersion(), newVersion);

        return newVersion;
    }


    @CacheEvict(cacheNames = RedisKeyConstants.DEVICE_SHADOW, key = "#deviceId")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long clearDesired(Long deviceId, Long version) {
        // 查询数据库
        DeviceShadowDO shadow = deviceShadowMapper.selectByDeviceId(deviceId);
        if (shadow == null) {
            throw ServiceExceptionUtil.exception(DEVICE_SHADOW_NOT_EXISTS);
        }

        // 版本校验：如果传入了 version，必须大于当前版本
        if (version != null && version <= shadow.getVersion()) {
            throw ServiceExceptionUtil.exception(DEVICE_SHADOW_VERSION_CONFLICT);
        }

        // 更新元数据
        Map<String, Object> metadata = parseJson(shadow.getMetadata());
        metadata.put("desired", new HashMap<>());

        // 计算新版本号：总是在当前版本基础上 +1
        Long newVersion = shadow.getVersion() + 1;

        // 构建更新对象
        DeviceShadowDO updateShadow = new DeviceShadowDO();
        updateShadow.setId(shadow.getId());
        updateShadow.setDesired("{}");
        updateShadow.setMetadata(JSONUtil.toJsonStr(metadata));
        updateShadow.setVersion(newVersion);
        updateShadow.setLastDesiredTime(LocalDateTime.now());

        // 使用乐观锁更新：WHERE id = ? AND version < ?
        int updateCount = deviceShadowMapper.updateByIdWithVersion(updateShadow, newVersion);
        if (updateCount == 0) {
            // 更新失败，说明版本冲突
            throw ServiceExceptionUtil.exception(DEVICE_SHADOW_VERSION_CONFLICT);
        }


        return newVersion;
    }

    @CacheEvict(cacheNames = RedisKeyConstants.DEVICE_SHADOW, key = "#deviceId")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long clearShadow(Long deviceId) {
        // 查询数据库
        DeviceShadowDO shadow = deviceShadowMapper.selectByDeviceId(deviceId);
        if (shadow == null) {
            throw ServiceExceptionUtil.exception(DEVICE_SHADOW_NOT_EXISTS);
        }

        // 清空 desired 和 reported
        shadow.setDesired("{}");
        shadow.setReported("{}");
        
        // 清空元数据
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("desired", new HashMap<>());
        metadata.put("reported", new HashMap<>());
        shadow.setMetadata(JSONUtil.toJsonStr(metadata));
        
        // 版本重置为 0
        shadow.setVersion(0L);
        shadow.setLastDesiredTime(LocalDateTime.now());
        shadow.setLastReportedTime(LocalDateTime.now());

        deviceShadowMapper.updateById(shadow);

        
        log.info("清空设备影子数据成功: deviceId={}, version=0", deviceId);

        return 0L;
    }

    @Override
    public Map<String, Object> getDelta(Long deviceId) {
        DeviceShadowDO shadow = deviceShadowMapper.selectByDeviceId(deviceId);
        if (shadow == null) {
            return new HashMap<>();
        }

        Map<String, Object> desired = parseJson(shadow.getDesired());
        Map<String, Object> reported = parseJson(shadow.getReported());

        // 计算差异：期望状态中存在但与上报状态不同的属性
        Map<String, Object> delta = new HashMap<>();
        for (Map.Entry<String, Object> entry : desired.entrySet()) {
            String key = entry.getKey();
            Object desiredValue = entry.getValue();
            Object reportedValue = reported.get(key);

            if (!ObjectUtil.equal(desiredValue, reportedValue)) {
                delta.put(key, desiredValue);
            }
        }

        return delta;
    }

    @CacheEvict(cacheNames = RedisKeyConstants.DEVICE_SHADOW, key = "#deviceId")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long deleteReportedProperties(Long deviceId, java.util.List<String> keys, Long version) {
        DeviceShadowDO shadow = deviceShadowMapper.selectByDeviceId(deviceId);
        
        // 版本校验：如果传入了 version，必须大于当前版本
        if (version != null && version <= shadow.getVersion()) {
            throw ServiceExceptionUtil.exception(DEVICE_SHADOW_VERSION_CONFLICT);
        }

        // 解析当前 reported 状态
        Map<String, Object> currentReported = parseJson(shadow.getReported());
        
        // 删除指定的属性
        if (keys != null && !keys.isEmpty()) {
            for (String key : keys) {
                currentReported.remove(key);
            }
        }

        // 更新元数据（删除对应属性的元数据）
        Map<String, Object> metadata = parseJson(shadow.getMetadata());
        Map<String, Object> reportedMetadata = (Map<String, Object>) metadata.getOrDefault("reported", new HashMap<>());
        
        if (keys != null && !keys.isEmpty()) {
            for (String key : keys) {
                reportedMetadata.remove(key);
            }
        }
        metadata.put("reported", reportedMetadata);

        // 计算新版本号
        Long newVersion = version != null ? version : shadow.getVersion() + 1;

        // 构建更新对象
        DeviceShadowDO updateShadow = new DeviceShadowDO();
        updateShadow.setId(shadow.getId());
        updateShadow.setReported(JSONUtil.toJsonStr(currentReported));
        updateShadow.setMetadata(JSONUtil.toJsonStr(metadata));
        updateShadow.setVersion(newVersion);
        updateShadow.setLastReportedTime(LocalDateTime.now());

        // 使用乐观锁更新：WHERE id = ? AND version < ?
        int updateCount = deviceShadowMapper.updateByIdWithVersion(updateShadow, newVersion);
        if (updateCount == 0) {
            // 更新失败，说明版本冲突
            throw ServiceExceptionUtil.exception(DEVICE_SHADOW_VERSION_CONFLICT);
        }

        log.info("设备删除上报属性成功: deviceId={}, keys={}, oldVersion={}, newVersion={}", 
            deviceId, keys, shadow.getVersion(), newVersion);

        return newVersion;
    }

    @CacheEvict(cacheNames = RedisKeyConstants.DEVICE_SHADOW, key = "#deviceId")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long replaceReported(Long deviceId, Map<String, Object> reported, Long version) {
        DeviceShadowDO shadow = deviceShadowMapper.selectByDeviceId(deviceId);
        
        // 版本校验：如果传入了 version，必须大于当前版本
        if (version != null && version <= shadow.getVersion()) {
            throw ServiceExceptionUtil.exception(DEVICE_SHADOW_VERSION_CONFLICT);
        }

        // 直接使用传入的 reported（完全替换，不合并）
        Map<String, Object> newReported = reported != null ? reported : new HashMap<>();

        // 重建元数据（时间戳，单位：毫秒）
        Map<String, Object> metadata = parseJson(shadow.getMetadata());
        Map<String, Object> reportedMetadata = new HashMap<>();
        long timestamp = System.currentTimeMillis();
        
        for (String key : newReported.keySet()) {
            Map<String, Object> propertyMeta = new HashMap<>();
            propertyMeta.put("timestamp", timestamp);
            reportedMetadata.put(key, propertyMeta);
        }
        metadata.put("reported", reportedMetadata);

        // 计算新版本号
        Long newVersion = version != null ? version : shadow.getVersion() + 1;

        // 构建更新对象
        DeviceShadowDO updateShadow = new DeviceShadowDO();
        updateShadow.setId(shadow.getId());
        updateShadow.setReported(JSONUtil.toJsonStr(newReported));
        updateShadow.setMetadata(JSONUtil.toJsonStr(metadata));
        updateShadow.setVersion(newVersion);
        updateShadow.setLastReportedTime(LocalDateTime.now());

        // 使用乐观锁更新：WHERE id = ? AND version < ?
        int updateCount = deviceShadowMapper.updateByIdWithVersion(updateShadow, newVersion);
        if (updateCount == 0) {
            // 更新失败，说明版本冲突
            throw ServiceExceptionUtil.exception(DEVICE_SHADOW_VERSION_CONFLICT);
        }

        log.info("设备完全替换上报状态成功: deviceId={}, reported={}, oldVersion={}, newVersion={}", 
            deviceId, newReported, shadow.getVersion(), newVersion);

        return newVersion;
    }



    /**
     * 解析JSON字符串为Map
     */
    private Map<String, Object> parseJson(String json) {
        if (json == null || json.trim().isEmpty() || "{}".equals(json)) {
            return new HashMap<>();
        }
        try {
            return JSONUtil.toBean(json, Map.class);
        } catch (Exception e) {
            log.error("解析JSON失败: {}", json, e);
            return new HashMap<>();
        }
    }


}
