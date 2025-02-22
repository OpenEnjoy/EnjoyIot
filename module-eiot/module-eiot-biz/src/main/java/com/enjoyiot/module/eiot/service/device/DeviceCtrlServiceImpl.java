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
package com.enjoyiot.module.eiot.service.device;


import cn.hutool.core.util.IdUtil;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.virtualdevice.VirtualManager;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.module.eiot.api.device.dto.DeviceConfig;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.service.component.ComponentManager;
import com.enjoyiot.module.eiot.service.iot.ParseThingModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class DeviceCtrlServiceImpl implements DeviceCtrlService {

    @Resource
    private DeviceConfigService deviceConfigService;

    @Resource
    private ParseThingModelService parseThingModelService;

    @Resource
    private DeviceInfoService deviceInfoService;

    @Resource
    private ComponentManager componentManager;

    @Resource
    @Lazy
    private VirtualManager virtualManager;


    /**
     * 设备服务调用
     */
    @Override
    public void invokeService(Long deviceId, String service,
                              Map<String, Object> args) {
        invokeService(deviceId, service, args, true);
    }

    /**
     * 设备服务调用
     */
    @Override
    public void invokeService(Long deviceId, String service,
                              Map<String, Object> args, boolean checkOwner) {
        DeviceInfo device = getAndCheckDevice(deviceId, checkOwner);

        send(deviceId, device.getProductKey(), device.getDn(),
                args, ThingModelMessage.TYPE_SERVICE, service);
    }

    @Override
    public void otaUpgrade(Long deviceId, boolean checkOwner, Object data) {
        DeviceInfo device = getAndCheckDevice(deviceId, checkOwner);
        send(deviceId, device.getProductKey(), device.getDn(),
                data, ThingModelMessage.TYPE_OTA, "ota");
    }

    /**
     * 设备属性获取
     */
    @Override
    public void getProperty(Long deviceId, List<String> properties,
                            boolean checkOwner) {
        DeviceInfo device = getAndCheckDevice(deviceId, checkOwner);

        send(deviceId, device.getProductKey(), device.getDn(), properties,
                ThingModelMessage.TYPE_PROPERTY, ThingModelMessage.ID_PROPERTY_GET);
    }

    /**
     * 设备属性设置
     */
    @Override
    public void setProperty(Long deviceId, Map<String, Object> properties) {
        setProperty(deviceId, properties, true);
    }

    /**
     * 设备属性设置
     */
    @Override
    public void setProperty(Long deviceId, Map<String, Object> properties,
                            boolean checkOwner) {
        DeviceInfo device = getAndCheckDevice(deviceId, checkOwner);

        send(deviceId, device.getProductKey(), device.getDn(), properties,
                ThingModelMessage.TYPE_PROPERTY, ThingModelMessage.ID_PROPERTY_SET);
    }

    /**
     * 设备配置下发
     */
    @Override
    public void sendConfig(Long deviceId, boolean checkOwner) {
        DeviceInfo device = getAndCheckDevice(deviceId, checkOwner);
        DeviceConfig config = deviceConfigService.findByDeviceId(deviceId);
        Map data = JsonUtils.parseObject(config.getConfig(), Map.class);
        send(deviceId, device.getProductKey(), device.getDn(), data,
                ThingModelMessage.TYPE_CONFIG, ThingModelMessage.ID_CONFIG_SET);

    }

    /**
     * 设备配置下发
     */
    @Override
    public void sendConfig(Long deviceId) {
        sendConfig(deviceId, true);
    }

    /**
     * 检查设备操作权限和状态
     */
    @Override
    public DeviceInfo getAndCheckDevice(Long deviceId, boolean checkOwner) {
        return deviceInfoService.getDeviceInfo(deviceId);
    }

    /**
     * 数据下发
     */
    private void send(Long deviceId, String pk, String dn,
                      Object data, String type, String identifier) {
        ThingModelMessage message = ThingModelMessage.builder()
                .id(IdUtil.fastSimpleUUID())
                .time(System.currentTimeMillis())
                .type(type)
                .productKey(pk)
                .dn(dn)
                .deviceId(deviceId)
                .mid(IdUtil.fastSimpleUUID())
                .identifier(identifier)
                .data(data)
                .build();
        if (virtualManager.isVirtual(deviceId)) {
            //虚拟设备指令下发
            virtualManager.send(message);
        } else {
            //设备指令下发
            componentManager.sendToDevice(message);
        }

    }

}
