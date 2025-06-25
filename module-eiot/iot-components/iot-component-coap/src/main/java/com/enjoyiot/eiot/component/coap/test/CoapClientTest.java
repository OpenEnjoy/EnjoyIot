package com.enjoyiot.eiot.component.coap.test;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CoapClientTest {

    private static final String SERVER_HOST = "127.0.0.1";
    private static final int SERVER_PORT = 5683; // 根据你的配置调整
    private static final String PRODUCT_KEY = "wSnpXDZJ3BQGrQmS";
    private static final String DEVICE_NAME = "PHCOAP1";
    private static final String SECRET = "gEdatxKkHMEZyO"; // 替换为实际的设备密钥

    public static void main(String[] args) {
        CoapClientTest test = new CoapClientTest();

        try {
            // 测试属性上报
            log.info("=== 测试属性上报 ===");
            test.testPropertyReport();

            Thread.sleep(1000);

            // 测试属性获取
            log.info("=== 测试属性获取 ===");
            test.testPropertyGet();

            Thread.sleep(1000);

            // 测试事件上报
            log.info("=== 测试事件上报 ===");
            test.testEventReport();

        } catch (Exception e) {
            log.error("测试过程中发生异常", e);
        }
    }

    /**
     * 测试属性上报
     */
    public void testPropertyReport() {
        String uri = String.format("coap://%s:%d/sys/%s/%s/properties?secret=%s",
                SERVER_HOST, SERVER_PORT, PRODUCT_KEY, DEVICE_NAME, SECRET);

        CoapClient client = new CoapClient(uri);

        // 构造属性上报数据
        String payload = "{\n" +
                "  \"params\": {\n" +
                "    \"temperature\": 38.6,\n" +
                "    \"humidity\": 11.5,\n" +
                "    \"status\": \"online\"\n" +
                "  }\n" +
                "}";

        try {
            CoapResponse response = client.post(payload, MediaTypeRegistry.APPLICATION_JSON);

            if (response != null) {
                log.info("属性上报响应: Code={}, Payload={}",
                        response.getCode(), response.getResponseText());
            } else {
                log.error("属性上报无响应");
            }
        } catch (Exception e) {
            log.error("属性上报失败", e);
        } finally {
            client.shutdown();
        }
    }

    /**
     * 测试属性获取
     */
    public void testPropertyGet() {
        String uri = String.format("coap://%s:%d/sys/%s/%s/properties?secret=%s",
                SERVER_HOST, SERVER_PORT, PRODUCT_KEY, DEVICE_NAME, SECRET);

        CoapClient client = new CoapClient(uri);

        try {
            CoapResponse response = client.get();

            if (response != null) {
                log.info("属性获取响应: Code={}, Payload={}",
                        response.getCode(), response.getResponseText());
            } else {
                log.error("属性获取无响应");
            }
        } catch (Exception e) {
            log.error("属性获取失败", e);
        } finally {
            client.shutdown();
        }
    }

    /**
     * 测试事件上报
     */
    public void testEventReport() {
        String eventName = "alarm";
        String uri = String.format("coap://%s:%d/sys/%s/%s/event/%s?secret=%s",
                SERVER_HOST, SERVER_PORT, PRODUCT_KEY, DEVICE_NAME, eventName, SECRET);

        CoapClient client = new CoapClient(uri);

        // 构造事件上报数据
        String payload = "{\n" +
                "  \"id\": \"event_" + System.currentTimeMillis() + "\",\n" +
                "  \"params\": {\n" +
                "    \"alarmType\": \"temperature_high\",\n" +
                "    \"level\": \"warning\",\n" +
                "    \"message\": \"温度过高警告\",\n" +
                "    \"value\": 85.2\n" +
                "  }\n" +
                "}";

        try {
            CoapResponse response = client.post(payload, MediaTypeRegistry.APPLICATION_JSON);

            if (response != null) {
                log.info("事件上报响应: Code={}, Payload={}",
                        response.getCode(), response.getResponseText());
            } else {
                log.error("事件上报无响应");
            }
        } catch (Exception e) {
            log.error("事件上报失败", e);
        } finally {
            client.shutdown();
        }
    }

    /**
     * 通用CoAP请求方法
     */
    public void sendCoapRequest(String uri, String method, String payload) {
        CoapClient client = new CoapClient(uri);

        try {
            CoapResponse response = null;

            switch (method.toUpperCase()) {
                case "GET":
                    response = client.get();
                    break;
                case "POST":
                    response = client.post(payload, MediaTypeRegistry.APPLICATION_JSON);
                    break;
                case "PUT":
                    response = client.put(payload, MediaTypeRegistry.APPLICATION_JSON);
                    break;
                default:
                    log.error("不支持的HTTP方法: {}", method);
                    return;
            }

            if (response != null) {
                log.info("CoAP {} 响应: Code={}, Payload={}",
                        method, response.getCode(), response.getResponseText());
            } else {
                log.error("CoAP {} 无响应", method);
            }

        } catch (Exception e) {
            log.error("CoAP {} 请求失败", method, e);
        } finally {
            client.shutdown();
        }
    }
}
