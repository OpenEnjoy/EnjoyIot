package com.enjoyiot.eiot.component.coap.service;




import com.enjoyiot.eiot.component.coap.model.CoapConfig;
import com.enjoyiot.eiot.component.coap.resource.CoapSysResource;
import io.vertx.core.AbstractVerticle;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.springframework.stereotype.Component;

@Data
@Slf4j
@Component
public class CoapVerticle extends AbstractVerticle {

    private CoapComponent coapComponent;
    private CoapServer coapServer;

    public void startServer(CoapConfig config) {
        try {
            NetworkConfig networkConfig = NetworkConfig.createStandardWithoutFile();
            networkConfig.setInt(NetworkConfig.Keys.COAP_PORT, config.getPort());
            coapServer = new CoapServer(networkConfig);
            CoapSysResource sysResource = new CoapSysResource(coapComponent::processDeviceRequest);
            coapServer.add(sysResource);
            coapServer.start();
            log.info("CoAP服务启动成功，监听端口 {}", config.getPort());

        } catch (Exception e) {
            log.error("CoAP服务启动失败", e);
        }
    }

    public void stopServer() {
        if (coapServer != null) {
            try {
                coapServer.stop();
                log.info("CoAP服务器已成功关闭");
            } catch (Exception e) {
                log.error("关闭CoAP服务器失败", e);
            }
        } else {
            log.warn("CoAP服务器尚未启动或已关闭");
        }
    }
}
