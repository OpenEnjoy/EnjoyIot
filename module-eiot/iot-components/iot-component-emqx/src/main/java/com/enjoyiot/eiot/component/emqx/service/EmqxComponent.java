
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
package com.enjoyiot.eiot.component.emqx.service;


import com.enjoyiot.eiot.component.core.ComponentServices;
import com.enjoyiot.eiot.component.core.ThingComponent;
import com.enjoyiot.eiot.component.core.model.down.DeviceConfig;
import com.enjoyiot.eiot.component.core.model.down.PropertyGet;
import com.enjoyiot.eiot.component.core.model.down.PropertySet;
import com.enjoyiot.eiot.component.core.model.down.ServiceInvoke;

import com.enjoyiot.eiot.component.emqx.model.MqttConfig;

import com.enjoyiot.framework.common.util.json.JsonUtils;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class EmqxComponent extends ThingComponent {


    private final EmqxVerticle mqttVerticle;


    public EmqxComponent(
            EmqxVerticle mqttVerticle,
            ComponentServices componentServices
    ) {
        super(componentServices);
        this.mqttVerticle = mqttVerticle;
    }

    @Override
    public String getType() {
        return "emqx";
    }

    @Override
    public String getName() {
        return "内置官方eqmx协议组件";
    }

    @Override
    public boolean stateChange(boolean enable, String config) {
        mqttVerticle.setEmqxComponent(this);

        //停止组件
        if (!enable) {
            mqttVerticle.stopServer();
            return true;
        }

        if (config == null) {
            return false;
        }

        MqttConfig mqttConfig = JsonUtils.parseObject(config, MqttConfig.class);
        if (mqttConfig == null) {
            log.error("parse json mqtt config failed.");
            return false;
        }

        mqttVerticle.stopServer();
        mqttVerticle.startServer(mqttConfig);
        return true;
    }


    @Override
    protected void serviceInvoke(ServiceInvoke action) {
        String topic = String.format("/sys/%s/%s/c/service/%s", action.getProductKey(), action.getDeviceName(), action.getName());
        publish(
                action.getProductKey(),
                action.getDeviceName(),
                topic,
                new JsonObject()
                        .put("id", action.getId())
                        .put("method", "thing.service." + action.getName())
                        .put("params", action.getParams())
                        .toString()
        );
    }

    @Override
    protected void propertyGet(PropertyGet action) {
        String topic = String.format("/sys/%s/%s/c/service/property/get", action.getProductKey(), action.getDeviceName());
        publish(
                action.getProductKey(),
                action.getDeviceName(),
                topic,
                new JsonObject()
                        .put("id", action.getId())
                        .put("method", "thing.service.property.get")
                        .put("params", action.getKeys())
                        .toString()
        );
    }

    @Override
    protected void propertySet(PropertySet action) {
        String topic = String.format("/sys/%s/%s/c/service/property/set", action.getProductKey(), action.getDeviceName());
        publish(
                action.getProductKey(),
                action.getDeviceName(),
                topic,
                new JsonObject()
                        .put("id", action.getId())
                        .put("method", "thing.service.property.set")
                        .put("params", action.getParams())
                        .toString()
        );
    }

    @Override
    protected void deviceConfig(DeviceConfig action) {
        String topic = String.format("/sys/%s/%s/c/config/set", action.getProductKey(), action.getDeviceName());

        publish(
                action.getProductKey(),
                action.getDeviceName(),
                topic,
                new JsonObject()
                        .put("id", action.getId())
                        .put("method", "thing.config.set")
                        .put("params", action.getConfig())
                        .toString()
        );
    }

    private void publish(String pk, String dn, String topic, String msg) {

        mqttVerticle.getClient().publish(topic, Buffer.buffer(msg), MqttQoS.AT_MOST_ONCE, false, false);
    }

}
