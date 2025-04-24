package com.enjoyiot.eiot.component.modbusCustom.parser;

import lombok.Data;

@Data
public class HeartbeatDataPackage extends DataPackage{
    private String dn;
}
