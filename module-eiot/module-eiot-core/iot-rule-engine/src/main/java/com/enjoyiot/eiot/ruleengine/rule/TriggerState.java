package com.enjoyiot.eiot.ruleengine.rule;

import lombok.Data;

import java.io.Serializable;

/**
 * 规则触发运行态（存 Redis）
 */
@Data
public class TriggerState implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 是否处于告警中
     */
    private boolean alerting;

    /**
     * 待执行的恢复任务 token（防止旧任务误恢复）
     */
    private String recoverToken;
}

