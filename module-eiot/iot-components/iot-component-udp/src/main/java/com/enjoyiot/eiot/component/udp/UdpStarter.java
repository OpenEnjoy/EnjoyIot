package com.enjoyiot.eiot.component.udp;

import com.enjoyiot.eiot.component.udp.service.UdpVerticle;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * UdpStarter init
 *@createtime 2025/5/22 13:47
 *@version 1.0
 *@author stuil
 */
@Slf4j
@Component
public class UdpStarter {
    @Resource
    private UdpVerticle udpVerticle;

    @PostConstruct
    public void init() {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(udpVerticle, ar -> {
            if (ar.succeeded()) {
                log.info("start tcp component success!");
            } else {
                log.error("start tcp component failed", ar.cause());
            }
        });
    }
}
