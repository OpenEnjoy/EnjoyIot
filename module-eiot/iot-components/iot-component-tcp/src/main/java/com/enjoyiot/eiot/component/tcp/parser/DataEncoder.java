package com.enjoyiot.eiot.component.tcp.parser;

import io.vertx.core.buffer.Buffer;
import lombok.extern.slf4j.Slf4j;
import java.nio.charset.StandardCharsets;

/**
 * 数据编码
 *
 * @author lzh
 */
@Slf4j
public class DataEncoder {

    public static Buffer encode(DataPackage data) {
        Buffer buffer = Buffer.buffer();

        // 数据协议格式：包头(后续长度，4字节) | 设备地址长度（2字节） | 设备地址（不定长度） | 功能码（2字节） | 消息序号（2字节） | 包体(有效数据，不定长度)
        byte[] addrBytes = data.getAddr() == null ? new byte[0] : data.getAddr().getBytes(StandardCharsets.UTF_8);
        byte[] payloadBytes = data.getPayload() == null ? new byte[0] : data.getPayload();
        int length = 2 + addrBytes.length + 2 + 2 + payloadBytes.length;
        buffer.appendInt(length);
        buffer.appendShort((short) addrBytes.length);
        buffer.appendBytes(addrBytes);
        buffer.appendShort(data.getCode());
        buffer.appendShort(data.getMid());
        buffer.appendBytes(payloadBytes);
        return buffer;
    }
}
