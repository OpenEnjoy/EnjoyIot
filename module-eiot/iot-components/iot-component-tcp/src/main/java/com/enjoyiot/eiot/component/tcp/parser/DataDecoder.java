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
        //获得设备号长度(2字节)
        int index = 0;
        int bufferInt = buffer.getShort(0);
        data.setAddrLength(bufferInt);
        // 获取设备号
        index += 2;
        data.setAddr(buffer.getBuffer(index, index + bufferInt).toString());
        // 获取功能码(2字节)
        index += bufferInt;
        data.setCode(buffer.getShort(index));
        // 获取消息序号(2字节)
        index += 2;
        data.setMid((buffer.getShort(index)));
        // 获取包体(数据实体)
        index += 2;
        data.setPayload(buffer.getString(index, buffer.length()));
        return data;
    }

}