package com.enjoyiot.eiot.component.modbusCustom.parser;

import com.digitalpetri.modbus.ModbusPdu;
import com.digitalpetri.modbus.codec.MbapHeader;
import com.digitalpetri.modbus.codec.ModbusResponseDecoder;
import io.vertx.core.buffer.Buffer;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据解码
 *
 * @author lzh
 */
@Slf4j
public class DataDecoder {
    private static final ModbusResponseDecoder decoder = new ModbusResponseDecoder();

    public static DataPackage decode(Buffer buffer) {
        /**
         * 其完整包格式为：
         * 1.注册包：r_{productKey}_{dn}_{slaveId}
         * 2.心跳包：dn
         * 3.响应包：modbus-tcp协议包
         * dn为设备序列号后5位
         */
        String asciiStr = buffer.toString("US-ASCII");
        //首先判断是不是字母r_开头的数据包
        if (asciiStr.startsWith("r_")) {
            //是，则认为是注册包
            String[] split = asciiStr.split("_");
            if (split.length != 4) {
                return null;
            }
            RegisterDataPackage registerData = new RegisterDataPackage();
            registerData.setProductKey(split[1]);
            registerData.setDn(split[2]);
            registerData.setSlaveId(Byte.parseByte(split[3]));
            return registerData;
        }

        if (buffer.length() == 5) {
            //等于5个字节，认为是心跳包
            HeartbeatDataPackage heartbeatData = new HeartbeatDataPackage();
            heartbeatData.setDn(asciiStr);
            return heartbeatData;
        }

        if (buffer.length() > 7) {
            //大于7个字节，认为是响应包
            ResponseDataPackage responseData = new ResponseDataPackage();
            try {

                MbapHeader header = MbapHeader.decode(buffer.getBuffer(0, 7).getByteBuf());
                responseData.setHeader(header);

                ModbusPdu pdu = decoder.decode(buffer.getBuffer(7, buffer.length()).getByteBuf());
                responseData.setPdu(pdu);
            } catch (Exception e) {
                log.error("解码失败", e);
            }
            return responseData;
        }
        return null;
    }

}
