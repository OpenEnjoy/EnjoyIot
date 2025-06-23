package com.enjoyiot.eiot.component.coap;


import com.enjoyiot.eiot.component.coap.service.CoapVerticle;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Component
public class CoapStarter {

    @Resource
    private CoapVerticle coapVerticle;

    @PostConstruct
    public void init() {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(coapVerticle, ar -> {
            if (ar.succeeded()) {
                log.info("start coap component success!");
            } else {
                log.error("start coap component failed", ar.cause());
            }
        });
    }
}