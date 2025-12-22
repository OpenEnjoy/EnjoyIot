package com.enjoyiot.eiot.component.tcp.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class TcpTestClient {

    private static byte[] encode(String addr, short code, short mid, byte[] payload) {
        byte[] addrBytes = addr.getBytes(StandardCharsets.UTF_8);
        byte[] body = payload == null ? new byte[0] : payload;
        int length = 2 + addrBytes.length + 2 + 2 + body.length;
        ByteBuffer buf = ByteBuffer.allocate(4 + length).order(ByteOrder.BIG_ENDIAN);
        buf.putInt(length);
        buf.putShort((short) addrBytes.length);
        buf.put(addrBytes);
        buf.putShort(code);
        buf.putShort(mid);
        buf.put(body);
        return buf.array();
    }

    private static byte[] readPacket(InputStream in) throws Exception {
        byte[] header = readFully(in, 4);
        int len = ByteBuffer.wrap(header).order(ByteOrder.BIG_ENDIAN).getInt();
        return readFully(in, len);
    }

    private static short readCode(byte[] body) {
        int idx = 0;
        int addrLen = ByteBuffer.wrap(Arrays.copyOfRange(body, idx, idx + 2)).order(ByteOrder.BIG_ENDIAN).getShort();
        idx += 2 + addrLen;
        return ByteBuffer.wrap(Arrays.copyOfRange(body, idx, idx + 2)).order(ByteOrder.BIG_ENDIAN).getShort();
    }

    private static byte[] readPayload(byte[] body) {
        int idx = 0;
        int addrLen = ByteBuffer.wrap(Arrays.copyOfRange(body, idx, idx + 2)).order(ByteOrder.BIG_ENDIAN).getShort();
        idx += 2 + addrLen + 2 + 2;
        return Arrays.copyOfRange(body, idx, body.length);
    }

    private static int readStatus(byte[] body) {
        byte[] payload = readPayload(body);
        if (payload.length < 4) return -1;
        return ByteBuffer.wrap(payload).order(ByteOrder.BIG_ENDIAN).getInt();
    }

    private static byte[] readFully(InputStream in, int len) throws Exception {
        byte[] data = new byte[len];
        int off = 0;
        while (off < len) {
            int r = in.read(data, off, len - off);
            if (r < 0) throw new IllegalStateException("socket closed");
            off += r;
        }
        return data;
    }

    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "127.0.0.1";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 6666;
        String addr = args.length > 2 ? args[2] : "C00001";
        String productKey = args.length > 3 ? args[3] : "R755G5Wb3jst4tD7";
        int intervalSec = args.length > 4 ? Integer.parseInt(args[4]) : 5;
        String mode = args.length > 5 ? args[5] : "both";

        try (Socket socket = new Socket(host, port)) {
            socket.setSoTimeout(2000);
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();

            byte[] registerPayload = productKey.getBytes(StandardCharsets.UTF_8);
            byte[] registerPacket = encode(addr, (short) 0x10, (short) 1, registerPayload);
            out.write(registerPacket);
            out.flush();
            byte[] regAck = readPacket(in);
            int regStatus = readStatus(regAck);
            System.out.println("register ack status=" + regStatus);

            byte[] heartbeatPacket = encode(addr, (short) 0x20, (short) 2, new byte[0]);
            out.write(heartbeatPacket);
            out.flush();

            short mid = 3;
            boolean toggle = true;
            while (true) {
                if ("prop".equalsIgnoreCase(mode) || ("both".equalsIgnoreCase(mode) && toggle)) {
                    String propJson = "{\"model\":" + (20 + Math.round(Math.random() * 100) / 10.0) + ",\"remain\":" + (50 + (int) (Math.random() * 20)) + "}";
                    byte[] propPacket = encode(addr, (short) 0x30, mid++, propJson.getBytes(StandardCharsets.UTF_8));
                    out.write(propPacket);
                    out.flush();
                } else {
                    String evtJson = "{\"event\":\"alarm\",\"level\":" + (1 + (int) (Math.random() * 3)) + "}";
                    byte[] evtPacket = encode(addr, (short) 0x30, mid++, evtJson.getBytes(StandardCharsets.UTF_8));
                    out.write(evtPacket);
                    out.flush();
                }

                try {
                    socket.setSoTimeout(500);
                    byte[] down = readPacket(in);
                    short downCode = readCode(down);
                    if (downCode == 0x40) {
                        String downPayload = new String(readPayload(down), StandardCharsets.UTF_8);
                        System.out.println("property down received payload=" + downPayload);
                    }
                } catch (Exception ignore) {
                } finally {
                    socket.setSoTimeout(2000);
                }

                toggle = !toggle;
                Thread.sleep(intervalSec * 1000L);
            }
        }
    }
}
