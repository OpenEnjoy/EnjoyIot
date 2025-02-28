/*
 *
 *  * | Licensed 未经许可不能去掉「EnjoyIot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2024] [EnjoyIot]
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


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.enjoyiot.eiot.common.enums.DeviceState;
import com.enjoyiot.eiot.component.core.model.up.DeviceStateChange;
import com.enjoyiot.eiot.component.core.model.up.EventReport;
import com.enjoyiot.eiot.component.core.model.up.PropertyReport;
import com.enjoyiot.eiot.component.core.model.up.ServiceReply;
import com.enjoyiot.eiot.component.emqx.model.MqttConfig;
import com.enjoyiot.eiot.component.emqx.model.ThingDevice;
import com.enjoyiot.framework.common.util.collection.CollectionUtils;
import com.enjoyiot.framework.common.util.thread.ThreadUtil;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.device.dto.RegisterDevice;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.mqtt.MqttClient;
import io.vertx.mqtt.MqttClientOptions;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * mqtt官方协议文档：
 *
 * @author sjg
 */
@Slf4j
@Component
@Data
public class EmqxVerticle extends AbstractVerticle implements Runnable {

    @Resource
    private AuthVerticle authVerticle;

    @Resource
    private EmqxComponent emqxComponent;


    private String deployedId;

    private MqttConfig mqttConfig;


    private boolean mqttConnected = false;

    private boolean authServerStarted = false;

    private MqttClient client;

    @Resource
    private DeviceApi deviceApi;

    private Boolean taskStarted = Boolean.FALSE;

    private Boolean taskStop = Boolean.FALSE;


    public static final Map<String, Set<String>> CLIENT_DEVICE_MAP = new HashMap<>();

    private ScheduledThreadPoolExecutor emqxConnectTask = ThreadUtil.newScheduled(1, "emqx_connect");


    public void startServer(MqttConfig mqttConfig) {

        String serverPassword = IdUtil.fastSimpleUUID();
        MqttClientOptions options = new MqttClientOptions()
                .setClientId("server")
                .setUsername("server")
                .setPassword(serverPassword)
                .setCleanSession(true)
                .setMaxInflightQueue(10000)
                .setKeepAliveInterval(60);

        if (mqttConfig.isSsl()) {
            options.setSsl(true)
                    .setTrustAll(true);
        }

        this.mqttConfig = mqttConfig;
        client = MqttClient.create(vertx, options);


        authVerticle.setPort(mqttConfig.getAuthPort());
        authVerticle.setServerPassword(serverPassword);
        taskStop = false;
        if (!taskStarted) {
            taskStarted = true;
            emqxConnectTask.scheduleWithFixedDelay(this, 3, 3, TimeUnit.SECONDS);
        }

    }


    @Override
    public void run() {
        if (taskStop) {
            return;
        }
        if (!authServerStarted) {
            try {
                CountDownLatch countDownLatch = new CountDownLatch(1);
                Future<String> future = vertx.deployVerticle(authVerticle);
                future.onSuccess((s -> {
                    deployedId = s;
                    countDownLatch.countDown();
                    authServerStarted = true;
                    log.info("start emqx auth plugin success");
                }));
                future.onFailure(e -> {
                    countDownLatch.countDown();
                    authServerStarted = false;
                    log.error("start emqx auth plugin failed", e);
                });
                countDownLatch.await();
            } catch (Exception e) {
                authServerStarted = false;
                log.error("start emqx auth server failed", e);
            }
        }
        if (!authServerStarted) {
            return;
        }

        if (mqttConnected) {
            return;
        }

        try {
            String[] topics = mqttConfig.getTopics().split(",");
            Map<String, Integer> subscribes = new HashMap<>(topics.length);
            for (String topic : topics) {
                subscribes.put(topic, 1);
            }

            client.connect(mqttConfig.getPort(), mqttConfig.getHost(), s -> {
                if (s.succeeded()) {
                    log.info("client connect success.");
                    mqttConnected = true;
                    client.subscribe(subscribes, e -> {
                        if (e.succeeded()) {
                            log.info("===>subscribe success: {}", e.result());
                        } else {
                            log.error("===>subscribe fail: ", e.cause());
                        }
                    });

                } else {
                    mqttConnected = false;
                    log.error("client connect fail: ", s.cause());
                }
            }).publishHandler(msg -> {
                String topic = msg.topicName();
                if (topic.contains("/c/")) {
                    return;
                }

                JsonObject payload = msg.payload().toJsonObject();
                log.info("Client received message on [{}] payload [{}] with QoS [{}]", topic, payload, msg.qosLevel());

                try {
                    //客户端连接断开
                    if (topic.equals("/sys/client/disconnected")) {
                        offline(payload.getString("clientid"));
                        return;
                    }

                    ThingDevice device = getDevice(topic);
                    if (device == null) {
                        return;
                    }

                    //有消息上报-设备上线
//                    online(device.getProductKey(), device.getDeviceName());

                    JsonObject defParams = JsonObject.mapFrom(new HashMap<>(0));

                    String method = payload.getString("method", "");
                    if (StringUtils.isBlank(method)) {
                        return;
                    }
                    JsonObject params = payload.getJsonObject("params", defParams);
                    method = method.toLowerCase();
                    switch (method) {
                        case "thing.lifetime.register":
                            //子设备注册
                            String subPk = params.getString("productKey");
                            String subDn = params.getString("deviceName");
                            String subModel = params.getString("model");
                            try {
                                //注册设备
                                deviceApi.registerDevice(RegisterDevice.builder()
                                        .productKey(subPk)
                                        .deviceName(subDn)
                                        .model(subModel)
                                        .parentId(Long.valueOf(device.getDeviceId()))
                                        .build());
                                //注册成功
                                reply(topic, payload, 0);
                            } catch (Exception e) {
                                log.error("registerDevice error", e);
                                //注册失败
                                reply(topic, new JsonObject(), -1);
                            }
                            return;
                        case "thing.event.property.post":
                            //属性上报
                            emqxComponent.report(PropertyReport.builder()
                                    .productKey(device.getProductKey())
                                    .deviceName(device.getDeviceName())
                                    .params(params.getMap())
                                    .build());
                            reply(topic, payload, 0);
                            break;
                        default:
                            if (method.startsWith("thing.event.")) {
                                //事件上报
                                emqxComponent.report(EventReport.builder()
                                        .name(method.replace("thing.event.", ""))
                                        .params(params.getMap())
                                        .build());
                                reply(topic, payload, 0);
                            } else if (method.startsWith("thing.service.") && method.endsWith("_reply")) {
                                //服务回复
                                emqxComponent.report(ServiceReply.builder()
                                        .name(method.replaceAll("thing\\.service\\.(.*)_reply", "$1"))
                                        .code(payload.getInteger("code", 0))
                                        .params(params.getMap())
                                        .build());
                            }
                    }


                } catch (Exception e) {
                    log.error("message is illegal.", e);
                }
            }).closeHandler(e -> {
                mqttConnected = false;
                log.info("client closed:", e);
            }).exceptionHandler(event -> log.error("client fail", event));
        } catch (Exception e) {
            log.error("start emqx client failed", e);
        }

    }

