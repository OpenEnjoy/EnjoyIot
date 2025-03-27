package com.enjoyiot.module.eiot.api.modbus;

import com.enjoyiot.module.eiot.api.modbus.dto.ModbusThingModel;

public interface ModbusThingModelApi {
    ModbusThingModel findByProductKey(String productKey);

    ModbusThingModel save(ModbusThingModel thingModel);
}
