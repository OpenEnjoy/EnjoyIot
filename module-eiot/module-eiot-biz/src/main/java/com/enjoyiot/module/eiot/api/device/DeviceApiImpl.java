
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
package com.enjoyiot.module.eiot.api.device;

import com.enjoyiot.eiot.common.thing.ThingService;
import com.enjoyiot.framework.tenant.core.util.TenantUtils;
import com.enjoyiot.module.eiot.api.device.dto.DeviceConfig;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.device.dto.DevicePropertyCache;
import com.enjoyiot.module.eiot.api.device.dto.RegisterDevice;
import com.enjoyiot.module.eiot.service.device.DeviceConfigService;
import com.enjoyiot.module.eiot.service.device.DeviceCtrlService;
import com.enjoyiot.module.eiot.service.device.DeviceInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class DeviceApiImpl implements DeviceApi {

    @Resource
    private DeviceInfoService deviceInfoService;

    @Resource
    private DeviceConfigService deviceConfigService;

    @Resource
    private DeviceCtrlService deviceCtrlService;

    @Override
    public DeviceInfo getDeviceByPkDnByCache(String pk, String dn) {
        return TenantUtils.executeIgnoreResult(() -> deviceInfoService.getDeviceByPkDnByCache(pk, dn));
    }

    @Override
    public DeviceInfo getDeviceInfoFromCache(Long deviceId) {
        return TenantUtils.executeIgnoreResult(() -> deviceInfoService.getDeviceInfoFromCache(deviceId));
    }

    @Override
    public DeviceInfo registerDevice(RegisterDevice registerDevice) {
        return TenantUtils.executeIgnoreResult(() -> deviceInfoService.registerDevice(registerDevice));
    }

    @Override
    public Map<String, DevicePropertyCache> getPropertiesFromCache(Long deviceId) {
        return TenantUtils.executeIgnoreResult(() -> deviceInfoService.getPropertiesFromCache(deviceId));
    }

    @Override
    public void updateDeviceLastTimeCache(Long deviceId, long lastTime) {
        TenantUtils.executeIgnore(() -> deviceInfoService.updateDeviceLastTimeCache(deviceId, lastTime));
    }

    @Override
    public Boolean updateDeviceState(Long deviceId, boolean online) {
        return TenantUtils.executeIgnoreWithResult(() -> deviceInfoService.updateDeviceState(deviceId, online));
    }

    @Override
    public void savePropertiesCache(Long deviceId, Map<String, DevicePropertyCache> properties) {
        TenantUtils.executeIgnore(() -> deviceInfoService.savePropertiesCache(deviceId, properties));
    }

    @Override
    public DeviceConfig getDeviceConfig(Long deviceId) {
        return TenantUtils.executeIgnoreResult(() -> deviceConfigService.findByDeviceId(deviceId));
    }

    @Override
    public void invoke(ThingService<?> service) {
//        deviceCtrlService.invokeService(service.getProductKey());
    }
}
