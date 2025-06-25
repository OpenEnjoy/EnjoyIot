package com.enjoyiot.eiot.component.coap.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CoAP请求信息封装
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoapRequestInfo {
    private String productKey;
    private String deviceName;
    private String type;
    private String eventName;
    private String secret;
    private String payload;
}
