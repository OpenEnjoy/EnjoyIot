
package com.enjoyiot.eiot.component.tcp;


import com.enjoyiot.eiot.component.tcp.service.TcpVerticle;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Component
public class TcpStarter {

    @Resource
    private TcpVerticle tcpVerticle;

    @PostConstruct
    public void init() {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(tcpVerticle, ar -> {
            if (ar.succeeded()) {
                log.info("start tcp component success!");
            } else {
                log.error("start tcp component failed", ar.cause());
            }
        });
    }
}