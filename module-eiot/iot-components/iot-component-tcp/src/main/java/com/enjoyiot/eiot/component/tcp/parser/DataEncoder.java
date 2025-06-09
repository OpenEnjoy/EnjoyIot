package com.enjoyiot.eiot.component.tcp.parser;

import io.vertx.core.buffer.Buffer;
import lombok.extern.slf4j.Slf4j;

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
        Integer length = 2+data.getAddr().length() + 2 + 2 + data.getPayload().length();
        buffer.appendInt(length.intValue());
        buffer.appendShort((short) data.getAddr().length());
        buffer.appendBytes(data.getAddr().getBytes());
        buffer.appendShort(data.getCode());
        buffer.appendShort(data.getMid());
        buffer.appendBytes(data.getPayload().getBytes());
        return buffer;
    }
}
