
package com.enjoyiot.eiot.component.http;



import com.enjoyiot.eiot.component.http.service.HttpVerticle;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Component
public class HttpStarter {

    @Resource
    private HttpVerticle httpVerticle;

    @PostConstruct
    public void init() {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(httpVerticle, ar -> {
            if (ar.succeeded()) {
                log.info("start http component success!");
            } else {
                log.error("start http component failed", ar.cause());
            }
        });
    }
}
