package com.enjoyiot.eiot.component.udp.dispatcher;

import com.enjoyiot.eiot.component.udp.service.UdpComponent;
import io.vertx.core.Vertx;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @title: UdpPacketDispatcher
 * @description:
 * @date: 2025/5/29
 * @author: stuil
 * @copyright: Copyright (c) 2025
 * @version: 1.0
 */

public class UdpPacketDispatcher {

    // 构造固定顺序的 Handler 列表
    private static final List<PacketHandler> HANDLERS = Arrays.asList(
            new JsonHandler(),
           // new UwbHandler(),
            new HexHandler(),
            new AsciiHandler(),
            new BinaryHandler()
    );

    public static void dispatch(final Vertx vertx, final UdpComponent component, final byte[] raw) {
        for (final PacketHandler h : HANDLERS) {
            if (h.matches(raw)) {
                vertx.executeBlocking(fut -> {
                    h.handle(vertx, component, raw);
                    fut.complete();
                }, false, null);
                return;
            }
        }
    }

    /** 策略接口：匹配 & 处理 */
    public interface PacketHandler {
        boolean matches(byte[] raw);
        void handle(Vertx vertx, UdpComponent comp, byte[] raw);
    }

    /** JSON 格式检测 & 处理 */
    private static class JsonHandler implements PacketHandler {
        @Override
        public boolean matches(byte[] raw) {
            int len = raw.length;
            return len >= 2
                    && ((raw[0] == '{' && raw[len - 1] == '}')
                    || (raw[0] == '[' && raw[len - 1] == ']'));
        }
        @Override
        public void handle(Vertx vertx, UdpComponent comp, byte[] raw) {
            comp.handleJson(new String(raw, StandardCharsets.UTF_8));
        }
    }

    /** UWB 前缀检测 & 处理 */
    private static class UwbHandler implements PacketHandler {
        private static final byte[][] PREFIXES = Arrays.stream(
                        com.enjoyiot.eiot.component.udp.enums.TagsForwardedTypeEnum.values()
                ).map(e -> e.getPrefix().getBytes(StandardCharsets.UTF_8))
                .toArray(byte[][]::new);

        @Override
        public boolean matches(byte[] raw) {
            for (byte[] p : PREFIXES) {
                if (raw.length < p.length) continue;
                boolean ok = true;
                for (int i = 0; i < p.length; i++) {
                    if (raw[i] != p[i]) {
                        ok = false;
                        break;
                    }
                }
                if (ok) return true;
            }
            return false;
        }
        @Override
        public void handle(Vertx vertx, UdpComponent comp, byte[] raw) {
            comp.handleUwb(new String(raw, StandardCharsets.UTF_8));
        }
    }

    /** HEX 文本检测 & 处理 */
    private static class HexHandler implements PacketHandler {
        @Override
        public boolean matches(byte[] raw) {
            if ((raw.length & 1) != 0) return false;
            for (byte b : raw) {
                int v = b & 0xFF;
                if (! ( (v >= '0' && v <= '9')
                        || (v >= 'A' && v <= 'F')
                        || (v >= 'a' && v <= 'f') ) ) {
                    return false;
                }
            }
            return true;
        }
        @Override
        public void handle(Vertx vertx, UdpComponent comp, byte[] raw) {
            comp.handleHex(new String(raw, StandardCharsets.UTF_8));
        }
    }

    /** ASCII 文本检测 & 处理 */
    private static class AsciiHandler implements PacketHandler {
        @Override
        public boolean matches(byte[] raw) {
            for (byte b : raw) {
                int v = b & 0xFF;
                if (v == 0x0A || v == 0x0D) continue;
                if (v < 32 || v > 126) {
                    return false;
                }
            }
            return true;
        }
        @Override
        public void handle(Vertx vertx, UdpComponent comp, byte[] raw) {
            comp.handleAscii(new String(raw, StandardCharsets.UTF_8));
        }
    }

    /** 二进制兜底处理 */
    private static class BinaryHandler implements PacketHandler {
        @Override
        public boolean matches(byte[] raw) {
            return true;
        }
        @Override
        public void handle(Vertx vertx, UdpComponent comp, byte[] raw) {
            comp.handleBinary(raw);
        }
    }

}
