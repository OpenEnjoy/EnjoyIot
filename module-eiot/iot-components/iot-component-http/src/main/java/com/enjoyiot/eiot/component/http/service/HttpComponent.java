package com.enjoyiot.eiot.component.http.service;

import com.enjoyiot.eiot.common.enums.DeviceState;
import com.enjoyiot.eiot.component.core.ComponentServices;
import com.enjoyiot.eiot.component.core.ThingComponent;
import com.enjoyiot.eiot.component.core.model.down.DeviceConfig;
import com.enjoyiot.eiot.component.core.model.down.PropertyGet;
import com.enjoyiot.eiot.component.core.model.down.PropertySet;
import com.enjoyiot.eiot.component.core.model.down.ServiceInvoke;
import com.enjoyiot.eiot.component.core.model.up.DeviceStateChange;
import com.enjoyiot.eiot.component.core.model.up.EventReport;
import com.enjoyiot.eiot.component.core.model.up.PropertyReport;
import com.enjoyiot.eiot.component.http.model.HttpConfig;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.device.dto.DevicePropertyCache;
import com.enjoyiot.module.eiot.api.product.ProductApi;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class HttpComponent extends ThingComponent implements Handler<RoutingContext> {

    private final HttpVerticle httpVerticle;

    private final ProductApi productApi;

    private final DeviceApi deviceApi;

    protected HttpComponent(HttpVerticle httpVerticle,
                            ProductApi productApi,
                            DeviceApi deviceApi,
                            ComponentServices componentServices
    ) {
        super(componentServices);
        this.httpVerticle = httpVerticle;
        this.productApi = productApi;
        this.deviceApi = deviceApi;
    }

    /**
     * 获取组件类型
     */
    @Override
    public String getType() {
        return "http";
    }

    /**
     * 组件名
     */
    @Override
    public String getName() {
        return "内置官方http协议组件";
    }
    /**
     * 组件状态变更
     *
     * @param enable 是否可用
     * @param config 组件配置
     */
    @Override
    protected boolean stateChange(boolean enable, String config) {
        httpVerticle.setHttpComponent(this);
        //停止组件
        if (!enable) {
            httpVerticle.stopServer();
            return true;
        }

        if (config == null) {
            return false;
        }

        HttpConfig httpConfig = JsonUtils.parseObject(config, HttpConfig.class);
        if (httpConfig == null) {
            log.error("parse json http config failed.");
            return false;
        }

        httpVerticle.stopServer();
        httpVerticle.startServer(httpConfig);
        return true;
    }

    @Override
    protected void serviceInvoke(ServiceInvoke action) {
        throw new UnsupportedOperationException("不支持该功能");
    }

    @Override
    protected void propertyGet(PropertyGet action) {
        throw new UnsupportedOperationException("不支持该功能");
    }

    @Override
    protected void propertySet(PropertySet action) {
        throw new UnsupportedOperationException("不支持该功能");
    }

    @Override
    protected void deviceConfig(DeviceConfig action) {
        throw new UnsupportedOperationException("不支持该功能");
    }

    @Override
    public void handle(RoutingContext ctx) {
        HttpServerResponse response = ctx.response();
        response.putHeader("content-type", "application/json");
        response.setStatusCode(200);

        try {
            String secret = ctx.request().getHeader("secret");
            if (StringUtils.isBlank(secret)) {
                log.error("secret不能为空");
                response.setStatusCode(401);
                end(response);
                return;
            }

            HttpServerRequest request = ctx.request();
            // /sys/{productKey}/{deviceName}/properties
            // /sys/{productKey}/{deviceName}/event/事件名
            String path = request.path();
            String[] parts = path.split("/");
            if (parts.length < 5) {
                log.error("不正确的路径");
                response.setStatusCode(500);
            }

            String productKey = parts[2];
            String deviceName = parts[3];
            String type = parts[4];
            DeviceInfo device = deviceApi.getDeviceByPkDnByCache(productKey, deviceName);
            if (device == null) {
                log.error("认证失败，设备:{} 不存在", deviceName);
                response.setStatusCode(401);
                end(response);
                return;
            }
            if (!secret.equalsIgnoreCase(device.getSecret())) {
                log.error("认证失败，secret不正确，期望值:{}", device.getSecret());
                response.setStatusCode(401);
                end(response);
                return;
            }

            //设备上线
            report(DeviceStateChange.builder()
                    .id(UUID.randomUUID().toString())
                    .productKey(productKey)
                    .deviceName(deviceName)
                    .state(DeviceState.ONLINE)
                    .time(System.currentTimeMillis())
                    .build());


            String method = request.method().name();
            JsonObject payload = ctx.body().asJsonObject();

            if ("event".equals(type)) {
                //事件上报
                if (!"POST".equalsIgnoreCase(method)) {
                    response.setStatusCode(500);
                    log.error("请求类型不正确，期望值:POST，实际值:{}", method);
                    end(response);
                }
                report(EventReport.builder()
                        .id(payload.getString("id"))
                        .productKey(productKey)
                        .deviceName(deviceName)
                        .name(parts[5])
                        .params(payload.getJsonObject("params").getMap())
                        .time(System.currentTimeMillis())
                        .build());

                end(response);
                return;
            }

            if ("properties".equals(type)) {
                if ("POST".equalsIgnoreCase(method)) {
                    //属性上报
                    report(PropertyReport.builder()
                            .id(UUID.randomUUID().toString())
                            .productKey(productKey)
                            .deviceName(deviceName)
                            .params(payload.getJsonObject("params").getMap())
                            .time(System.currentTimeMillis())
                            .build());

                    end(response);
                    return;
                }

                if ("GET".equalsIgnoreCase(method)) {
                    //属性获取
                    Map<String, DevicePropertyCache> property = deviceApi.getPropertiesFromCache(device.getId());
                    response.end(new JsonObject()
                            .put("code", 0)
                            .put("data", property)
                            .toString());
                }
            }
        } catch (Exception e) {
            log.error("消息处理失败", e);
            response.setStatusCode(500);
            end(response);
        }
    }

    private void end(HttpServerResponse response) {
        response.end(new JsonObject()
                .put("code", response.getStatusCode() == 200 ? 0 : response.getStatusCode())
                .toString());
    }
}
