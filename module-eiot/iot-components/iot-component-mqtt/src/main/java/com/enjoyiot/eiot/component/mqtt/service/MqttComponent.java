
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
import cn.hutool.json.JSONUtil;
import com.enjoyiot.eiot.component.core.ComponentServices;
import com.enjoyiot.eiot.component.core.ThingComponent;
import com.enjoyiot.eiot.common.enums.DeviceState;
import com.enjoyiot.eiot.component.core.model.down.*;
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
import com.enjoyiot.module.eiot.api.shadow.DeviceShadowApi;
import com.enjoyiot.module.eiot.api.shadow.dto.DeviceShadowDTO;
import com.enjoyiot.module.eiot.enums.shadow.ShadowErrorCodeEnum;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.handler.codec.mqtt.MqttProperties;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.mqtt.MqttAuth;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.MqttTopicSubscription;
import io.vertx.mqtt.messages.codes.MqttSubAckReasonCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.*;

@Slf4j
@Component
public class MqttComponent extends ThingComponent implements Handler<MqttEndpoint> {

    private final Map<String, MqttEndpoint> endpointMap = new HashMap<>();

    private final MqttVerticle mqttVerticle;

    private final ProductApi productApi;

    private final DeviceApi deviceApi;

    private final DeviceShadowApi deviceShadowApi;

