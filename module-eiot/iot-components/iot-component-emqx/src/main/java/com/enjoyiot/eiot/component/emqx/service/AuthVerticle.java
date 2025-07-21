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


import com.enjoyiot.framework.common.pojo.CommonResult;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.device.dto.DeviceAuth;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class AuthVerticle extends AbstractVerticle {


    @Resource
    private DeviceApi deviceApi;

    private HttpServer backendServer;

    @Setter
    private int port;

    @Setter
    private String serverPassword;


    @Override
    public void start() {
        backendServer = vertx.createHttpServer();

        //第一步 声明Router&初始化Router
        Router backendRouter = Router.router(vertx);
        //获取body参数，得先添加这句
        backendRouter.route().handler(BodyHandler.create());

        //第二步 配置Router解析url
        backendRouter.route(HttpMethod.POST, "/mqtt/auth").handler(rc -> {
            JsonObject json = rc.getBodyAsJson();
            log.info("mqtt auth:{}", json);
            try {
                String clientId = json.getString("clientid");
                String username = json.getString("username");
                String password = json.getString("password");

                //服务端插件连接
                if (clientId.equals("server") && serverPassword.equals(password)) {
                    httpResult(rc.response(), true);
                    return;
                }

                //其它客户端连接
                String[] parts = clientId.split("_");
                if (parts.length < 3) {
                    log.error("clientid:{}不正确", clientId);
                    httpResult(rc.response(), false);
                    return;
                }

                log.info("MQTT client auth,clientId:{},username:{},password:{}",
                        clientId, username, password);

                String productKey = parts[0];
                String deviceName = parts[1];
                String gwModel = parts[2];
                if (!username.equals(deviceName)) {
                    log.error("username:{}不正确", deviceName);
                    httpResult(rc.response(), false);
                    return;
                }

                // 认证逻辑
                CommonResult<DeviceInfo> authResult = deviceApi.auth(DeviceAuth.builder().canRegister(true).clientId(clientId)
                        .userName(username).password(password).authType(DeviceAuth.AUTH_TYPE_MQTT).build());

                if (authResult.isError()) {
                    log.error("设备认证失败:{}", authResult.getMsg());
                    httpResult(rc.response(), false);
                    return;
                }


                Set<String> devices = new HashSet<>();
                devices.add(productKey + "," + deviceName);
                EmqxVerticle.CLIENT_DEVICE_MAP.putIfAbsent(productKey + deviceName, devices);

                httpResult(rc.response(), true);
            } catch (Throwable e) {
                httpResult(rc.response(), false);
                log.error("mqtt auth failed", e);
            }
        });
        backendRouter.route(HttpMethod.POST, "/mqtt/acl").handler(rc -> {
            JsonObject json = rc.getBodyAsJson();
            log.info("mqtt acl:{}", json);
            try {
                String clientId = json.getString("clientid");
                if ("server".equals(clientId)) {
                    httpResult(rc.response(), true);
                    return;
                }

                //设备端
                String[] parts = clientId.split("_");
                if (parts.length < 3) {
                    log.error("设备端cilentid格式异常");
                    httpResult(rc.response(), false);
                    return;
                }

                String topic = json.getString("topic");
                List<String> topicParts = Arrays.asList(topic.split("/"));
                if (!topicParts.contains(parts[0]) || !topicParts.contains(parts[1])) {
                    log.error("设备端cilentid和topic不一致");
                    httpResult(rc.response(), false);
                    return;
                }
                httpResult(rc.response(), true);
            } catch (Throwable e) {
                httpResult(rc.response(), false);
                log.error("mqtt acl failed", e);
            }
        });

        backendServer.requestHandler(backendRouter)
                .listen(port, "0.0.0.0")
                .onSuccess(s -> {
                    log.info("auth server start success,port:{}", s.actualPort());
                }).onFailure(e -> {
                    e.printStackTrace();
                })
        ;
    }

    private void httpResult(HttpServerResponse response, boolean allow) {
        response.putHeader("Content-Type", "application/json");
        response
                .setStatusCode(200);
        response
                .end("{\"result\": \"" + (allow ? "allow" : "deny") + "\"}");
    }

    @Override
    public void stop() throws Exception {
        backendServer.close(voidAsyncResult -> log.info("close emqx auth server..."));
    }
}
