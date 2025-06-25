package com.enjoyiot.eiot.component.coap.handler;


import org.eclipse.californium.core.server.resources.CoapExchange;

/**
 * CoAP请求处理器接口
 */
@FunctionalInterface
public interface CoapRequestHandler {
    void handleRequest(CoapExchange exchange, String method);
}
