
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
package com.enjoyiot.eiot.component.core;

import cn.hutool.core.util.IdUtil;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.component.core.model.ActionType;
import com.enjoyiot.eiot.common.enums.DeviceState;
import com.enjoyiot.eiot.component.core.model.down.DeviceConfig;
import com.enjoyiot.eiot.component.core.model.down.PropertyGet;
import com.enjoyiot.eiot.component.core.model.down.PropertySet;
import com.enjoyiot.eiot.component.core.model.down.ServiceInvoke;
import com.enjoyiot.eiot.component.core.model.up.*;
import com.enjoyiot.module.eiot.api.component.dto.ComponentInfo;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.enjoyiot.eiot.common.enums.ErrorCodeConstants.PARAMS_EXCEPTION;
import static com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil.exception;

public abstract class ThingComponent extends AbstractComponent {

    private ComponentInfo componentInfo;

    protected ThingComponent(ComponentServices componentServices) {
        super(componentServices);
    }

    @Scheduled(fixedRate = 5000)
    public void config() {
        ComponentInfo info = componentServices.getComponentApi().getInfo(getType());
        if (info == null || info.getConfig() == null) {
            return;
        }
        //非初次启动，状态或配置未变更
        if (componentInfo != null
                && componentInfo.getStatus().equals(info.getStatus())
                && componentInfo.getConfig().equals(info.getConfig())) {
            return;
        }
        //变更调用
        boolean changed = stateChange(info.getStatus() == 1, info.getConfig());
        if (changed) {
            //变更成功
            componentInfo = info;
        }
    }

    /**
     * 组件状态变更
     *
     * @param enable 是否可用
     * @param config 组件配置
     */
    protected abstract boolean stateChange(boolean enable, String config);

    @Override
    public void onMessage(ThingModelMessage message) {
        String type = message.getType();
        switch (type) {
            case ThingModelMessage.TYPE_SERVICE:
                doServiceInvoke(message);
                break;
            case ThingModelMessage.TYPE_PROPERTY:
                doProperty(message);
                break;
            case ThingModelMessage.TYPE_CONFIG:
                doConfig(message);
                break;
            case ThingModelMessage.TYPE_OTA:
                doOta(message);
                break;
        }
    }

    private void doServiceInvoke(ThingModelMessage message) {
        if (!(message.getData() instanceof Map)) {
            throw exception(PARAMS_EXCEPTION);
        }
        serviceInvoke(ServiceInvoke.builder()
                .id(message.getId())
                .name(message.getIdentifier())
                .productKey(message.getProductKey())
                .deviceName(message.getDn())
                .params(message.dataToMap())
                .build());
    }

    private void doProperty(ThingModelMessage message) {
        String identifier = message.getIdentifier();
        if (ThingModelMessage.ID_PROPERTY_GET.equals(identifier)) {
            if (!(message.getData() instanceof List)) {
                throw exception(PARAMS_EXCEPTION);
            }
            //属性获取
            propertyGet(PropertyGet.builder()
                    .id(message.getId())
                    .productKey(message.getProductKey())
                    .deviceName(message.getDn())
                    .keys((List<String>) message.getData())
                    .build());
        } else if (ThingModelMessage.ID_PROPERTY_SET.equals(identifier)) {
            propertySet(PropertySet.builder()
                    .id(message.getId())
                    .productKey(message.getProductKey())
                    .deviceName(message.getDn())
                    .params(message.dataToMap())
                    .build());
        }
    }

    private void doConfig(ThingModelMessage message) {
        deviceConfig(DeviceConfig.builder()
                .id(message.getId())
                .productKey(message.getProductKey())
                .deviceName(message.getDn())
                .config(message.dataToMap())
                .build());
    }

    private void doOta(ThingModelMessage message) {

    }

    protected abstract void serviceInvoke(ServiceInvoke action);

    protected abstract void propertyGet(PropertyGet action);

    protected abstract void propertySet(PropertySet action);

    protected abstract void deviceConfig(DeviceConfig action);

