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
package com.enjoyiot.eiot.component.mqtt.service;


import com.enjoyiot.eiot.component.mqtt.model.MqttConfig;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.PemKeyCertOptions;
import io.vertx.mqtt.MqttServer;
import io.vertx.mqtt.MqttServerOptions;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * mqtt官方协议文档：
 *
 * @author sjg
 */
@Slf4j
@Component
@Data
public class MqttVerticle extends AbstractVerticle {

    private MqttServer mqttServer;

    private boolean closed = true;

    private MqttComponent mqttComponent;

    public void startServer(MqttConfig config) {
        MqttServerOptions options = new MqttServerOptions()
                .setPort(config.getPort());
        if (config.isSsl()) {
            options = options.setSsl(true)
                    .setKeyCertOptions(new PemKeyCertOptions()
                            .setKeyPath(config.getSslKey())
                            .setCertPath(config.getSslCert()));
        }
        options.setUseWebSocket(config.isUseWebSocket());

        mqttServer = MqttServer.create(vertx, options);
        mqttServer.endpointHandler(mqttComponent).listen(ar -> {
            if (ar.succeeded()) {
                closed = false;
                log.info("MQTT server is listening on port " + ar.result().actualPort());
            } else {
                log.error("Error on starting the server", ar.cause());
            }
        });
    }

    public void stopServer() {
        try {
            mqttComponent.offlineAll();
            if (mqttServer != null) {
                mqttServer.close();
                closed = true;
            }
        } finally {
            log.info("组件关闭...");
        }
    }

}
