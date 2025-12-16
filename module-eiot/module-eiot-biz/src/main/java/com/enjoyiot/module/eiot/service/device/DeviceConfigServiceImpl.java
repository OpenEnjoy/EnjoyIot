
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


import com.enjoyiot.module.eiot.api.device.dto.DeviceConfig;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.dal.dataobject.deviceconfig.DeviceConfigDO;
import com.enjoyiot.module.eiot.dal.mysql.deviceconfig.DeviceConfigMapper;
import com.enjoyiot.framework.common.exception.ServiceException;
import com.enjoyiot.framework.common.util.object.BeanUtils;
import com.enjoyiot.module.eiot.dal.redis.RedisKeyConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.time.ZoneId;


@Service
@RequiredArgsConstructor
public class DeviceConfigServiceImpl implements DeviceConfigService {

    private final DeviceConfigMapper deviceConfigMapper;
    private final DeviceInfoService deviceInfoService;

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.DEVICE_ID_CONFIG, key = "#deviceId", unless = "#result == null")
    public DeviceConfig findByDeviceId(Long deviceId) {
        DeviceInfo deviceInfo = deviceInfoService.getDeviceInfo(deviceId);
        if (deviceInfo == null) {
            return null;
        }
        DeviceConfigDO configDO = deviceConfigMapper.selectByPkDn(deviceInfo.getProductKey(), deviceInfo.getDn());
        if (configDO == null) {
            return null;
        }
        DeviceConfig dto = BeanUtils.toBean(configDO, DeviceConfig.class);
        dto.setDeviceId(deviceId);
        dto.setDeviceName(deviceInfo.getName());
        return dto;
    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.DEVICE_CONFIG, key = "#productKey+':'+#dn", unless = "#result == null")
    public DeviceConfig findByPkDn(String productKey, String dn) {
        DeviceInfo deviceInfo = deviceInfoService.getDeviceByPkDnByCache(productKey, dn);
        if (deviceInfo == null) {
            return null;
        }
        return findByDeviceId(deviceInfo.getId());
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = RedisKeyConstants.DEVICE_ID_CONFIG, key = "#config.deviceId", beforeInvocation = true),
            @CacheEvict(cacheNames = RedisKeyConstants.DEVICE_CONFIG, key = "#config.productKey+':'+#config.deviceName", beforeInvocation = true)
    })
    public Boolean saveConfig(DeviceConfig config) {
        DeviceInfo deviceInfo = deviceInfoService.getDeviceInfo(config.getDeviceId());
        if (deviceInfo == null) {
            throw new ServiceException(400, "设备不存在");
        }
        // 确保产品key/dn写入
        config.setProductKey(deviceInfo.getProductKey());
        config.setDeviceName(deviceInfo.getName());

        DeviceConfigDO exist = deviceConfigMapper.selectByPkDn(deviceInfo.getProductKey(), deviceInfo.getDn());
        DeviceConfigDO target = BeanUtils.toBean(config, DeviceConfigDO.class);
        target.setDn(deviceInfo.getDn());
        target.setProductKey(deviceInfo.getProductKey());
        target.setTenantId(deviceInfo.getTenantId());
        if (exist == null) {
            deviceConfigMapper.insert(target);
        } else {
            target.setId(exist.getId());
            deviceConfigMapper.updateById(target);
        }
        return true;
    }
}