    protected MqttComponent(
            MqttVerticle mqttVerticle,
            ProductApi productApi,
            DeviceApi deviceApi,
            DeviceShadowApi deviceShadowApi,
            ComponentServices componentServices
    ) {
        super(componentServices);
        this.mqttVerticle = mqttVerticle;
        this.productApi = productApi;
        this.deviceApi = deviceApi;
        this.deviceShadowApi = deviceShadowApi;
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
    protected void deviceOta(DeviceOta action) {
        String topic = String.format("/ota/deivce/upgrade/%s/%s", action.getProductKey(), action.getDeviceName());
        publish(action.getProductKey(),
                action.getDeviceName(),
                topic,
                new JsonObject()
                        .put("id", action.getId())
                        .put("code", "200")
                        .put("data", JSONUtil.parse(action.getData())).toString()
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
    public void deviceTopoChange(DeviceTopoChange action) {
        String topic = String.format("/sys/%s/%s/c/topo/change", action.getProductKey(), action.getDeviceName());
         publish(
                action.getProductKey(),
                action.getDeviceName(),
                topic,
                new JsonObject()
                        .put("id", action.getId())
                        .put("method", "thing.topo.change")
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

    @Override
    protected void shadowPush(ShadowPush action) {
        String topic = String.format("/shadow/get/%s/%s", action.getProductKey(), action.getDeviceName());

        // 构建 control 消息
        Map<String, Object> message = new HashMap<>();
        message.put("method", "control");
        
        // 构建 payload
        Map<String, Object> payload = new HashMap<>();

        Map<String, Object> state = new HashMap<>();
        state.put("desired", action.getDesired());
        state.put("reported", action.getReported());

        payload.put("state", state);
        payload.put("metadata", action.getMetadata());

        message.put("payload", payload);
        message.put("version", action.getVersion());
        message.put("timestamp", System.currentTimeMillis());

        publish(
                action.getProductKey(),
                action.getDeviceName(),
                topic,
                JSONUtil.toJsonStr(message)
        );
    }

    public void publish(String pk, String dn, String topic, String msg) {
        MqttEndpoint endpoint = endpointMap.get(getEndpointKey(pk, dn));
        if (endpoint == null) {
            throw new ServiceException(500, "mqtt endpoint not found,pk:" + pk + ",dn:" + dn);
        }
        try {
            Future<Integer> result = endpoint.publish(topic, Buffer.buffer(msg),
                    MqttQoS.AT_LEAST_ONCE, false, false);
            result.onFailure(e -> {
                log.error("public topic failed", e);
                removeEndpoint(pk, dn);
            });
            result.onSuccess(integer -> log.info("publish success,topic:{},payload:{}", topic, msg));
        } catch (IllegalStateException e) {
            removeEndpoint(pk, dn);
            throw new ServiceException(500, "mqtt endpoint disconnected,pk:" + pk + ",dn:" + dn);
        }
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

        if (false){
            //校验密码-默认不校验,为了让新手快速上手
            String md5 = MD5.create().digestHex(productSecret + clientId);
            if (!md5.equals(auth.getPassword())) {
                log.error("设备认证失败,密码错误,当前:{},期望:{}", auth.getPassword(), md5);
                endpoint.reject(MqttConnectReturnCode.CONNECTION_REFUSED_BAD_USERNAME_OR_PASSWORD);
                return;
            }
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
                    String topicName = s.topicName();
                    
                    // 特殊处理：设备影子 topic
                    if (topicName.startsWith("/shadow/")) {
                        String[] parts = topicName.split("/");
                        if (parts.length >= 5) {
                            String subPk = parts[3];
                            String subDn = parts[4];
                            // 验证设备是否有权限订阅此影子 topic（只能订阅自己的）
                            if (pk.equals(subPk) && dn.equals(subDn)) {
                                fixOnline(pk, dn, endpoint);
                                reasonCodes.add(MqttSubAckReasonCode.qosGranted(s.qualityOfService()));
                                log.info("设备订阅影子topic成功: pk={}, dn={}, topic={}", pk, dn, topicName);
                            } else {
                                reasonCodes.add(MqttSubAckReasonCode.NOT_AUTHORIZED);
                                log.error("设备无权订阅其他设备的影子topic: 当前设备pk={},dn={}, 订阅topic={}", pk, dn, topicName);
                            }
                        } else {
                            reasonCodes.add(MqttSubAckReasonCode.NOT_AUTHORIZED);
                            log.error("影子topic格式不正确: {}", topicName);
                        }
                        continue;
                    }
                    
                    // 普通 topic 处理
                    String[] subPkDn = getSubDevice(topicName);
                    //检验topic
                    if (subPkDn == null) {
                        reasonCodes.add(MqttSubAckReasonCode.NOT_AUTHORIZED);
                        log.error("订阅的topic格式不正确: {}", topicName);
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

            // 处理设备影子 Topic
            if (topic.startsWith("/shadow/")) {
                handleShadowMessage(endpoint, topic, payload);
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
                String method = payload.getString("method", "").toLowerCase();
                if (StringUtils.isBlank(method)) {
                    return;
                }
                method = method.toLowerCase();
                JsonObject params = resolveParams(payload);
                switch (method) {
                    case "thing.lifetime.register":
                        //子设备注册
                        subPk = params.getString("productKey");
                        if (parentDevice.getProductKey().equals(subPk)) {
                            //防呆
                            log.warn("你自己注册自己? wtf ? 注册子产品Key与父产品Key相同");
                            return;
                        }
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
                    case "thing.config.get":
                        try {
                            com.enjoyiot.module.eiot.api.device.dto.DeviceConfig deviceConfig = deviceApi.getDeviceConfig(subPk, subDn);
                            if (deviceConfig == null || deviceConfig.getConfig() == null) {
                                reply(endpoint, topic, new JsonObject(), -1);
                                break;
                            }
                            Map<String, Object> config = JsonUtils.parseObject(deviceConfig.getConfig(), Map.class);
                            payload.put("params", new JsonObject(config));
                            reply(endpoint, topic, payload);
                        } catch (Throwable e) {
                            log.error("thing.config.get handle failed", e);
                            reply(endpoint, topic, new JsonObject(), -1);
                        }
                        break;

                    case "thing.topo.get":
                        //网关获取拓扑关系
                        List<DeviceInfo> subDeviceList = deviceApi.getSubDevicesByProductKeAndDeviceName(pk, dn);
                        payload.put("params", new JsonArray(JsonUtils.toJsonString(subDeviceList)));
                        replyArray(endpoint, topic, payload, 0);
                        break;

                    case "thing.lifetime.deregister":
                        String subPkDeregister = params.getString("productKey");
                        String subDnDeregister = params.getString("deviceName");
                        Boolean ret = deviceApi.deregisterSubDevice(pk, dn, model, subPkDeregister, subDnDeregister);
                        if (ret) {
                            //取消绑定注册成功
                            reply(endpoint, topic, payload);
                        } else {
                            //取消绑定失败
                            reply(endpoint, topic, new JsonObject(), -1);
                        }

                        break;

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

    private JsonObject resolveParams(JsonObject payload) {
        JsonObject params = payload.getJsonObject("params");
        if (params != null) {
            return params;
        }
        JsonObject data = payload.getJsonObject("data");
        if (data == null) {
            return JsonObject.mapFrom(new HashMap<>(0));
        }
        return data;
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
    private void replyArray(MqttEndpoint endpoint, String topic, JsonObject payload, int code) {
        Map<String, Object> payloadReply = new HashMap<>();
        payloadReply.put("id", payload.getString("id"));
        payloadReply.put("method", payload.getString("method") + "_reply");
        payloadReply.put("code", code);
        payloadReply.put("data", payload.getJsonArray("params"));

        endpoint.publish(topic.replace("/s/", "/c/") + "_reply", JsonObject.mapFrom(payloadReply).toBuffer(), MqttQoS.AT_LEAST_ONCE, false, false);
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

    /**
     * 处理设备影子消息
     * Topic 格式: /shadow/update/${productKey}/${deviceName}
     */
    private void handleShadowMessage(MqttEndpoint endpoint, String topic, JsonObject payload) {
        try {
            // 解析 topic 获取 productKey 和 deviceName
            String[] parts = topic.split("/");
            if (parts.length < 5) {
                log.error("设备影子 Topic 格式不正确: {}", topic);
                return;
            }

            String action = parts[2]; // update 或 get
            String productKey = parts[3];
            String deviceName = parts[4];

            // 确保设备在线
            fixOnline(productKey, deviceName, endpoint);

            // 获取设备信息
            DeviceInfo deviceInfo = deviceApi.getDeviceByPkDnByCache(productKey, deviceName);
            if (deviceInfo == null) {
                log.error("设备不存在: pk={}, dn={}", productKey, deviceName);
                replyShadowError(endpoint, productKey, deviceName, ShadowErrorCodeEnum.SERVER_ERROR);
                return;
            }

            String method = payload.getString("method", "").toLowerCase();
            
            if ("update".equals(action)) {
                handleShadowUpdate(endpoint, deviceInfo, payload, method);
            } else if ("get".equals(action)) {
                handleShadowGet(endpoint, deviceInfo, payload);
            } else {
                log.error("不支持的影子操作: {}", action);
            }

        } catch (Exception e) {
            log.error("处理设备影子消息失败, topic: {}, payload: {}", topic, payload, e);
        }
    }

    /**
     * 处理设备影子更新
     */
    private void handleShadowUpdate(MqttEndpoint endpoint, DeviceInfo deviceInfo, JsonObject payload, String method) {
        try {
            Long deviceId = deviceInfo.getId();
            String productKey = deviceInfo.getProductKey();
            String dn = deviceInfo.getDn();

            // 校验 method 字段
            if (StringUtils.isBlank(method)) {
                replyShadowError(endpoint, productKey, dn, ShadowErrorCodeEnum.MISSING_METHOD);
                return;
            }

            // 处理 get 方法
            if ("get".equals(method)) {
                DeviceShadowDTO shadow = deviceShadowApi.getByDeviceId(deviceId);
                if (shadow == null) {
                    replyShadowError(endpoint, productKey, dn, ShadowErrorCodeEnum.SERVER_ERROR);
                    return;
                }

                Map<String, Object> response = new HashMap<>();
                response.put("method", "reply");
                response.put("payload", buildShadowPayload(shadow));
                response.put("version", shadow.getVersion());
                response.put("timestamp", System.currentTimeMillis());

                String topic = String.format("/shadow/get/%s/%s", productKey, dn);
                endpoint.publish(topic, JsonObject.mapFrom(response).toBuffer(), 
                    MqttQoS.AT_LEAST_ONCE, false, false);

                log.info("返回设备影子: deviceId={}, version={}", deviceId, shadow.getVersion());
                return;
            }

            // 处理 update 方法
            if ("update".equals(method)) {
                handleShadowUpdateMethod(endpoint, deviceId, productKey, dn, payload);
                return;
            }

            // 处理 delete 方法
            if ("delete".equals(method)) {
                handleShadowDeleteMethod(endpoint, deviceId, productKey, dn, payload);
                return;
            }

            // 不支持的方法
            replyShadowError(endpoint, productKey, dn, ShadowErrorCodeEnum.INVALID_METHOD);

        } catch (Exception e) {
            log.error("处理设备影子更新失败", e);
            replyShadowError(endpoint, deviceInfo.getProductKey(), deviceInfo.getDn(), ShadowErrorCodeEnum.SERVER_ERROR);
        }
    }

    /**
     * 处理设备影子 update 方法
     */
    private void handleShadowUpdateMethod(MqttEndpoint endpoint, Long deviceId, String productKey, String dn, JsonObject payload) {
        JsonObject state = payload.getJsonObject("state");
        if (state == null) {
            replyShadowError(endpoint, productKey, dn, ShadowErrorCodeEnum.MISSING_STATE);
            return;
        }

        // 解析 version
        Long deviceVersion = parseVersion(payload, endpoint, productKey, dn);
        if (deviceVersion == null && payload.getValue("version") != null) {
            return; // 版本解析失败，已回复错误
        }

        try {
            Long newVersion;

            // 特殊版本号：-1 表示清空整个影子
            if (deviceVersion != null && deviceVersion == -1) {
                newVersion = deviceShadowApi.clearShadow(deviceId);
                log.info("设备清空影子: deviceId={}, newVersion={}", deviceId, newVersion);
                replyShadowSuccess(endpoint, productKey, dn, newVersion);
                return;
            }

            // 检查是否清空 desired
            Object desiredObj = state.getValue("desired");
            if (desiredObj != null && "null".equals(String.valueOf(desiredObj))) {
                newVersion = deviceShadowApi.clearDesired(deviceId, deviceVersion);
                log.info("设备清空期望状态: deviceId={}, newVersion={}", deviceId, newVersion);
                replyShadowSuccess(endpoint, productKey, dn, newVersion);
                return;
            }

            // 更新 reported
            Object reportedObj = state.getValue("reported");
            if (reportedObj == null) {
                replyShadowError(endpoint, productKey, dn, ShadowErrorCodeEnum.MISSING_REPORTED);
                return;
            }

            Map<String, Object> reportedMap;
            if ("null".equals(String.valueOf(reportedObj))) {
                // 清空全部 reported
                reportedMap = new HashMap<>();
            } else {
                JsonObject reported = state.getJsonObject("reported");
                if (reported == null || reported.isEmpty()) {
                    replyShadowError(endpoint, productKey, dn, ShadowErrorCodeEnum.MISSING_REPORTED);
                    return;
                }
                reportedMap = reported.getMap();
            }

            // 校验属性个数
            if (reportedMap.size() > 128) {
                replyShadowError(endpoint, productKey, dn, ShadowErrorCodeEnum.TOO_MANY_ATTRIBUTES);
                return;
            }

            newVersion = deviceShadowApi.updateReported(deviceId, reportedMap, deviceVersion);
            log.info("设备更新影子: deviceId={}, newVersion={}", deviceId, newVersion);
            replyShadowSuccess(endpoint, productKey, dn, newVersion);

        } catch (Exception e) {
            log.error("设备影子更新失败: deviceId={}, version={}", deviceId, deviceVersion, e);
            replyShadowError(endpoint, productKey, dn, ShadowErrorCodeEnum.VERSION_CONFLICT);
        }
    }

    /**
     * 处理设备影子 delete 方法
     */
    private void handleShadowDeleteMethod(MqttEndpoint endpoint, Long deviceId, String productKey, String dn, JsonObject payload) {
        JsonObject state = payload.getJsonObject("state");
        if (state == null) {
            replyShadowError(endpoint, productKey, dn, ShadowErrorCodeEnum.MISSING_STATE);
            return;
        }

        // 解析 version
        Long deviceVersion = parseVersion(payload, endpoint, productKey, dn);
        if (deviceVersion == null && payload.getValue("version") != null) {
            return; // 版本解析失败，已回复错误
        }

        try {
            Object reportedObj = state.getValue("reported");
            if (reportedObj == null) {
                replyShadowError(endpoint, productKey, dn, ShadowErrorCodeEnum.MISSING_REPORTED);
                return;
            }

            Long newVersion;

            if ("null".equals(String.valueOf(reportedObj))) {
                // 删除全部属性：使用完全替换方法传入空 map
                newVersion = deviceShadowApi.replaceReported(deviceId, new HashMap<>(), deviceVersion);
            } else {
                // 删除指定属性
                JsonObject reported = state.getJsonObject("reported");
                if (reported == null || reported.isEmpty()) {
                    replyShadowError(endpoint, productKey, dn, ShadowErrorCodeEnum.MISSING_REPORTED);
                    return;
                }

                // 收集值为 "null" 的属性 key
                java.util.List<String> keysToDelete = new java.util.ArrayList<>();
                for (String key : reported.fieldNames()) {
                    if ("null".equals(String.valueOf(reported.getValue(key)))) {
                        keysToDelete.add(key);
                    }
                }

                if (keysToDelete.isEmpty()) {
                    replyShadowError(endpoint, productKey, dn, ShadowErrorCodeEnum.MISSING_REPORTED);
                    return;
                }

                newVersion = deviceShadowApi.deleteReportedProperties(deviceId, keysToDelete, deviceVersion);
            }

            log.info("设备删除影子属性: deviceId={}, newVersion={}", deviceId, newVersion);
            replyShadowSuccess(endpoint, productKey, dn, newVersion);

        } catch (Exception e) {
            log.error("设备影子删除失败: deviceId={}, version={}", deviceId, deviceVersion, e);
            replyShadowError(endpoint, productKey, dn, ShadowErrorCodeEnum.VERSION_CONFLICT);
        }
    }

    /**
     * 解析版本号
     */
    private Long parseVersion(JsonObject payload, MqttEndpoint endpoint, String productKey, String dn) {
        Object versionObj = payload.getValue("version");
        if (versionObj == null) {
            return null;
        }

        try {
            if (versionObj instanceof Number) {
                return ((Number) versionObj).longValue();
            } else {
                return Long.parseLong(versionObj.toString());
            }
        } catch (NumberFormatException e) {
            replyShadowError(endpoint, productKey, dn, ShadowErrorCodeEnum.INVALID_VERSION);
            return null;
        }
    }

    /**
     * 处理设备影子获取
     */
    private void handleShadowGet(MqttEndpoint endpoint, DeviceInfo deviceInfo, JsonObject payload) {
        try {
            String method = payload.getString("method", "get");
            
            if ("get".equals(method)) {
                // 设备请求获取完整影子
                DeviceShadowDTO shadow = deviceShadowApi.getByDeviceId(deviceInfo.getId());
                if (shadow == null) {
                    replyShadowError(endpoint, deviceInfo.getProductKey(), deviceInfo.getDn(), ShadowErrorCodeEnum.SERVER_ERROR);
                    return;
                }

                // 构建响应消息
                Map<String, Object> response = new HashMap<>();
                response.put("method", "reply");
                response.put("payload", buildShadowPayload(shadow));
                response.put("version", shadow.getVersion());
                response.put("timestamp", System.currentTimeMillis());

                String topic = String.format("/shadow/get/%s/%s", 
                    deviceInfo.getProductKey(), deviceInfo.getDn());
                endpoint.publish(topic, JsonObject.mapFrom(response).toBuffer(), 
                    MqttQoS.AT_LEAST_ONCE, false, false);

                log.info("返回设备影子: deviceId={}, version={}", deviceInfo.getId(), shadow.getVersion());
            }

        } catch (Exception e) {
            log.error("处理设备影子获取失败", e);
            replyShadowError(endpoint, deviceInfo.getProductKey(), deviceInfo.getDn(), ShadowErrorCodeEnum.SERVER_ERROR);
        }
    }

    /**
     * 构建影子 payload
     */
    private Map<String, Object> buildShadowPayload(DeviceShadowDTO shadow) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("status", "success");

        Map<String, Object> state = new HashMap<>();
        
        // 添加 desired 和 reported
        if (shadow.getDesired() != null && !shadow.getDesired().isEmpty()) {
            state.put("desired", shadow.getDesired());
        }
        if (shadow.getReported() != null && !shadow.getReported().isEmpty()) {
            state.put("reported", shadow.getReported());
        }

        payload.put("state", state);
        
        // 添加元数据
        if (shadow.getMetadata() != null) {
            payload.put("metadata", shadow.getMetadata());
        }

        return payload;
    }

    /**
     * 回复影子操作成功
     */
    private void replyShadowSuccess(MqttEndpoint endpoint, String productKey, String deviceName, Long version) {
        Map<String, Object> response = new HashMap<>();
        response.put("method", "reply");
        
        Map<String, Object> payload = new HashMap<>();
        payload.put("status", "success");
        if (version != null) {
            payload.put("version", version);
        }
        
        response.put("payload", payload);
        response.put("timestamp", System.currentTimeMillis());

        String topic = String.format("/shadow/get/%s/%s", productKey, deviceName);
        endpoint.publish(topic, JsonObject.mapFrom(response).toBuffer(), 
            MqttQoS.AT_LEAST_ONCE, false, false);
    }

    /**
     * 回复影子操作失败
     */
    private void replyShadowError(MqttEndpoint endpoint, String productKey, String deviceName, ShadowErrorCodeEnum errorCode) {
        Map<String, Object> response = new HashMap<>();
        response.put("method", "reply");
        
        Map<String, Object> payload = new HashMap<>();
        payload.put("status", "error");
        
        Map<String, Object> content = new HashMap<>();
        content.put("errorcode", String.valueOf(errorCode.getCode()));
        content.put("errormessage", errorCode.getMessage());
        payload.put("content", content);
        
        response.put("payload", payload);
        response.put("timestamp", System.currentTimeMillis());

        String topic = String.format("/shadow/get/%s/%s", productKey, deviceName);
        endpoint.publish(topic, JsonObject.mapFrom(response).toBuffer(), 
            MqttQoS.AT_LEAST_ONCE, false, false);
    }

}
