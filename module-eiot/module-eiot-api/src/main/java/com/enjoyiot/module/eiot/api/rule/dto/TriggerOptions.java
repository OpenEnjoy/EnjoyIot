package com.enjoyiot.module.eiot.api.rule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 规则触发控制选项：
 * 1. 触发频率：minIntervalSec（秒），两个触发之间的最小间隔。
 * 2. 延时触发：delaySec（秒），匹配后延时执行动作。
 * 3. 告警解除：enableAlertRecover + recoverQuietSec（秒），在静默时间后自动触发解除动作。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TriggerOptions {

    /**
     * 触发最小间隔（秒），为空或小于等于 0 则不限制
     */
    private Integer minIntervalSec;

    /**
     * 延时触发时间（秒），为空或小于等于 0 则即时触发
     */
    private Integer delaySec;

    /**
     * 是否开启告警解除
     */
    private Boolean enableAlertRecover;

    /**
     * 从最后一次匹配失败开始计算的静默时间（秒），到达后触发告警解除动作
     */
    private Integer recoverQuietSec;
}

