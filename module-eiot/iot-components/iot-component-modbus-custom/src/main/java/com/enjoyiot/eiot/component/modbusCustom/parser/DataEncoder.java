package com.enjoyiot.eiot.component.modbusCustom.parser;

import io.vertx.core.buffer.Buffer;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据编码
 *
 * @author lzh
 */
@Slf4j
public class DataEncoder {

    public static Buffer encode(RequestDataPackage data) {
        Buffer buffer = Buffer.buffer();
        buffer.appendShort(data.getTransactionId()); //事务标识符	2字节
        buffer.appendShort((short) 0); //协议标识符	2字节	Modbus固定为0x0000
        buffer.appendShort((short) 6); //长度字段	2字节   后续PDU部分的字节数（从单元标识符到数据结束）
        buffer.appendByte(data.getSlaveId()); //单元标识符	1字节
        buffer.appendByte(data.getFunctionCode()); //功能码	1字节
        buffer.appendShort(data.getAddress()); //寄存器地址	2字节
        buffer.appendShort(data.getQuantity()); //寄存器数量	2字节
        return buffer;
    }
}
