package com.enjoyiot.eiot.component.coap.resource;

import com.enjoyiot.eiot.component.coap.handler.CoapRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;

/**
 * CoAP系统子资源
 * 实际处理所有 /sys/* 路径的请求
 */
@Slf4j
public class CoapDynamicResource extends CoapResource {
    private final CoapRequestHandler requestHandler;

    public CoapDynamicResource(String name, CoapRequestHandler requestHandler) {
        super(name);
        this.requestHandler = requestHandler;
        log.info("创建动态CoAP资源: {}", name);
    }

    @Override
    public CoapResource getChild(String name) {
        log.info("请求动态子资源: {}", name);
        return new CoapDynamicResource(name, requestHandler);
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        log.info("动态资源收到GET请求");
        requestHandler.handleRequest(exchange, "GET");
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        log.info("动态资源收到POST请求");
        requestHandler.handleRequest(exchange, "POST");
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        log.info("动态资源收到PUT请求");
        requestHandler.handleRequest(exchange, "PUT");
    }
}
