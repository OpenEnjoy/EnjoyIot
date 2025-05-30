package com.enjoyiot.eiot.component.udp.client;

import com.alibaba.fastjson.JSON;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: UDPClient
 * @description:
 * @date: 2025/5/30
 * @author: stuil
 * @copyright: Copyright (c) 2025
 * @version: 1.0
 */

public class UDPClient {
    private final String host;
    private final int port;

    public UDPClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 发送字符串消息
     * @param message 待发送内容
     * @throws Exception 发送异常
     */
    public void send(String message) throws Exception {
        byte[] data = message.getBytes("UTF-8");
        InetAddress address = InetAddress.getByName(host);

        try (DatagramSocket socket = new DatagramSocket()) {
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            socket.send(packet);
            System.out.println("UDP 消息已发送到 " + host + ":" + port + " -> " + message);
        }
    }

    // 简单测试
    public static void main(String[] args) {
        try {
            UDPClient sender = new UDPClient("127.0.0.1", 7101);
            Map<String, Object> map = new HashMap<>();
            map.put("deviceId", "123456789");
            map.put("name", "UDP");
            map.put("age", 18);
            sender.send(JSON.toJSONString(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
