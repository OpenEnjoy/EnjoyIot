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
        Integer length = 2+data.getAddr().length() + 2 + 2 + data.getPayload().length();
        buffer.appendBytes(new byte[]{(byte) length.intValue()});
        buffer.appendBytes(new byte[]{(byte) data.getAddr().length()});
        buffer.appendBytes(data.getAddr().getBytes());
        buffer.appendShort(data.getCode());
        buffer.appendShort(data.getMid());
        buffer.appendBytes(data.getPayload().getBytes());
        return buffer;
    }
}
