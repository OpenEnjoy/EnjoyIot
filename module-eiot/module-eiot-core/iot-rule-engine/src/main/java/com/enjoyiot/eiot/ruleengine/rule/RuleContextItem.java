package com.enjoyiot.eiot.ruleengine.rule;

import lombok.Data;

@Data
public class RuleContextItem {
    /** 标识符 */
    private String identifier;
    /** 值 */
    private Object value;
    private String productKey;
    private String dn;
    private Long deviceId;
}
