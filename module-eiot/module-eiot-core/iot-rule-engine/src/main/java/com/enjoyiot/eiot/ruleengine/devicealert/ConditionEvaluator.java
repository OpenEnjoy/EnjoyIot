package com.enjoyiot.eiot.ruleengine.devicealert;

import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.module.eiot.api.devicealert.dto.DeviceAlertConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConditionEvaluator {

    private static final String LOGIC_AND = "AND";
    private static final String LOGIC_OR = "OR";

    public boolean evaluate(List<DeviceAlertConfig.AlertCondition> conditions, ThingModelMessage message) {
        if (conditions == null || conditions.isEmpty()) {
            return false;
        }

        Map<String, Object> dataMap = messageToMap(message);

        boolean hasTrue = false;
        boolean hasFalse = false;

        for (DeviceAlertConfig.AlertCondition condition : conditions) {
            boolean result = evaluateSingle(condition, dataMap);
            if (result) {
                hasTrue = true;
            } else {
                hasFalse = true;
            }
        }

        return hasTrue && !hasFalse;
    }

    private boolean evaluateSingle(DeviceAlertConfig.AlertCondition condition, Map<String, Object> dataMap) {
        if (condition == null || condition.getKey() == null) {
            return false;
        }

        String type = condition.getType();
        String key = condition.getKey();
        String operator = condition.getOperator();
        String value = condition.getValue();

        Object actualValue = null;
        if ("property".equals(type)) {
            actualValue = dataMap.get(key);
            return compare(String.valueOf(actualValue), operator, value);

        } else if ("status".equals(type)) {
            actualValue = dataMap.get(key);
            // TODO: 状态判断
            return true;
        }
        return true;

    }

    private boolean compare(String actual, String operator, String expected) {
        if (actual == null || operator == null) {
            return false;
        }

        switch (operator) {
            case ">":
                return compareNumeric(actual, expected) > 0;
            case "<":
                return compareNumeric(actual, expected) < 0;
            case ">=":
                return compareNumeric(actual, expected) >= 0;
            case "<=":
                return compareNumeric(actual, expected) <= 0;
            case "==":
                return actual.equals(expected);
            case "!=":
                return !actual.equals(expected);
            case "contains":
                return actual.contains(expected);
            case "not_contains":
                return !actual.contains(expected);
            default:
                return false;
        }
    }

    private int compareNumeric(String actual, String expected) {
        try {
            double actualNum = Double.parseDouble(actual);
            double expectedNum = Double.parseDouble(expected);
            return Double.compare(actualNum, expectedNum);
        } catch (NumberFormatException e) {
            return actual.compareTo(expected);
        }
    }

    private Map<String, Object> messageToMap(ThingModelMessage message) {
        Map<String, Object> map = new HashMap<>();
        if (message.getData() instanceof Map) {
            ((Map<?, ?>) message.getData()).forEach((k, v) -> map.put(String.valueOf(k), v));
        }
        return map;
    }
}
