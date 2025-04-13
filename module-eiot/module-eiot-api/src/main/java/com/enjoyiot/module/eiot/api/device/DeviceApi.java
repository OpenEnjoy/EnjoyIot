
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
import com.enjoyiot.framework.common.pojo.CommonResult;
import com.enjoyiot.module.eiot.api.device.dto.*;

import java.util.Map;

public interface DeviceApi {

    DeviceInfo getDeviceByPkDnByCache(String pk, String dn);

    DeviceInfo getDeviceInfoFromCache(Long deviceId);

    DeviceInfo registerDevice(RegisterDevice registerDevice);

    CommonResult<DeviceInfo> auth(DeviceAuth deviceAuth);

    Map<String, DevicePropertyCache> getPropertiesFromCache(Long deviceId);

    void updateDeviceLastTimeCache(Long deviceId, long lastTime);

    Boolean updateDeviceState(Long deviceId, boolean online);

    void savePropertiesCache(Long deviceId, Map<String, DevicePropertyCache> properties);

    void clearPropertiesCache(String productKey);

    DeviceConfig getDeviceConfig(Long deviceId);

    /**
     * 调用设备服务
     *
     * @param service 服务
     */
    void invoke(ThingService<?> service);
}
