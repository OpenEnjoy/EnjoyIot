
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


import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;

import java.util.List;
import java.util.Map;

/**
 * @Author: EnjoyIot
 * @Date: 2025/1/8 18:42
 * @Version: V1.0
 * @Description: 设备控制接口
 */
public interface DeviceCtrlService {
    void invokeService(Long deviceId, String service,
                         Map<String, Object> args);

    void invokeService(Long deviceId, String service,
                         Map<String, Object> args, boolean checkOwner);

    void otaUpgrade(Long deviceId, boolean checkOwner, Object data);

    void getProperty(Long deviceId, List<String> properties,
                       boolean checkOwner);

    void setProperty(Long deviceId, Map<String, Object> properties);

    void setProperty(Long deviceId, Map<String, Object> properties,
                       boolean checkOwner);

    void sendConfig(Long deviceId, boolean checkOwner);

    void sendConfig(Long deviceId);

    DeviceInfo getAndCheckDevice(Long deviceId, boolean checkOwner);

}
