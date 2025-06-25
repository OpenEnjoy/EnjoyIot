package com.enjoyiot.eiot.component.coap.resource;

import com.enjoyiot.eiot.component.coap.handler.CoapRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;

/**
 * CoAP系统根资源
 * 处理所有 /sys/* 路径的请求 到动态资源类
 */
@Slf4j
public class CoapSysResource extends CoapResource {

    private final CoapRequestHandler requestHandler;

    public CoapSysResource(CoapRequestHandler requestHandler) {
        super("sys");
        this.requestHandler = requestHandler;
        setObservable(true);
        log.info("创建CoAP系统资源: /sys");
    }

    @Override
    public CoapResource getChild(String name) {
        // 为任何子路径请求返回一个动态资源
        log.info("请求子资源: /sys/{}", name);
        return new CoapDynamicResource(name, requestHandler);
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        log.info("收到对 /sys 的GET请求");
        requestHandler.handleRequest(exchange, "GET");
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        log.info("收到对 /sys 的POST请求");
        requestHandler.handleRequest(exchange, "POST");
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        log.info("收到对 /sys 的PUT请求");
        requestHandler.handleRequest(exchange, "PUT");
    }
}


