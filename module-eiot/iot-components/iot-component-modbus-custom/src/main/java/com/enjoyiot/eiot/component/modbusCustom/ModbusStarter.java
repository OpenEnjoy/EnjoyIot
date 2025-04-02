package com.enjoyiot.eiot.component.modbusCustom;

import com.enjoyiot.eiot.component.modbusCustom.service.ModbusVerticle;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Component
public class ModbusStarter {
    @Resource
    private ModbusVerticle modbusVerticle;

    @PostConstruct
    public void init() {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(modbusVerticle, ar -> {
            if (ar.succeeded()) {
                log.info("start modbus[custom] component success!");
            } else {
                log.error("start modbus[custom] component failed", ar.cause());
            }
        });
    }
}
