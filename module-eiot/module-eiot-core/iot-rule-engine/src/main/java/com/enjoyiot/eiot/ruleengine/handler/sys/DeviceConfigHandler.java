/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot]
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
package com.enjoyiot.eiot.ruleengine.handler.sys;


import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.common.thing.ThingService;
import com.enjoyiot.eiot.ruleengine.handler.DeviceMessageHandler;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.device.dto.DeviceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 设备配置服务
 */
@Slf4j
@Service
public class DeviceConfigHandler implements DeviceMessageHandler {

    @Autowired
    private DeviceApi deviceApi;

    @Override
    public void handle(ThingModelMessage msg) {
        String identifier = msg.getIdentifier();
        if (!ThingModelMessage.ID_CONFIG_GET.equals(identifier)) {
            return;
        }
        //收到设备获取配置消息，回复配置信息给设备
        DeviceConfig deviceConfig = deviceApi.getDeviceConfig(msg.getDeviceId());
        if (deviceConfig == null) {
            return;
        }

        Map config = JsonUtils.parseObject(deviceConfig.getConfig(), Map.class);
        ThingService<Object> service = ThingService.builder()
                .productKey(msg.getProductKey())
                .dn(msg.getDn())
                .identifier(ThingModelMessage.ID_CONFIG_GET + "_reply")
                .type(ThingModelMessage.TYPE_CONFIG)
                .mid(msg.getMid())
                .params(config)
                .build();
        //todo 下发
    }
}
