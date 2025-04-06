package com.enjoyiot.module.eiot.api.modbus;

import com.enjoyiot.module.eiot.api.modbus.dto.ModbusInfo;

public interface ModbusInfoApi {
    ModbusInfo findByProductKey(String productKey);
}
