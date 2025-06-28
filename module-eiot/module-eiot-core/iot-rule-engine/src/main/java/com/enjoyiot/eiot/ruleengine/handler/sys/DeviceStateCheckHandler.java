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


import cn.hutool.core.util.IdUtil;
import com.enjoyiot.eiot.common.constant.Constants;
import com.enjoyiot.eiot.common.enums.DeviceState;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.message.core.MqProducer;
import com.enjoyiot.eiot.ruleengine.handler.DeviceMessageHandler;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 设备状态检查
 */
@Slf4j
@Component
public class DeviceStateCheckHandler implements DeviceMessageHandler {

    @Autowired
    private DeviceApi deviceApi;

    @Resource
    private MqProducer<ThingModelMessage> producer;

    @Override
    public void handle(ThingModelMessage msg) {
        // 过滤下行消息
        if (Boolean.TRUE.equals(msg.getToClient())) {
            return;
        }
        String type = msg.getType();

        DeviceInfo device = deviceApi.getDeviceInfoFromCache(msg.getDeviceId());
        if (device == null) {
            return;
        }

        DeviceState state = DeviceState.ONLINE;
        String identifier = msg.getIdentifier();

        // 当
        if (ThingModelMessage.TYPE_STATE.equals(type)) {
            state = StringUtils.equals(identifier, "online") ? DeviceState.ONLINE : DeviceState.OFFLINE;
        }
        if (state != DeviceState.OFFLINE) {
            // 上行消息,不是离线状态,都更新最新消息
            updateDeviceLastTime(device, msg);
        }

        // 其他消息， 发送设备在线物模型消息
        if ((device.isOnline() && state == DeviceState.ONLINE) ||
                (!device.isOnline() && state == DeviceState.OFFLINE)) {
            return;
        }
        log.debug("rule engine state change [{} -> {}] msgId: {}", device.isOnline(), state.isOnline(), msg.getMid());

        Long time = msg.getTime();
        if (time == null) {
            time = System.currentTimeMillis();
        }
        if (!ThingModelMessage.TYPE_STATE.equals(type)) {
            // 如果非状态改变的消息, 为了避免覆盖原消息日志(时序数据库按时间存,相同时间只存一条),所以-1
            time = time - 1;
        }

        Boolean b = deviceApi.updateDeviceState(device.getId(), state.isOnline());
        if(b && !ThingModelMessage.TYPE_STATE.equals(type)){
            sendDeviceStateChangeMessage(device, state.isOnline() ,time);
        }


    }

    private void updateDeviceLastTime(DeviceInfo device, ThingModelMessage msg) {
        Long msgTimeMilli = msg.getTime();
        deviceApi.updateDeviceLastTimeCache(device.getId(), msgTimeMilli);

    }

    private void sendDeviceStateChangeMessage(DeviceInfo device, Boolean online, Long time) {
        // TODO: 提却到某个公共类中
        String stateId =   (online) ? ThingModelMessage.ID_ONLINE : ThingModelMessage.ID_OFFLINE;
        ThingModelMessage msg = ThingModelMessage.builder()
                .id(IdUtil.fastSimpleUUID())
                .time(time)
                .dn(device.getDn())
                .productKey(device.getProductKey())
                .occurred(time)
                .deviceId(device.getId())
                .identifier(stateId)
                .type(ThingModelMessage.TYPE_STATE)
                .build();
        producer.publish(Constants.THING_MODEL_MESSAGE_TOPIC, msg);

    }
}
