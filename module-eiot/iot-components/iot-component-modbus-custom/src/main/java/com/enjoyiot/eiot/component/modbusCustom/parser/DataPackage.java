package com.enjoyiot.eiot.component.modbusCustom.parser;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据包
 */
public abstract class DataPackage {
    @Getter
    @Setter
    protected String dn;
}
