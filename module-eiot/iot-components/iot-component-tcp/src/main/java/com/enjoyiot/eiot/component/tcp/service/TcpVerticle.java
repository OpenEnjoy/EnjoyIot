
package com.enjoyiot.eiot.component.tcp.service;


import com.enjoyiot.eiot.component.tcp.model.TcpConfig;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
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
public class TcpVerticle extends AbstractVerticle {

    private TcpComponent tcpComponent;
    private NetServer netServer;

    public void startServer(TcpConfig config) {
        NetServerOptions options = new NetServerOptions()
                .setPort(config.getPort())
                .setHost(config.getHost());

        Promise<NetServer> promise = Promise.promise();
        netServer = vertx.createNetServer(options);
        tcpComponent.setTcpServer(netServer, vertx);

        netServer.listen(ar -> {
            if (ar.succeeded()) {
                log.info("TCP服务启动成功，监听端口 {}", ar.result().actualPort());
                promise.complete(ar.result());
            } else {
                log.error("TCP服务启动失败", ar.cause());
                promise.fail(ar.cause());
            }
        });

        promise.future().onFailure(throwable -> {
            log.error("启动TCP服务器失败: {}", throwable.getMessage());
            // 处理启动失败的情况，例如尝试重新启动或通知管理员
        });
    }

    public void stopServer() {
        if (netServer != null) {
            netServer.close(rst -> {
                if (rst.succeeded()) {
                    log.info("TCP服务器已成功关闭");
                } else {
                    log.error("关闭TCP服务器失败: {}", rst.cause().getMessage());
                }
            });
        } else {
            log.warn("TCP服务器尚未启动或已关闭");
        }
    }
}
