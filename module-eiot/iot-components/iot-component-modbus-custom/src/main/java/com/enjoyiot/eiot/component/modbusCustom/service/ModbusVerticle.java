
package com.enjoyiot.eiot.component.modbusCustom.service;


import com.enjoyiot.eiot.component.modbusCustom.model.ModbusConfig;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 *  TCP 启动与停止
 *
 * @author lzh
 */
@Slf4j
@Component
@Data
public class ModbusVerticle extends AbstractVerticle {

    private ModbusComponent modbusComponent;
    private NetServer netServer;

    public void startServer(ModbusConfig config) {
        NetServerOptions options = new NetServerOptions()
                .setPort(config.getPort());

        netServer = vertx.createNetServer(options);
        modbusComponent.setModbusServer(netServer, vertx);

        netServer.listen(ar -> {
            if (ar.succeeded()) {
                log.info("MODBUS服务启动成功，监听端口 {}", ar.result().actualPort());
            } else {
                log.error("MODBUS服务启动失败", ar.cause());
            }
        });
    }

    public void stopServer() {
        if (netServer != null) {
            netServer.close(rst -> {
                if (rst.succeeded()) {
                    log.info("MODBUS服务器已成功关闭");
                } else {
                    log.error("关闭MODBUS服务器失败: {}", rst.cause().getMessage());
                }
            });
        } else {
            log.warn("MODBUS服务器尚未启动或已关闭");
        }
    }
}
