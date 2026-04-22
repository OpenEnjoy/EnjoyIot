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


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.virtualdevice.VirtualManager;
import com.enjoyiot.framework.common.exception.ServiceException;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.module.eiot.api.device.dto.DeviceConfig;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.device.dto.DeviceTopoChangeDTO;
import com.enjoyiot.module.eiot.service.component.ComponentManager;
import com.enjoyiot.module.eiot.service.iot.ParseThingModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


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
    private Optional<VirtualManager> virtualManager;


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
        if (config == null || StrUtil.isBlank(config.getConfig())) {
            throw new ServiceException(400, "device config is empty, cannot send");
        }
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
     * 解绑子设备
     */
    public void unbindDevice(List<Long> subDeviceIds) {
        if (CollectionUtil.isEmpty(subDeviceIds)){
            return;
        }
        Long deviceId = subDeviceIds.get(0);
        DeviceInfo deviceInfo = deviceInfoService.getDeviceInfoFromCache(deviceId);
        if (deviceInfo == null) {
            return;
        }
        Long parentId = deviceInfo.getParentId();
        if (ObjectUtil.isNull(parentId)) {
            return;
        }
        DeviceInfo parent = deviceInfoService.getDeviceInfoFromCache(parentId);
        List<DeviceInfo> subList =deviceInfoService.getDeviceInfoList(subDeviceIds);
        sendUnbindMsg(subList, parent);
    }

    private void sendUnbindMsg(List<DeviceInfo> subList, DeviceInfo parent) {
        if (parent == null) {
            log.error("sendUnbindMsg : parent device not found: {}", parent.getDn());
            return;
        }
        if(CollectionUtil.isEmpty(subList)){
            log.error("sendUnbindMsg : sub device not found: {}", parent.getDn());
            return;
        }

        try {
            List<DeviceTopoChangeDTO.DeviceInfo> changeDeviceList
                    = subList.stream().map(device -> {
                DeviceTopoChangeDTO.DeviceInfo d = new DeviceTopoChangeDTO.DeviceInfo();
                d.setDn(device.getDn());
                d.setPk(device.getProductKey());
                return d;
            }).collect(Collectors.toList());

            DeviceTopoChangeDTO changeBo = DeviceTopoChangeDTO.builder().status(1).subList(changeDeviceList).build();
            //下发子设备注销给网关
            send(parent.getId(), parent.getProductKey(), parent.getDn(),
                    changeBo,
                    ThingModelMessage.TYPE_TOPO_CHANGE, ThingModelMessage.ID_CHANGE);
        } catch (Throwable e) {
            log.error("send {} message error", ThingModelMessage.ID_CHANGE, e);
        }
        return ;
    }

    @Override
    public void bindDevice(List<Long> subDeviceIds, Long parentId) {
        if (CollectionUtil.isEmpty(subDeviceIds)){
            return;
        }
        if (ObjectUtil.isNull(parentId)) {
            return;
        }
        DeviceInfo parent = deviceInfoService.getDeviceInfoFromCache(parentId);
        List<DeviceInfo> subList =deviceInfoService.getDeviceInfoList(subDeviceIds);

        sendBindMsg(subList, parent);
    }

    private void sendBindMsg(List<DeviceInfo> subList, DeviceInfo parent) {
        if (parent == null) {
            log.error("sendUnbindMsg : parent device not found: {}", parent.getDn());
            return;
        }
        if(CollectionUtil.isEmpty(subList)){
            log.error("sendUnbindMsg : sub device not found: {}", parent.getDn());
            return;
        }

        try {
            List<DeviceTopoChangeDTO.DeviceInfo> changeDeviceList
                    = subList.stream().map(device -> {
                DeviceTopoChangeDTO.DeviceInfo d = new DeviceTopoChangeDTO.DeviceInfo();
                d.setDn(device.getDn());
                d.setPk(device.getProductKey());
                return d;
            }).collect(Collectors.toList());

            DeviceTopoChangeDTO changeBo = DeviceTopoChangeDTO.builder().status(0).subList(changeDeviceList).build();
            //下发子设备注销给网关
            send(parent.getId(), parent.getProductKey(), parent.getDn(),
                    changeBo,
                    ThingModelMessage.TYPE_TOPO_CHANGE, ThingModelMessage.ID_CHANGE);
        } catch (Throwable e) {
            log.error("send {} message error", ThingModelMessage.ID_CHANGE, e);
        }
        return ;
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
        if (virtualManager.isPresent() && virtualManager.get().isVirtual(deviceId)) {
            //虚拟设备指令下发
            virtualManager.get().send(message);
        } else {
            //设备指令下发
            componentManager.sendToDevice(message);
        }

    }

}
