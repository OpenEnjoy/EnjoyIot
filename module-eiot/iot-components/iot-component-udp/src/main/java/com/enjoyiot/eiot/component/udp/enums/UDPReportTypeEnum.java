package com.enjoyiot.eiot.component.udp.enums;


import java.util.LinkedHashMap;

/**
 * @description: UDP上报类型枚举
 * @author: stuil
 * @version: 1.0
 */

public enum UDPReportTypeEnum implements GlobalAbstractBaseEnum<UDPReportTypeEnum,String,String> {
    EVENT_REPORT("eventReport","事件上报"),
    PROPERTY_REPORT("propertyReport","属性上报"),

    ;

    private final String value;
    private final String text;


    UDPReportTypeEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getText() {
        return text;
    }

    public static UDPReportTypeEnum getEnum(String value) {
        return GlobalAbstractBaseEnum.getEnum(UDPReportTypeEnum.class, value);
    }

    public static LinkedHashMap<String,String> enumToMap(){
        return GlobalAbstractBaseEnum.convertEnumToMap(UDPReportTypeEnum.class);
    }
}
