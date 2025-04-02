package com.enjoyiot.eiot.component.modbusCustom.parser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDataPackage extends DataPackage {
    private byte slaveId;
    private short transactionId;
    private byte functionCode;
    private short address;
    private short quantity;
}
