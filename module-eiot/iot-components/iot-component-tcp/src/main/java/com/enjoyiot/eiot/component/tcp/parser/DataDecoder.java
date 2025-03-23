package com.enjoyiot.eiot.component.tcp.parser;

import io.vertx.core.buffer.Buffer;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据解码
 *
 * @author lzh
 */
@Slf4j
public class DataDecoder {

    public static DataPackage decode(Buffer buffer) {
        String string = buffer.toString();
        DataPackage data = new DataPackage();
        //获得设备号长度
        int index = 0;
        int bufferInt = Integer.parseInt(buffer.getString(index, index+2));
        data.setAddrLength(bufferInt);
        index += 2;
        data.setAddr(buffer.getBuffer(index, index + bufferInt).toString());
        index += bufferInt;
        data.setCode(Short.parseShort(buffer.getString(index, index + 2)));
        index += 2;
        data.setMid(Short.parseShort(buffer.getString(index, index + 2)));
        index += 2;
        data.setPayload(buffer.getString(index, buffer.length()));
        return data;
    }

}