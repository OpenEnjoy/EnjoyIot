
/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com | Tel: 19918996474
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot] | Tel: 19918996474
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */
package com.enjoyiot.module.eiot.service.device;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.enjoyiot.framework.common.exception.ServiceException;
import com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.util.object.BeanUtils;
import com.enjoyiot.framework.common.util.validation.ValidationUtils;
import com.enjoyiot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.enjoyiot.framework.tenant.core.aop.TenantIgnore;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.device.dto.DevicePropertyCache;
import com.enjoyiot.module.eiot.api.device.dto.DeviceShortInfo;
import com.enjoyiot.module.eiot.api.device.dto.RegisterDevice;
import com.enjoyiot.module.eiot.api.enums.ErrorCodeConstants;
import com.enjoyiot.module.eiot.api.product.dto.Product;
import com.enjoyiot.module.eiot.controller.admin.device.vo.*;
import com.enjoyiot.module.eiot.controller.admin.device.vo.devicegroup.DeviceImportRespVO;
import com.enjoyiot.module.eiot.convert.DeviceInfoConvert;
import com.enjoyiot.module.eiot.convert.ProductConvert;
import com.enjoyiot.module.eiot.dal.dataobject.deviceinfo.EiotDeviceInfoDO;
import com.enjoyiot.module.eiot.dal.dataobject.product.ProductDO;
import com.enjoyiot.module.eiot.dal.mysql.EiotIotDeviceGroupMapper;
import com.enjoyiot.module.eiot.dal.mysql.deviceinfo.EiotDeviceInfoMapper;
import com.enjoyiot.module.eiot.dal.mysql.product.ProductMapper;
import com.enjoyiot.module.eiot.dal.redis.RedisKeyConstants;
import com.enjoyiot.module.eiot.dal.redis.no.EiotRedisDAO;
import com.enjoyiot.module.eiot.service.product.ProductService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

import static com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil.exception;


/**
 * 设备信息 Service 实现类
 *
 * @author EnjoyIot
 */
@Service
@Validated
public class DeviceInfoServiceImpl implements DeviceInfoService {

    @Resource
    private EiotDeviceInfoMapper deviceInfoMapper;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private EiotIotDeviceGroupMapper deviceGroupMapper;

    @Resource
    private ProductService productService;

    @Resource
    private EiotRedisDAO eiotRedisDAO;

    @Resource
    private CacheManager cacheManager;


