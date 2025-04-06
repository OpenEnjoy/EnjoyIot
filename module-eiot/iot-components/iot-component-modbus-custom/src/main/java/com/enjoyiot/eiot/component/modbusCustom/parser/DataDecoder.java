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
         * 1.注册包：r+设备序列号后5位
         * 2.心跳包：设备序列号后5位
         * 3.响应包：r+设备序列号后5位+modbus-tcp协议包
         * 这样保证每个数据包都携带设备序列号，便于后续数据处理
         */
        //首先判断第一个字节是不是字母r
        byte first = buffer.getByte(0);
        if (buffer.length() == 6 && first == 'r') {
            //是字母r，则认为是注册包
            RegisterDataPackage registerData = new RegisterDataPackage();
            registerData.setDn(buffer.getString(1, buffer.length(), "US-ASCII"));
            return registerData;
        }

        if (buffer.length() == 5) {
            //等于5个字节，认为是心跳包
            HeartbeatDataPackage heartbeatData = new HeartbeatDataPackage();
            heartbeatData.setDn(buffer.toString("US-ASCII"));
            return heartbeatData;
        }

        if (buffer.length() > 13) {
            //大于13个字节，认为是响应包
            ResponseDataPackage responseData = new ResponseDataPackage();
            responseData.setDn(buffer.getString(1, 6));
            try {

                MbapHeader header = MbapHeader.decode(buffer.getBuffer(6, 13).getByteBuf());
                responseData.setHeader(header);

                ModbusPdu pdu = decoder.decode(buffer.getBuffer(13, buffer.length()).getByteBuf());
                responseData.setPdu(pdu);
            } catch (Exception e) {
                log.error("解码失败", e);
            }
            return responseData;
        }
        return null;
    }

}
