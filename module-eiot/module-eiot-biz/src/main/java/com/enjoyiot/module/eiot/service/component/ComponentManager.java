
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
package com.enjoyiot.module.eiot.service.component;

import cn.hutool.core.util.IdUtil;
import com.enjoyiot.eiot.common.constant.Constants;
import com.enjoyiot.eiot.common.thing.ComponentMessage;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.message.core.ConsumerHandler;
import com.enjoyiot.eiot.message.core.MqConsumer;
import com.enjoyiot.eiot.message.core.MqProducer;
import com.enjoyiot.framework.common.exception.ServiceException;
import com.enjoyiot.module.eiot.controller.admin.component.vo.ComponentCreateReqVO;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Set;

import static com.enjoyiot.eiot.common.constant.Constants.*;
import static com.enjoyiot.eiot.common.enums.ErrorCodeConstants.ROUTER_NOT_EXISTS;

@Service
public class ComponentManager implements ConsumerHandler<ComponentMessage> {

    private final MqProducer<ThingModelMessage> producer;
    private final MqProducer<ComponentMessage> componentMessageMqProducer;

    private final StringRedisTemplate stringRedisTemplate;

    private final ComponentService componentService;

    private Set<String> components = new HashSet<>();

    public ComponentManager(MqConsumer<ThingModelMessage> consumer,
                            MqProducer<ThingModelMessage> producer,
                            MqConsumer<ComponentMessage> componentMessageMqConsumer,
                            MqProducer<ComponentMessage> componentMessageMqProducer,
                            StringRedisTemplate stringRedisTemplate,
                            ComponentService componentService) {
        this.producer = producer;
        this.componentMessageMqProducer = componentMessageMqProducer;
        this.stringRedisTemplate = stringRedisTemplate;
        this.componentService = componentService;

        //订阅组件发现
        componentMessageMqConsumer.consume(COMPONENT_DISCOVER_TOPIC, this);
    }

    @Override
    public void handler(ComponentMessage msg) {
        //组件发现消息
        componentDiscover(msg);
    }

    /**
     * 通过路由发送消息给设备
     */
    public void sendToDevice(ThingModelMessage msg) {
        String routerKey = getRouterKey(msg);
        String router = stringRedisTemplate.opsForValue().get(routerKey);
        //未找到设备路由
        if (StringUtils.isBlank(router)) {
            throw new ServiceException(ROUTER_NOT_EXISTS);
        }
//
//        String[] split = router.split("/");
//        String componentType = split[0];
        //发给指定组件
        producer.publish(Constants.getSendToDeviceTopic(router), msg);
    }

    @NotNull
    private static String getSendToDeviceTopic(String componentType) {
        return String.format("%s/%s", componentType, THING_MODEL_MESSAGE_TOPIC);
    }

    private void componentDiscover(ComponentMessage msg) {
        //组件类型
        String type = msg.getType();
        //组件实例id
        String componentId = msg.getComponentId();
        //组件名称
        String name = msg.getName();

        if (!components.contains(type)) {
            if (componentService.getComponent(type) == null) {
                //组件信息入库
                componentService.createComponent(ComponentCreateReqVO.builder()
                        .type(type)
                        .name(name)
                        .status(0)
                        .build());
            }
            components.add(type);
        }

        //回复发现
        componentMessageMqProducer.publish(COMPONENT_DISCOVER_REPLY_TOPIC, ComponentMessage.builder()
                .id(IdUtil.fastSimpleUUID())
                .content(COMPONENT_DISCOVER_REPLY_TOPIC)
                .componentId(componentId)
                .type(type)
                .time(System.currentTimeMillis())
                .name(name)
                .build());
    }

    private String getRouterKey(ThingModelMessage message) {
        return Constants.getRedisDeviceRouter(message.getProductKey(), message.getDn());
    }
}