    @Override
    public Long createDeviceInfo(DeviceInfoSaveReqVO createReqVO) {

        String productKey = createReqVO.getProductKey();

        ProductDO productDO = productMapper.getByProductKey(productKey);
        if (ObjectUtils.isNull(productDO)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.PRODUCT_NOT_EXISTS);
        }
        // 插入
        EiotDeviceInfoDO deviceInfo = BeanUtils.toBean(createReqVO, EiotDeviceInfoDO.class);
        deviceInfo.setId(IdUtil.getSnowflakeNextId());
        deviceInfo.setNodeType(productDO.getNodeType());
        deviceInfo.setState(DeviceInfo.STATE_NOT_ACTIVE);
        deviceInfo.setTenantId(productDO.getTenantId());
        deviceInfoMapper.insert(deviceInfo);
        // 返回
        return deviceInfo.getId();
    }

    @Override
    @Caching(
            evict = {@CacheEvict(cacheNames = RedisKeyConstants.DEVICE, key = "#updateReqVO.productKey+':'+#updateReqVO.dn")
                    ,
                    @CacheEvict(cacheNames = RedisKeyConstants.DEVICE_ID, key = "#updateReqVO.id")
            }
    )
    public void updateDeviceInfo(@Validated DeviceInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateDeviceInfoExists(updateReqVO.getId());

        ProductDO productDO = productMapper.getByProductKey(updateReqVO.getProductKey());
        if (ObjectUtils.isNull(productDO)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.PRODUCT_NOT_EXISTS);
        }
        // 更新
        EiotDeviceInfoDO updateObj = BeanUtils.toBean(updateReqVO, EiotDeviceInfoDO.class);
        updateObj.setNodeType(productDO.getNodeType());
        deviceInfoMapper.updateById(updateObj);
    }

    @CacheEvict(cacheNames = RedisKeyConstants.DEVICE_ID, key = "#deviceId")
    @Override
    public Boolean deleteDeviceInfo(Long deviceId) {
        EiotDeviceInfoDO deviceInfo = validateDeviceInfoExists(deviceId);
        // 删除
        Boolean ret = deviceInfoMapper.deleteById(deviceId) > 0;
        if (ret) {
            clearCache(RedisKeyConstants.DEVICE, deviceInfo.getProductKey() + ":" + deviceInfo.getDn());
        }
        return ret;
    }

    private void clearCache(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
        }
    }


    @Override
    public DeviceInfo getDeviceInfo(Long id) {
        return DeviceInfoConvert.INSTANCE.convert(deviceInfoMapper.selectById(id));
    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.DEVICE, key = "#pk+':'+#dn",
            unless = "#result == null")
    @TenantIgnore
    public DeviceInfo getDeviceByPkDnByCache(String pk, String dn) {
        return DeviceInfoConvert.INSTANCE.convert(deviceInfoMapper.selectOne(EiotDeviceInfoDO::getProductKey, pk,
                EiotDeviceInfoDO::getDn, dn));
    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.DEVICE_ID, key = "#deviceId",
            unless = "#result == null")
    @TenantIgnore
    public DeviceInfo getDeviceInfoFromCache(Long deviceId) {
        return getDeviceInfo(deviceId);
    }

    @Override
    public Map<String, DevicePropertyCache> getPropertiesFromCache(Long deviceId) {
        return eiotRedisDAO.getProperties(deviceId);
    }

    @Override
    public PageResult<DeviceShortInfo> getDeviceInfoPage(DeviceInfoPageReqVO pageReqVO) {
        return deviceInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public DeviceInfo getDeviceBySerialNo(String serialNumber) {
        return DeviceInfoConvert.INSTANCE.convert(deviceInfoMapper.selectOne(EiotDeviceInfoDO::getSerialNo, serialNumber));
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        deviceInfoMapper.deleteByIds(ids);
        // TODO: 删除缓存
        deviceGroupMapper.deleteByDeviceIds(ids);
        return false;
    }

    @Override
    public DeviceImportRespVO importDevice(List<DeviceInfoImportVo> list, Long productId) {
        // 1.1 参数校验
        if (CollUtil.isEmpty(list)) {
            throw new ServiceException(400, "导入数据不许为空");
        }
        Product product = ProductConvert.INSTANCE.convert(productMapper.selectById(productId));
        if (Objects.isNull(product)) {
            throw new ServiceException(400, "产品不存在");
        }

        // 2. 遍历，逐个创建 or 更新
        DeviceImportRespVO respVO = DeviceImportRespVO.builder().createKey(new ArrayList<>())
                .updateKey(new ArrayList<>()).failureKeyMap(new LinkedHashMap<>()).build();

        list.forEach(o -> {
            DeviceInfoSaveReqVO saveReqVO = BeanUtils.toBean(o, DeviceInfoSaveReqVO.class);
            saveReqVO.setProductKey(product.getProductKey());

            try {
                ValidationUtils.validate(saveReqVO);
            } catch (ConstraintViolationException ex) {
                respVO.getFailureKeyMap().put(saveReqVO.getName(), ex.getMessage());
                return;
            }

            try {
                validateDeviceForCreateOrUpdate(saveReqVO);
            } catch (ConstraintViolationException ex) {
                respVO.getFailureKeyMap().put(saveReqVO.getName(), ex.getMessage());
                return;
            }
            Long deviceId = createDeviceInfo(saveReqVO);
            if (ObjectUtil.isNotNull(deviceId)) {
                respVO.getCreateKey().add(saveReqVO.getName());
            }

        });

        return respVO;
    }

    @Override
    public List<DeviceInfo> findSubDeviceList(String productKey, String deviceName) {
        DeviceInfo deviceParent = getDeviceByPkDnByCache(productKey, deviceName);
        if (ObjectUtil.isNull(deviceParent)) {
            return Collections.EMPTY_LIST;
        }
        LambdaQueryWrapperX<EiotDeviceInfoDO> q = new LambdaQueryWrapperX<EiotDeviceInfoDO>();
        q.eq(EiotDeviceInfoDO::getParentId, deviceParent.getId());
        return DeviceInfoConvert.INSTANCE.convertList(deviceInfoMapper.selectList(q));
    }

    private EiotDeviceInfoDO validateDeviceInfoExists(Long id) {
        if (ObjectUtil.isNull(id)) {
            return null;
        }
        EiotDeviceInfoDO device = deviceInfoMapper.selectById(id);
        if (device == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.DEVICE_INFO_NOT_EXISTS);
        }
        return device;
    }

    public void validateDeviceForCreateOrUpdate(DeviceInfoSaveReqVO saveReqVO) {
        Long id = saveReqVO.getId();
        validateDeviceInfoExists(id);
        validateDeviceDnUnique(id, saveReqVO.getDn(), saveReqVO.getProductKey());
        validateDeviceSerialNo(id, saveReqVO.getSerialNo());
    }

    private void validateDeviceSerialNo(Long id, String serialNo) {
        if (StringUtils.isBlank(serialNo)) {
            return;
        }
        DeviceInfo obj = getDeviceBySerialNo(serialNo);
        if (ObjectUtil.isNotNull(obj) && !obj.getId().equals(id)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.DEVICE_SERIAL_REPEAT);
        }

    }

    private void validateDeviceDnUnique(Long id, String dn, String productKey) {
        if (StringUtils.isBlank(dn)) {
            return;
        }
        DeviceInfo deviceByPkDnByCache = getDeviceByPkDnByCache(productKey, dn);
        if (ObjectUtil.isNotNull(deviceByPkDnByCache) && !deviceByPkDnByCache.getId().equals(id)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.DEVICE_DN_REPEAT);
        }

    }

    @Caching(
            evict = {@CacheEvict(cacheNames = RedisKeyConstants.DEVICE, key = "#saveReqVO.productKey+':'+#saveReqVO.dn")
                    ,
                    @CacheEvict(cacheNames = RedisKeyConstants.DEVICE_ID, key = "#saveReqVO.id")

            }
    )
    @Override
    public void bindParent(@Validated DeviceBindReqVO saveReqVO) {
        LambdaUpdateWrapper<EiotDeviceInfoDO> up = new LambdaUpdateWrapper<>();
        up.eq(EiotDeviceInfoDO::getId, saveReqVO.getId());
        up.set(EiotDeviceInfoDO::getParentId, saveReqVO.getParentId());
        deviceInfoMapper.update(null, up);
    }

    @Override
    public DeviceInfo registerDevice(RegisterDevice registerDevice) {
        String productKey = registerDevice.getProductKey();
        Product product = productService.getByPk(productKey);
        if (Objects.isNull(product)) {
            throw exception(ErrorCodeConstants.PRODUCT_NOT_EXISTS);
        }

        DeviceInfo device = getDeviceByPkDnByCache(productKey, registerDevice.getDeviceName());
        if (ObjectUtil.isNotNull(device)) {
            return device;
        }

        DeviceInfoSaveReqVO deviceInfo = new DeviceInfoSaveReqVO();
        deviceInfo.setProductKey(productKey);
        deviceInfo.setName(registerDevice.getDeviceName());
        deviceInfo.setDn(registerDevice.getDeviceName());
        deviceInfo.setModel(registerDevice.getModel());
        deviceInfo.setParentId(registerDevice.getParentId());
        deviceInfo.setSecret(RandomStringUtils.randomAlphabetic(16));
        deviceInfo.setSerialNo(registerDevice.getDeviceName());

        Long deviceId = createDeviceInfo(deviceInfo);
        return getDeviceInfo(deviceId);
    }

    @Override
    public long getLastTimeCache(Long deviceId) {
        return eiotRedisDAO.getLastTime(deviceId);
    }

    @Override
    public void updateDeviceLastTimeCache(Long deviceId, long lastTime) {
        eiotRedisDAO.setLastTime(deviceId, lastTime);
    }

    @CacheEvict(cacheNames = RedisKeyConstants.DEVICE_ID, key = "#deviceId")
    @Override
    public Boolean updateDeviceState(Long deviceId, boolean online) {
        LambdaUpdateWrapper<EiotDeviceInfoDO> up = new LambdaUpdateWrapper<>();
        up.set(EiotDeviceInfoDO::getState, online ? DeviceInfo.ONLINE : DeviceInfo.OFFLINE);
        if (online) {
            up.set(EiotDeviceInfoDO::getOnlineTime, System.currentTimeMillis());
        } else {
            up.set(EiotDeviceInfoDO::getOfflineTime, System.currentTimeMillis());
        }
        up.eq(EiotDeviceInfoDO::getId, deviceId);
        // TODO: mybatis 拦截器会设置更新时间,所以应该是
        boolean b = deviceInfoMapper.update(null, up) > 0;
        if (b) {
            DeviceInfo deviceInfo = getDeviceInfo(deviceId);
            clearCache(RedisKeyConstants.DEVICE, deviceInfo.getProductKey() + ":" + deviceInfo.getDn());
        }
        return b;
    }

    @Override
    public void savePropertiesCache(Long deviceId, Map<String, DevicePropertyCache> properties) {
        eiotRedisDAO.saveProperties(deviceId, properties);
    }

    @Override
    public void clearPropertiesCache(String productKey) {
        List<EiotDeviceInfoDO> deviceList = deviceInfoMapper.selectList(EiotDeviceInfoDO::getProductKey, productKey);
        if (deviceList != null && !deviceList.isEmpty()) {
            List<Long> deviceIds = deviceList.stream().map(EiotDeviceInfoDO::getId).collect(Collectors.toList());
            eiotRedisDAO.clearProperties(deviceIds);
        }
    }
}
