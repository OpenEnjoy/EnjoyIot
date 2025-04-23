package com.enjoyiot.eiot.component.modbusCustom.parser;

import lombok.Data;

@Data
public class RegisterDataPackage extends DataPackage {
    private String dn;
    /**
     * 产品key
     */
    private String productKey;

    /**
     * modbus 从机id
     */
    private Byte slaveId;
}
