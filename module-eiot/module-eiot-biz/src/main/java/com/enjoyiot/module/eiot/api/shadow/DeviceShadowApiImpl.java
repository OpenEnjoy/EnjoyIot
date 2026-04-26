/*
 * Copyright [2025] [Enjoy-iot]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.enjoyiot.module.eiot.api.shadow;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.framework.tenant.core.util.TenantUtils;
import com.enjoyiot.module.eiot.api.shadow.dto.DeviceShadowDTO;
import com.enjoyiot.module.eiot.convert.DeviceShadowConvert;
import com.enjoyiot.module.eiot.dal.dataobject.shadow.DeviceShadowDO;
import com.enjoyiot.module.eiot.service.component.ComponentManager;
import com.enjoyiot.module.eiot.service.shadow.DeviceShadowService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备影子 API 实现类
 *
 * @author EnjoyIot
 */
@Slf4j
@Service
public class DeviceShadowApiImpl implements DeviceShadowApi {

    @Resource
    private DeviceShadowService deviceShadowService;

    @Resource
    private ComponentManager componentManager;

    @Override
    public DeviceShadowDTO getByDeviceId(Long deviceId) {
        return TenantUtils.executeIgnoreResult(() -> {
            DeviceShadowDO shadow = deviceShadowService.getByDeviceId(deviceId);
            return DeviceShadowConvert.INSTANCE.convertToDTO(shadow);
        });
    }



    @Override
    public Long updateReported(Long deviceId, Map<String, Object> reported, Long version) {
        return TenantUtils.executeIgnoreResult(() -> 
            deviceShadowService.updateReported(deviceId, reported, version)
        );
    }

    @Override
    public Long deleteReportedProperties(Long deviceId, List<String> keys, Long version) {
        return TenantUtils.executeIgnoreResult(() -> 
            deviceShadowService.deleteReportedProperties(deviceId, keys, version)
        );
    }

    @Override
    public Long replaceReported(Long deviceId, Map<String, Object> reported, Long version) {
        return TenantUtils.executeIgnoreResult(() -> 
            deviceShadowService.replaceReported(deviceId, reported, version)
        );
    }

    @Override
    public Long clearDesired(Long deviceId, Long version) {
        return TenantUtils.executeIgnoreResult(() -> 
            deviceShadowService.clearDesired(deviceId, version)
        );
    }

    @Override
    public Long clearShadow(Long deviceId) {
        return TenantUtils.executeIgnoreResult(() -> 
            deviceShadowService.clearShadow(deviceId)
        );
    }



    @Override
    public void pushControlToDevice(Long deviceId) {
        TenantUtils.executeIgnore(() -> {
            DeviceShadowDO shadow = deviceShadowService.getByDeviceId(deviceId);
            if (shadow == null) {
                log.warn("设备影子不存在，无法推送控制消息: deviceId={}", deviceId);
                return;
            }

            // 解析 desired、reported、metadata
            Map<String, Object> desired = parseJson(shadow.getDesired());
            Map<String, Object> reported = parseJson(shadow.getReported());
            Map<String, Object> metadata = parseJson(shadow.getMetadata());

            // 构建影子数据
            Map<String, Object> shadowData = new HashMap<>();
            shadowData.put("desired", desired);
            shadowData.put("reported", reported);
            shadowData.put("metadata", metadata);
            shadowData.put("version", shadow.getVersion());

            // 构建 ThingModelMessage
            ThingModelMessage message = ThingModelMessage.builder()
                    .id(IdUtil.fastSimpleUUID())
                    .mid(IdUtil.fastSimpleUUID())
                    .deviceId(deviceId)
                    .productKey(shadow.getProductKey())
                    .dn(shadow.getDn())
                    .type(ThingModelMessage.TYPE_SHADOW)
                    .identifier(ThingModelMessage.ID_SHADOW_UPDATE)
                    .data(shadowData)
                    .time(System.currentTimeMillis())
                    .build();

            // 通过 ComponentManager 发送到消息总线
            componentManager.sendToDevice(message);

            log.info("推送影子控制消息到消息总线: pk={}, dn={}, deviceId={}, version={}",
                shadow.getProductKey(), shadow.getDn(), deviceId, shadow.getVersion());
        });
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
