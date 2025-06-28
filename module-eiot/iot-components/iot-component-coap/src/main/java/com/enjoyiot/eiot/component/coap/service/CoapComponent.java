package com.enjoyiot.eiot.component.coap.service;

import com.enjoyiot.eiot.common.enums.DeviceState;
import com.enjoyiot.eiot.component.coap.model.CoapRequestInfo;
import com.enjoyiot.eiot.component.core.ComponentServices;
import com.enjoyiot.eiot.component.core.ThingComponent;
import com.enjoyiot.eiot.component.core.model.down.DeviceConfig;
import com.enjoyiot.eiot.component.core.model.down.PropertyGet;
import com.enjoyiot.eiot.component.core.model.down.PropertySet;
import com.enjoyiot.eiot.component.core.model.down.ServiceInvoke;
import com.enjoyiot.eiot.component.core.model.up.DeviceStateChange;
import com.enjoyiot.eiot.component.core.model.up.EventReport;
import com.enjoyiot.eiot.component.core.model.up.PropertyReport;
import com.enjoyiot.eiot.component.coap.model.CoapConfig;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.device.dto.DevicePropertyCache;
import com.enjoyiot.module.eiot.api.product.ProductApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class CoapComponent extends ThingComponent {

    private final CoapVerticle coapVerticle;
    private final ProductApi productApi;
    private final DeviceApi deviceApi;

    protected CoapComponent(CoapVerticle coapVerticle,
                            ProductApi productApi,
                            DeviceApi deviceApi,
                            ComponentServices componentServices) {
        super(componentServices);
        this.coapVerticle = coapVerticle;
        this.productApi = productApi;
        this.deviceApi = deviceApi;
    }

    @Override
    public String getType() {
        return "coap";
    }

    @Override
    public String getName() {
        return "内置官方CoAP协议组件";
    }

    @Override
    protected boolean stateChange(boolean enable, String config) {
        coapVerticle.setCoapComponent(this);

        if (!enable) {
            coapVerticle.stopServer();
            return true;
        }

        if (config == null) {
            log.error("CoAP配置不能为空");
            return false;
        }

        CoapConfig coapConfig = JsonUtils.parseObject(config, CoapConfig.class);
        if (coapConfig == null) {
            log.error("解析CoAP配置失败: {}", config);
            return false;
        }

        coapVerticle.stopServer();
        coapVerticle.startServer(coapConfig);
        return true;
    }

    @Override
    protected void serviceInvoke(ServiceInvoke action) {
        throw new UnsupportedOperationException("CoAP组件不支持服务调用");
    }

    @Override
    protected void propertyGet(PropertyGet action) {
        throw new UnsupportedOperationException("CoAP组件不支持属性获取下发");
    }

    @Override
    protected void propertySet(PropertySet action) {
        throw new UnsupportedOperationException("CoAP组件不支持属性设置下发");
    }

    @Override
    protected void deviceConfig(DeviceConfig action) {
        throw new UnsupportedOperationException("CoAP组件不支持设备配置下发");
    }

    /**
     * 统一的设备请求处理方法
     */
    public void processDeviceRequest(CoapExchange exchange, String method) {
        String requestId = cn.hutool.core.lang.UUID.fastUUID().toString();
        long startTime = System.currentTimeMillis();

        log.info("[{}] === 开始处理CoAP {}请求 ===", requestId, method);

        try {
            // 打印详细的请求信息
            logRequestDetails(exchange, requestId);

            // 解析请求路径和参数
            CoapRequestInfo requestInfo = parseRequestInfo(exchange, requestId);
            if (requestInfo == null) {
                return; // 错误已处理
            }

            // 设备认证
            DeviceInfo device = authenticateDevice(requestInfo, exchange, requestId);
            if (device == null) {
                return; // 错误已处理
            }

            // 上报设备上线
            reportDeviceOnline(requestInfo, requestId);

            // 处理具体业务
            handleBusinessLogic(exchange, method, requestInfo, device, requestId);

            log.info("[{}] CoAP请求处理完成，耗时: {}ms", requestId,
                    System.currentTimeMillis() - startTime);

        } catch (Exception e) {
            log.error("[{}] CoAP请求处理异常", requestId, e);
            respondWithError(exchange, CoAP.ResponseCode.INTERNAL_SERVER_ERROR,
                    "服务器内部错误: " + e.getMessage());
        }
    }

    private void logRequestDetails(CoapExchange exchange, String requestId) {
        log.info("[{}] === 请求详细信息 ===", requestId);
        log.info("[{}] 完整URI路径: {}", requestId, exchange.getRequestOptions().getUriPath());
        log.info("[{}] 查询参数: {}", requestId, exchange.getRequestOptions().getUriQuery());
        log.info("[{}] Content-Type: {}", requestId, exchange.getRequestOptions().getContentFormat());
        log.info("[{}] 请求负载长度: {}", requestId,
                exchange.getRequestText() != null ? exchange.getRequestText().length() : 0);

        String payload = exchange.getRequestText();
        if (StringUtils.isNotBlank(payload)) {
            log.info("[{}] 请求负载内容: {}", requestId, payload);
        }
        log.info("[{}] === 请求信息结束 ===", requestId);
    }

    private CoapRequestInfo parseRequestInfo(CoapExchange exchange, String requestId) {
        // 获取完整的URI路径
        // 期望格式: [sys, productKey, deviceName, properties] 或 [sys, productKey, deviceName, event, eventName]
        String[] pathSegments = exchange.getRequestOptions().getUriPath().toArray(new String[0]);

        log.info("[{}] 解析URI路径，段数: {}, 内容: {}", requestId, pathSegments.length,
                String.join("/", pathSegments));

        if (pathSegments.length < 4) {
            log.error("[{}] URI路径格式错误，期望: /sys/productKey/deviceName/type[/eventName]", requestId);
            respondWithError(exchange, CoAP.ResponseCode.BAD_REQUEST, "URI路径格式错误");
            return null;
        }

        // 提取secret参数
        String secret = null;
        for (String query : exchange.getRequestOptions().getUriQuery()) {
            if (query.startsWith("secret=")) {
                secret = query.substring("secret=".length());
                break;
            }
        }

        if (StringUtils.isBlank(secret)) {
            log.error("[{}] 缺少secret参数", requestId);
            respondWithError(exchange, CoAP.ResponseCode.UNAUTHORIZED, "缺少认证参数");
            return null;
        }

        String productKey = pathSegments[1];
        String deviceName = pathSegments[2];
        String type = pathSegments[3];
        String eventName = pathSegments.length > 4 ? pathSegments[4] : null;

        log.info("[{}] 解析结果 - ProductKey: {}, DeviceName: {}, Type: {}, EventName: {}",
                requestId, productKey, deviceName, type, eventName);

        return CoapRequestInfo.builder()
                .productKey(productKey)
                .deviceName(deviceName)
                .type(type)
                .eventName(eventName)
                .secret(secret)
                .payload(exchange.getRequestText())
                .build();
    }

    private DeviceInfo authenticateDevice(CoapRequestInfo requestInfo, CoapExchange exchange, String requestId) {
        log.info("[{}] 开始设备认证...", requestId);

        DeviceInfo device = deviceApi.getDeviceByPkDnByCache(requestInfo.getProductKey(), requestInfo.getDeviceName());

        if (device == null) {
            log.error("[{}] 设备不存在: {}/{}", requestId, requestInfo.getProductKey(), requestInfo.getDeviceName());
            respondWithError(exchange, CoAP.ResponseCode.UNAUTHORIZED, "设备不存在");
            return null;
        }

        if (!requestInfo.getSecret().equalsIgnoreCase(device.getSecret())) {
            log.error("[{}] Secret不匹配，期望: {}, 实际: {}",
                    requestId, device.getSecret(), requestInfo.getSecret());
            respondWithError(exchange, CoAP.ResponseCode.UNAUTHORIZED, "认证失败");
            return null;
        }

        log.info("[{}] 设备认证成功: {}/{}", requestId, requestInfo.getProductKey(), requestInfo.getDeviceName());
        return device;
    }

    private void reportDeviceOnline(CoapRequestInfo requestInfo, String requestId) {
        try {
            report(DeviceStateChange.builder()
                    .id(UUID.randomUUID().toString())
                    .productKey(requestInfo.getProductKey())
                    .deviceName(requestInfo.getDeviceName())
                    .state(DeviceState.ONLINE)
                    .time(System.currentTimeMillis())
                    .build());
            log.info("[{}] 设备上线状态已上报", requestId);
        } catch (Exception e) {
            log.warn("[{}] 设备上线状态上报失败", requestId, e);
        }
    }

    private void handleBusinessLogic(CoapExchange exchange, String method,
                                     CoapRequestInfo requestInfo, DeviceInfo device, String requestId) {

        log.info("[{}] 处理业务逻辑，类型: {}, 方法: {}", requestId, requestInfo.getType(), method);

        switch (requestInfo.getType()) {
            case "properties":
                handlePropertiesRequest(exchange, method, requestInfo, device, requestId);
                break;
            case "event":
                handleEventRequest(exchange, method, requestInfo, requestId);
                break;
            default:
                log.error("[{}] 不支持的请求类型: {}", requestId, requestInfo.getType());
                respondWithError(exchange, CoAP.ResponseCode.BAD_REQUEST,
                        "不支持的请求类型: " + requestInfo.getType());
        }
    }

    private void handlePropertiesRequest(CoapExchange exchange, String method,
                                         CoapRequestInfo requestInfo, DeviceInfo device, String requestId) {

        log.info("[{}] 处理属性请求，方法: {}", requestId, method);

        if ("POST".equalsIgnoreCase(method)) {
            handlePropertyReport(exchange, requestInfo, requestId);
        } else if ("GET".equalsIgnoreCase(method)) {
            handlePropertyGet(exchange, device, requestId);
        } else {
            log.error("[{}] 属性操作不支持的方法: {}", requestId, method);
            respondWithError(exchange, CoAP.ResponseCode.METHOD_NOT_ALLOWED,
                    "属性操作不支持" + method + "方法");
        }
    }

    private void handlePropertyReport(CoapExchange exchange, CoapRequestInfo requestInfo, String requestId) {
        log.info("[{}] 处理属性上报", requestId);

        try {
            if (StringUtils.isBlank(requestInfo.getPayload())) {
                log.error("[{}] 属性上报数据不能为空", requestId);
                respondWithError(exchange, CoAP.ResponseCode.BAD_REQUEST, "属性数据不能为空");
                return;
            }

            Map<String, Object> payloadMap = JsonUtils.parseObject(requestInfo.getPayload(), Map.class);
            if (payloadMap == null || !payloadMap.containsKey("params")) {
                log.error("[{}] 属性数据格式错误", requestId);
                respondWithError(exchange, CoAP.ResponseCode.BAD_REQUEST, "数据格式错误");
                return;
            }

            Map<String, Object> params = (Map<String, Object>) payloadMap.get("params");

            report(PropertyReport.builder()
                    .id(UUID.randomUUID().toString())
                    .productKey(requestInfo.getProductKey())
                    .deviceName(requestInfo.getDeviceName())
                    .params(params)
                    .time(System.currentTimeMillis())
                    .build());

            log.info("[{}] 属性上报成功，参数: {}", requestId, params);
            respondWithSuccess(exchange, "属性上报成功");

        } catch (Exception e) {
            log.error("[{}] 属性上报处理失败", requestId, e);
            respondWithError(exchange, CoAP.ResponseCode.BAD_REQUEST, "属性数据解析失败");
        }
    }

    private void handlePropertyGet(CoapExchange exchange, DeviceInfo device, String requestId) {
        log.info("[{}] 处理属性获取", requestId);

        try {
            Map<String, DevicePropertyCache> properties = deviceApi.getPropertiesFromCache(device.getId());

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("code", 0);
            responseData.put("message", "获取成功");
            responseData.put("data", properties);

            String response = JsonUtils.toJsonString(responseData);
            exchange.respond(CoAP.ResponseCode.CONTENT, response, MediaTypeRegistry.APPLICATION_JSON);

            log.info("[{}] 属性获取成功，返回数据: {}", requestId, response);

        } catch (Exception e) {
            log.error("[{}] 属性获取失败", requestId, e);
            respondWithError(exchange, CoAP.ResponseCode.INTERNAL_SERVER_ERROR, "属性获取失败");
        }
    }

    private void handleEventRequest(CoapExchange exchange, String method,
                                    CoapRequestInfo requestInfo, String requestId) {

        log.info("[{}] 处理事件请求", requestId);

        if (!"POST".equalsIgnoreCase(method)) {
            log.error("[{}] 事件上报只支持POST方法", requestId);
            respondWithError(exchange, CoAP.ResponseCode.METHOD_NOT_ALLOWED, "事件上报只支持POST方法");
            return;
        }

        if (StringUtils.isBlank(requestInfo.getEventName())) {
            log.error("[{}] 事件名称不能为空", requestId);
            respondWithError(exchange, CoAP.ResponseCode.BAD_REQUEST, "事件名称不能为空");
            return;
        }

        try {
            Map<String, Object> payloadMap = JsonUtils.parseObject(requestInfo.getPayload(), Map.class);
            String eventId = (String) payloadMap.get("id");
            Map<String, Object> params = (Map<String, Object>) payloadMap.get("params");

            report(EventReport.builder()
                    .productKey(requestInfo.getProductKey())
                    .deviceName(requestInfo.getDeviceName())
                    .name(requestInfo.getEventName())
                    .params(params)
                    .build());

            log.info("[{}] 事件上报成功: {}", requestId, requestInfo.getEventName());
            respondWithSuccess(exchange, "事件上报成功");

        } catch (Exception e) {
            log.error("[{}] 事件上报处理失败", requestId, e);
            respondWithError(exchange, CoAP.ResponseCode.BAD_REQUEST, "事件数据解析失败");
        }
    }

    private void respondWithSuccess(CoapExchange exchange, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("message", message);
        response.put("timestamp", System.currentTimeMillis());

        String responseJson = JsonUtils.toJsonString(response);
        exchange.respond(CoAP.ResponseCode.CONTENT, responseJson, MediaTypeRegistry.APPLICATION_JSON);
        log.info("响应成功: {}", responseJson);
    }

    private void respondWithError(CoapExchange exchange, CoAP.ResponseCode code, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", -1);
        response.put("message", message);
        response.put("timestamp", System.currentTimeMillis());

        String responseJson = JsonUtils.toJsonString(response);
        exchange.respond(code, responseJson, MediaTypeRegistry.APPLICATION_JSON);
        log.error("响应错误: {} - {}", code, responseJson);
    }
}