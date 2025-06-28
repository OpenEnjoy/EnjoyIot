
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
package com.enjoyiot.eiot.component.mqtt.service;

import cn.hutool.crypto.digest.MD5;
import com.enjoyiot.eiot.component.core.ComponentServices;
import com.enjoyiot.eiot.component.core.ThingComponent;
import com.enjoyiot.eiot.common.enums.DeviceState;
import com.enjoyiot.eiot.component.core.model.down.DeviceConfig;
import com.enjoyiot.eiot.component.core.model.down.PropertyGet;
import com.enjoyiot.eiot.component.core.model.down.PropertySet;
import com.enjoyiot.eiot.component.core.model.down.ServiceInvoke;
import com.enjoyiot.eiot.component.core.model.up.DeviceStateChange;
import com.enjoyiot.eiot.component.core.model.up.EventReport;
import com.enjoyiot.eiot.component.core.model.up.PropertyReport;
import com.enjoyiot.eiot.component.core.model.up.ServiceReply;
import com.enjoyiot.eiot.component.mqtt.model.MqttConfig;
import com.enjoyiot.framework.common.exception.ServiceException;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.device.dto.RegisterDevice;
import com.enjoyiot.module.eiot.api.product.ProductApi;
import com.enjoyiot.module.eiot.api.product.dto.Product;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.handler.codec.mqtt.MqttProperties;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.mqtt.MqttAuth;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.MqttTopicSubscription;
import io.vertx.mqtt.messages.codes.MqttSubAckReasonCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class MqttComponent extends ThingComponent implements Handler<MqttEndpoint> {

    private final Map<String, MqttEndpoint> endpointMap = new HashMap<>();

    private final MqttVerticle mqttVerticle;

    private final ProductApi productApi;

    private final DeviceApi deviceApi;


    protected MqttComponent(
            MqttVerticle mqttVerticle,
            ProductApi productApi,
            DeviceApi deviceApi,
            ComponentServices componentServices
    ) {
        super(componentServices);
        this.mqttVerticle = mqttVerticle;
        this.productApi = productApi;
        this.deviceApi = deviceApi;
    }

    @Override
    public String getType() {
        return "mqtt";
    }

    @Override
    public String getName() {
        return "内置官方mqtt协议组件";
    }

    @Override
    public boolean stateChange(boolean enable, String config) {
        mqttVerticle.setMqttComponent(this);

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

    public void publish(String pk, String dn, String topic, String msg) {
        MqttEndpoint endpoint = endpointMap.get(getEndpointKey(pk, dn));
        if (endpoint == null) {
            throw new ServiceException(500, "mqtt endpoint not found,pk:" + pk + ",dn:" + dn);
        }
        Future<Integer> result = endpoint.publish(topic, Buffer.buffer(msg),
                MqttQoS.AT_LEAST_ONCE, false, false);
        result.onFailure(e -> log.error("public topic failed", e));
        result.onSuccess(integer -> log.info("publish success,topic:{},payload:{}", topic, msg));
    }

    private String getEndpointKey(String pk, String dn) {
        return String.format("%s_%s", pk, dn);
    }

    public void addEndpoint(String pk, String dn, MqttEndpoint endpoint) {
        endpointMap.put(getEndpointKey(pk, dn), endpoint);
    }

    public MqttEndpoint getMqttEndpoint(String pk, String dn) {
        return endpointMap.get(getEndpointKey(pk, dn));
    }

    public void removeEndpoint(String pk, String dn) {
        endpointMap.remove(getEndpointKey(pk, dn));
    }


    @Override
    public void handle(MqttEndpoint endpoint) {

        log.info("MQTT client:{} request to connect, clean session = {}", endpoint.clientIdentifier(), endpoint.isCleanSession());

        MqttAuth auth = endpoint.auth();
        if (auth == null) {
            endpoint.reject(MqttConnectReturnCode.CONNECTION_REFUSED_NOT_AUTHORIZED);
            return;
        }
        //mqtt连接认证信息：
        /*
         * mqttClientId: productKey_deviceName_model
         * mqttUserName: deviceName
         * mqttPassword: md5(产品密钥mqttClientId)
         */
        String clientId = endpoint.clientIdentifier();
        String[] split = clientId.split("_");
        if (split.length != 3) {
            log.error("设备认证失败,clientId格式不正确,需要有三个_");
            endpoint.reject(MqttConnectReturnCode.CONNECTION_REFUSED_CLIENT_IDENTIFIER_NOT_VALID);
            return;
        }
        String pk = split[0];
        String dn = split[1];
        String model = split[2];

        String username = auth.getUsername();
        if (!username.equals(dn)) {
            log.error("设备认证失败,username不正在，当前:{},期望:{}", username, dn);
            endpoint.reject(MqttConnectReturnCode.CONNECTION_REFUSED_BAD_USERNAME_OR_PASSWORD);
            return;
        }

        Product product = productApi.getProductByPkFromCache(pk);
        if (product == null) {
            log.error("产品未找到,pk:{}", pk);
            endpoint.reject(MqttConnectReturnCode.CONNECTION_REFUSED_NOT_AUTHORIZED);
            return;
        }
        String productSecret = product.getProductSecret();

        //校验密码
        String md5 = MD5.create().digestHex(productSecret + clientId);
        if (!md5.equals(auth.getPassword())) {
            log.error("设备认证失败,密码错误,当前:{},期望:{}", auth.getPassword(), md5);
            endpoint.reject(MqttConnectReturnCode.CONNECTION_REFUSED_BAD_USERNAME_OR_PASSWORD);
            return;
        }

        //注册设备
        DeviceInfo parentDevice = deviceApi.registerDevice(RegisterDevice.builder()
                .productKey(pk)
                .deviceName(dn)
                .model(model)
                .build());

        endpoint.accept(false);

        endpoint.disconnectMessageHandler(disconnectMessage -> {
            // 下线
            offline(pk, dn);
        }).subscribeHandler(subscribe -> {
            List<MqttSubAckReasonCode> reasonCodes = new ArrayList<>();
            for (MqttTopicSubscription s : subscribe.topicSubscriptions()) {
                log.info("Subscription for {},with QoS {}", s.topicName(), s.qualityOfService());
                try {
                    String[] subPkDn = getSubDevice(s.topicName());
                    //检验topic
                    if (subPkDn == null) {
                        reasonCodes.add(MqttSubAckReasonCode.NOT_AUTHORIZED);
                        log.error("订阅的topic格式不正确");
                        continue;
                    }
                    String subPk = subPkDn[0];
                    String subDn = subPkDn[1];
                    DeviceInfo subDevice = deviceApi.getDeviceByPkDnByCache(subPk, subDn);
                    if (subDevice == null) {
                        reasonCodes.add(MqttSubAckReasonCode.NOT_AUTHORIZED);
                        log.error("订阅设备pk:{},dn:{} 未注册", subPk, subDn);
                        continue;
                    }
                    fixOnline(subPk, subDn, endpoint);
                    reasonCodes.add(MqttSubAckReasonCode.qosGranted(s.qualityOfService()));

                } catch (Throwable e) {
                    log.error("subscribe failed,topic:" + s.topicName(), e);
                    reasonCodes.add(MqttSubAckReasonCode.NOT_AUTHORIZED);
                }
            }
            // ack the subscriptions request
            endpoint.subscribeAcknowledge(subscribe.messageId(), reasonCodes, MqttProperties.NO_PROPERTIES);
        }).unsubscribeHandler(unsubscribe -> {
            for (String topic : unsubscribe.topics()) {
                String[] subPkDn = getSubDevice(topic);
                //检验topic
                if (subPkDn == null) {
                    log.error("取消订阅的topic格式不正确");
                    continue;
                }
                String subPk = subPkDn[0];
                String subDn = subPkDn[1];

                DeviceInfo subDevice = deviceApi.getDeviceByPkDnByCache(subPk, subDn);
                if (subDevice == null) {
                    log.error("取消订阅设备pk:{},dn:{} 未注册", subPk, subDn);
                    continue;
                }

                //删除设备对应连接
                removeEndpoint(subPk, subDn);
                //下线
                offline(subPk, subDn);
            }
            // ack the subscriptions request
            endpoint.unsubscribeAcknowledge(unsubscribe.messageId());
        }).publishHandler(message -> {
            String topic = message.topicName();
            JsonObject payload = message.payload().toJsonObject();
            log.info("Received message:topic={},payload={}, with QoS {}", topic, payload,
                    message.qosLevel());

            if (message.qosLevel() == MqttQoS.AT_LEAST_ONCE) {
                endpoint.publishAcknowledge(message.messageId());
            } else if (message.qosLevel() == MqttQoS.EXACTLY_ONCE) {
                endpoint.publishReceived(message.messageId());
            }
            if (payload.isEmpty()) {
                return;
            }

            String[] subPkDn = getSubDevice(topic);
            //检验topic
            if (subPkDn == null) {
                log.error("发布的topic格式不正确");
                return;
            }
            String subPk = subPkDn[0];
            String subDn = subPkDn[1];
            fixOnline(subPk, subDn, endpoint);

            try {
                JsonObject defParams = JsonObject.mapFrom(new HashMap<>(0));

                String method = payload.getString("method", "").toLowerCase();
                if (StringUtils.isBlank(method)) {
                    return;
                }
                JsonObject params = payload.getJsonObject("params", defParams);
                method = method.toLowerCase();
                switch (method) {
                    case "thing.lifetime.register":
                        //子设备注册
                        subPk = params.getString("productKey");
                        subDn = params.getString("deviceName");
                        String subModel = params.getString("model");
                        try {
                            //注册设备
                            deviceApi.registerDevice(RegisterDevice.builder()
                                    .productKey(subPk)
                                    .deviceName(subDn)
                                    .model(subModel)
                                    .parentId(parentDevice.getId())
                                    .build());
                            //注册成功
                            reply(endpoint, topic, payload);
                        } catch (Exception e) {
                            log.error("registerDevice error", e);
                            //注册失败
                            reply(endpoint, topic, new JsonObject(), -1);
                        }
                        return;
                    case "thing.event.property.post":
                        //属性上报
                        report(PropertyReport.builder()
                                .productKey(subPk)
                                .deviceName(subDn)
                                .params(params.getMap())
                                .build());
                        reply(endpoint, topic, payload);
                        break;
                    default:
                        if (method.startsWith("thing.event.")) {
                            //事件上报
                            report(EventReport.builder()
                                    .name(method.replace("thing.event.", ""))
                                    .productKey(subPk)
                                    .deviceName(subDn)
                                    .params(params.getMap())
                                    .build());
                            reply(endpoint, topic, payload);
                        } else if (method.startsWith("thing.service.") && method.endsWith("_reply")) {
                            //服务回复
                            report(ServiceReply.builder()
                                    .name(method.replaceAll("thing\\.service\\.(.*)_reply", "$1"))
                                    .productKey(subPk)
                                    .deviceName(subDn)
                                    .code(payload.getInteger("code", 0))
                                    .params(params.getMap())
                                    .build());

                        }
                }

            } catch (Throwable e) {
                log.error("handler message failed,topic:" + message.topicName(), e);
            }

        }).publishReleaseHandler(endpoint::publishComplete);
    }

    /**
     * 下线所有设备
     */
    public void offlineAll() {
        for (String pkDn : endpointMap.keySet()) {
            String[] parts = pkDn.split("_");
            //下线
            offline(parts[0], parts[1]);
        }
    }

    public void online(String pk, String dn, MqttEndpoint endpoint) {
        addEndpoint(pk, dn, endpoint);

        //上线
        report(DeviceStateChange.builder()
                .productKey(pk)
                .deviceName(dn)
                .state(DeviceState.ONLINE)
                .build());
    }

    private void fixOnline(String pk, String dn, MqttEndpoint endpoint) {
        MqttEndpoint mqttEndpoint = getMqttEndpoint(pk, dn);
        if (Objects.isNull(mqttEndpoint) || mqttEndpoint != endpoint) {
            online(pk, dn, endpoint);
        }
    }

    private void offline(String productKey, String deviceName) {
        removeEndpoint(productKey, deviceName);

        report(DeviceStateChange.builder()
                .productKey(productKey)
                .deviceName(deviceName)
                .state(DeviceState.OFFLINE)
                .build());
    }


    private String[] getSubDevice(String topic) {
        String[] topicParts = topic.split("/");
        if (topicParts.length < 5) {
            return null;
        }
        return new String[]{topicParts[2], topicParts[3]};
    }

    /**
     * 回复设备
     */
    private void reply(MqttEndpoint endpoint, String topic, JsonObject payload) {
        reply(endpoint, topic, payload, 0);
    }

    /**
     * 回复设备
     */
    private void reply(MqttEndpoint endpoint, String topic, JsonObject payload, int code) {
        Map<String, Object> payloadReply = new HashMap<>();
        topic = topic.replace("/s/", "/c/") + "_reply";

        payloadReply.put("id", payload.getString("id"));
        payloadReply.put("method", payload.getString("method") + "_reply");
        payloadReply.put("code", code);
        payloadReply.put("data", payload.getJsonObject("params"));

        endpoint.publish(topic, JsonObject.mapFrom(payloadReply).toBuffer(), MqttQoS.AT_LEAST_ONCE, false, false);
    }
}
