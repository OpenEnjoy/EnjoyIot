package com.enjoyiot.eiot.component.udp.service;

import com.enjoyiot.eiot.component.udp.dispatcher.UdpPacketDispatcher;
import com.enjoyiot.eiot.component.udp.model.UdpConfig;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.datagram.DatagramPacket;
import io.vertx.core.datagram.DatagramSocket;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Udp 启动与停止
 *@createtime 2025/5/22 13:28
 *@version 1.0
 *@author stuil
 */
@Slf4j
@Component
@Data
public class UdpVerticle extends AbstractVerticle {

    private DatagramSocket socket;
    private UdpComponent udpComponent;
    private final Map<String, Long> lastSeen = new ConcurrentHashMap<>();
    private static final long TIMEOUT_MS = 60_000;
    private static final long CLEAN_INTERVAL_MS = 30_000;



    public void startServer(UdpConfig config) {
        socket = vertx.createDatagramSocket();
        socket.listen(config.getPort(), config.getHost(), ar -> {
            if (ar.succeeded()) {
                log.info("UDP server listening on {}:{}", config.getHost(), config.getPort());
                // 注册业务处理
                socket.handler(this::handlePacket);
                // 定期清理超时会话
                vertx.setPeriodic(CLEAN_INTERVAL_MS, id -> cleanOffline());
            } else {
                log.error("UDP server start failed", ar.cause());
            }
        });
    }

    public void stopServer() {
        if (socket != null) {
            socket.close();
            log.info("UDP server stopped");
        }
    }

    private void handlePacket(DatagramPacket packet) {
        String key = packet.sender().host() + ":" + packet.sender().port();
        lastSeen.put(key, System.currentTimeMillis());
        UdpPacketDispatcher.dispatch(vertx, udpComponent, packet.data().getBytes());
    }

    private void cleanOffline() {
        long now = System.currentTimeMillis();
        lastSeen.forEach((key, ts) -> {
            if (now - ts > TIMEOUT_MS) {
                lastSeen.remove(key);
                log.info("UDP session timed out, offline {}", key);
                udpComponent.handleOffline(key);
            }
        });
    }
}
