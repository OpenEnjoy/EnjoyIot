package com.enjoyiot.eiot.ruleengine.rule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 延时队列任务载体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TriggerJob implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long ruleId;
    private String messageJson;
    private boolean recovery;
    private String recoverToken;

    private Long tenantId;
}

