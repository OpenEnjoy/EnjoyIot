package com.enjoyiot.eiot.component.udp.enums;


import java.util.LinkedHashMap;

/**
 * @description: 标签数据转发类型
 * @author: stuil
 * @version: 1.0
 */

public enum TagsForwardedTypeEnum implements GlobalAbstractBaseEnum<TagsForwardedTypeEnum,String,String> {
    WARNING("warning","告警数据"),
    //数据格式： display: 68,123456,43015,2025-04-23 10:49:21.909,0,0.11,11.01,1.50
    DISPLAY("display","位置数据"),
    // 数据格式：  status1: 85,TAG,123456,2025-04-23 10:56:51.458,92%,0,2,5,0,02,7E8E5,UT-241-C,3.2.09
    // 数据格式：  status1:120,ANC,123456,2025-04-23 10:56:51.807,192.168.1.10,UA-210-O,GD.V3.3.6.20220510(V2.0.08),0,5.39,7.86,2.55,939B6
    STATUS1("status1","状态"),

    ;

    private final String value;
    private final String text;


    TagsForwardedTypeEnum(String value, String text) {
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

    public static TagsForwardedTypeEnum getEnum(String value) {
        return GlobalAbstractBaseEnum.getEnum(TagsForwardedTypeEnum.class, value);
    }

    public static LinkedHashMap<String,String> enumToMap(){
        return GlobalAbstractBaseEnum.convertEnumToMap(TagsForwardedTypeEnum.class);
    }

    public String getPrefix() {
        return value + ":";
    }
}
