/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */
package com.enjoyiot.eiot.ruleengine.rule;


import com.enjoyiot.eiot.IRuleLogData;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.ruleengine.action.Action;
import com.enjoyiot.eiot.ruleengine.action.alert.AlertAction;
import com.enjoyiot.eiot.ruleengine.filter.Filter;
import com.enjoyiot.eiot.ruleengine.listener.Listener;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.module.eiot.api.rule.dto.RuleLog;
import com.enjoyiot.module.eiot.api.rule.dto.TriggerOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 规则执行器
 */
@Component
@Slf4j
public class RuleExecutor {

    @Lazy
    @Autowired
    private IRuleLogData ruleLogData;

    @Autowired
    private TriggerControlService triggerControlService;

    public void execute(ThingModelMessage message, Rule rule) {
        TriggerOptions options = rule.getTriggerOptions();

        if (!doListeners(message, rule)) {
            log.info("The listener did not match the appropriate content,rule:{},{}", rule.getId(), rule.getName());
            // 监听器不匹配时，如果开启了告警解除，调度恢复
            triggerControlService.scheduleRecoverIfNeeded(rule, message, options);
            return;
        }

        if (!doFilters(rule, message)) {
            recordLog(rule.getId(), RuleLog.STATE_UNMATCHED_FILTER, null, false);
            // 过滤器不匹配时，如果开启了告警解除，调度恢复
            triggerControlService.scheduleRecoverIfNeeded(rule, message, options);
            return;
        }

        // 匹配成功，取消恢复任务
        triggerControlService.cancelRecover(rule.getId());

        // 限频判断
        if (!triggerControlService.passRateLimit(rule.getId(), options)) {
            log.info("rule {} skipped by minIntervalSec", rule.getId());
            return;
        }

        // 执行动作：有延时入队列，无延时直接执行
        triggerControlService.executeAction(rule, message, options, this);
    }

    private boolean doListeners(ThingModelMessage message, Rule rule) {
        List<Listener<?>> listeners = rule.getListeners();
        for (Listener<?> listener : listeners) {
            if (listener.execute(message)) {
                //只要有一个监听器匹配到数据即可
                return true;
            }
        }
        return false;
    }

    private boolean doFilters(Rule rule, ThingModelMessage msg) {
        List<Filter<?>> filters = rule.getFilters();
        for (Filter<?> filter : filters) {
            //只要有一个过滤器未通过都不算通过
            if (!filter.execute(msg)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 执行动作（可被外部调用，用于延时队列消费）
     */
    void executeActions(Rule rule, ThingModelMessage message, boolean recovery) {
        RuleLog ruleLog = new RuleLog();
        ruleLog.setRuleId(rule.getId());
        ruleLog.setState(recovery ? RuleLog.STATE_RECOVERED : RuleLog.STATE_MATCHED_FILTER);
        try {
            List<String> results = doActions(rule, message, recovery);
            ruleLog.setContent(JsonUtils.toJsonString(results));
            if (!recovery) {
                ruleLog.setState(RuleLog.STATE_EXECUTED_ACTION);
            }
            ruleLog.setSuccess(true);
            // 状态标记：恢复时标记恢复，触发时由调用方标记（避免重复）
            if (recovery) {
                triggerControlService.markRecovered(rule.getId());
                log.info("rule {} alert recovered", rule.getId());
            } else {
                log.info("rule execution completed,id:{}", rule.getId());
            }
        } catch (Throwable e) {
            log.error("rule execution error,id:" + rule.getId(), e);
            ruleLog.setSuccess(false);
            ruleLog.setContent(e.toString());
        } finally {
            ruleLog.setLogAt(System.currentTimeMillis());
            ruleLogData.add(ruleLog);
        }
    }

    private List<String> doActions(Rule rule, ThingModelMessage msg, boolean recovery) {
        List<String> results = new ArrayList<>();
        for (Action<?> action : rule.getActions()) {
            if (recovery) {
                if (action instanceof AlertAction) {
                    results.addAll(((AlertAction) action).recover(msg));
                }
                continue;
            }
            results.addAll(action.execute(msg));
        }
        return results;
    }

    private void recordLog(Long ruleId, String state, String content, Boolean success) {
        RuleLog ruleLog = new RuleLog();
        ruleLog.setRuleId(ruleId);
        ruleLog.setState(state);
        ruleLog.setContent(content);
        ruleLog.setSuccess(success);
        ruleLog.setLogAt(System.currentTimeMillis());
        ruleLogData.add(ruleLog);
    }

}