    public ThingDevice getDevice(String topic) {
        String[] topicParts = topic.split("/");
        if (topicParts.length < 5) {
            return null;
        }
        return ThingDevice.builder()
                .productKey(topicParts[2])
                .deviceName(topicParts[3])
                .build();
    }


    public void online(String pk, String dn) {
        //上线
        emqxComponent.
                report(DeviceStateChange.builder()
                        .productKey(pk)
                        .deviceName(dn)
                        .state(DeviceState.ONLINE)
                        .build());
    }


    /**
     * 回复设备
     */
    private void replyArray(String topic, JsonObject payload, int code) {
        Map<String, Object> payloadReply = new HashMap<>();
        payloadReply.put("id", payload.getString("id"));
        payloadReply.put("method", payload.getString("method") + "_reply");
        payloadReply.put("code", code);
        payloadReply.put("data", payload.getJsonArray("params"));

        topic = topic.replace("/s/", "/c/") + "_reply";

        String finalTopic = topic;
        client.publish(topic, JsonObject.mapFrom(payloadReply).toBuffer(), MqttQoS.AT_MOST_ONCE, false, false)
                .onSuccess(h -> {
                    log.info("publish {} success", finalTopic);
                });
    }


    public void offline(String clientId) {
        String[] parts = clientId.split("_");
        if (parts.length < 2) {
            return;
        }
        Set<String> devices = CLIENT_DEVICE_MAP.get(parts[0] + parts[1]);
        if (CollectionUtil.isEmpty(devices)) {
            return;
        }
        for (String device : devices) {
            String[] pkDn = device.split(",");


            emqxComponent.report(DeviceStateChange.builder()
                    .productKey(pkDn[0])
                    .deviceName(pkDn[1])
                    .state(DeviceState.OFFLINE)
                    .build());
        }
    }


    /**
     * 回复设备
     */
    /**
     * 回复设备
     */
    private void reply(String topic, JsonObject payload, int code) {
        Map<String, Object> payloadReply = new HashMap<>();
        payloadReply.put("id", payload.getString("id"));
        payloadReply.put("method", payload.getString("method") + "_reply");
        payloadReply.put("code", code);
        payloadReply.put("data", payload.getJsonObject("params"));
        topic = topic.replace("/s/", "/c/") + "_reply";

        String finalTopic = topic;
        client.publish(topic, JsonObject.mapFrom(payloadReply).toBuffer(), MqttQoS.AT_MOST_ONCE, false, false)
                .onSuccess(h -> {
                    log.info("publish {} success", finalTopic);
                });
    }

    public void stopServer() {
        taskStop = true;
        try {
            log.info("emqx component close");
            if (deployedId != null) {
                CountDownLatch wait = new CountDownLatch(1);
                Future<Void> future = vertx.undeploy(deployedId);
                future.onSuccess(unused -> {
                    log.info("emqx component stopped success");
                    wait.countDown();
                    deployedId = null;
                    authServerStarted = false;
                });
                future.onFailure(h -> {
                    log.error("emqx component stopped failed", h);
                    wait.countDown();
                });
                wait.await(5, TimeUnit.SECONDS);
            }
            if (client != null) {
                client.disconnect()
                        .onSuccess(unused -> {
                            mqttConnected = false;
                            log.info("stop emqx connect success");
                        })
                        .onFailure(unused -> log.error("stop emqx connect failure"));

            }


        } catch (Throwable e) {
            log.error("emqx plugin stop error", e);
        }
    }

    @Override
    public void stop() {
        stopServer();
        // 停止
        if (emqxConnectTask != null && !emqxConnectTask.isShutdown()) {
            emqxConnectTask.shutdown();
        }
    }
}