    public void report(ReportAction action) {
        ActionType type = action.getType();
        ThingModelMessage message = null;

        switch (type) {
            case EVENT_REPORT:
                message = getEventReport((EventReport) action);
                break;
            case PROPERTY_REPORT:
                message = getPropertyReport((PropertyReport) action);
                break;
            case REGISTER:
                message = getDeviceRegister((DeviceRegister) action);
                break;
            case STATE_CHANGE:
                message = getStateChange((DeviceStateChange) action);
                break;
            case SERVICE_REPLY:
                message = getServiceReply((ServiceReply) action);
                break;
            case SUB_REGISTER:
                message = getSubDeviceRegister((SubDeviceRegister) action);
                break;
            default:
                throw exception(PARAMS_EXCEPTION);
        }

        if (!StringUtils.hasText(action.getId())) {
            message.setId(IdUtil.fastSimpleUUID());
        }else {
            message.setId(action.getId());
        }
        if (ObjectUtils.isEmpty(action.getTime())) {
            message.setTime(System.currentTimeMillis());
        }else {
            message.setTime(action.getTime());
        }
        message.setMid(message.getId());
        message.setDn(action.getDeviceName());
        message.setProductKey(action.getProductKey());
        message.setOccurred(message.getTime());
        //填充设备id
        DeviceInfo deviceInfo = componentServices.getDeviceApi().getDeviceByPkDnByCache(action.getProductKey(), action.getDeviceName());
        message.setDeviceId(deviceInfo.getId());
        sendMessage(message);
    }

    private ThingModelMessage getEventReport(EventReport action) {
        return ThingModelMessage.builder()
                .data(action.getParams())
                .identifier(action.getName())
                .type(ThingModelMessage.TYPE_EVENT)
                .build();
    }

    private ThingModelMessage getPropertyReport(PropertyReport action) {
        return ThingModelMessage.builder()
                .data(action.getParams())
                .identifier(ThingModelMessage.ID_PROPERTY_REPORT)
                .type(ThingModelMessage.TYPE_PROPERTY)
                .build();
    }

    private ThingModelMessage getDeviceRegister(DeviceRegister action) {
        Map<String, Object> data = new HashMap<>();
        data.put("model", action.getModel());
        data.put("version", action.getVersion());
        return ThingModelMessage.builder()
                .data(data)
                .identifier(ThingModelMessage.ID_REGISTER)
                .type(ThingModelMessage.TYPE_LIFETIME)
                .build();
    }

    private ThingModelMessage getSubDeviceRegister(SubDeviceRegister action) {
        Map<String, Object> data = new HashMap<>();
        data.put("model", action.getModel());
        data.put("version", action.getVersion());

        List<Map<String, Object>> subs = new ArrayList<>();
        for (DeviceRegister sub : action.getSubs()) {
            Map<String, Object> subData = new HashMap<>();
            subData.put("model", sub.getModel());
            subData.put("version", sub.getVersion());
            subs.add(subData);
        }
        data.put("subs", subs);
        return ThingModelMessage.builder()
                .data(data)
                .identifier(ThingModelMessage.ID_REGISTER)
                .type(ThingModelMessage.TYPE_LIFETIME)
                .build();
    }

    private ThingModelMessage getStateChange(DeviceStateChange action) {
        String id = ThingModelMessage.ID_ONLINE;
        if (action.getState() == DeviceState.OFFLINE) {
            id = ThingModelMessage.ID_OFFLINE;
        }
        return ThingModelMessage.builder()
                .identifier(id)
                .type(ThingModelMessage.TYPE_STATE)
                .build();
    }

    private ThingModelMessage getServiceReply(ServiceReply action) {
        return ThingModelMessage.builder()
                .id(action.getReplyId())
                .data(action.getParams())
                .identifier(action.getName() + ThingModelMessage.SERVICE_REPLY_SUFFIX)
                .type(ThingModelMessage.TYPE_SERVICE)
                .build();
    }


}
