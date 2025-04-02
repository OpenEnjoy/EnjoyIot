package com.enjoyiot.eiot.component.modbusCustom.parser;

import com.digitalpetri.modbus.ModbusPdu;
import com.digitalpetri.modbus.codec.MbapHeader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDataPackage extends DataPackage {
    MbapHeader header;
    ModbusPdu pdu;
}
