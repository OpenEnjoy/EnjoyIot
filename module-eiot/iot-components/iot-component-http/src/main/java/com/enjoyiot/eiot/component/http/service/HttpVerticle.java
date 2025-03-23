package com.enjoyiot.eiot.component.http.service;

import com.enjoyiot.eiot.component.http.model.HttpConfig;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.PemKeyCertOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Data
@Slf4j
@Component
public class HttpVerticle extends AbstractVerticle {

    private HttpComponent httpComponent;
    private HttpServer httpServer;

    public void startServer(HttpConfig config) {
        HttpServerOptions options = new HttpServerOptions()
                .setPort(config.getPort());
        if (config.isSsl()) {
            options = options.setSsl(true)
                    .setKeyCertOptions(new PemKeyCertOptions()
                            .setKeyPath(config.getSslKey())
                            .setCertPath(config.getSslCert()));
        }

        httpServer = vertx.createHttpServer(options);
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create()).handler(httpComponent);
        httpServer.requestHandler(router).listen(ar -> {
            if (ar.succeeded()) {
                log.info("HTTP服务启动成功，监听端口 {}", ar.result().actualPort());
            } else {
                log.error("HTTP服务启动失败", ar.cause());
            }
        });
    }

    public void stopServer() {
        if (httpServer != null) {
            httpServer.close(rst -> {
                if (rst.succeeded()) {
                    log.info("HTTP服务器已成功关闭");
                } else {
                    log.error("关闭HTTP服务器失败: {}", rst.cause().getMessage());
                }
            });
        } else {
            log.warn("HTTP服务器尚未启动或已关闭");
        }
    }
}
