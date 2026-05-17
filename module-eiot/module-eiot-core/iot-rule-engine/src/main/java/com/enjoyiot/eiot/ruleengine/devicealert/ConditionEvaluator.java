package com.enjoyiot.eiot.ruleengine.devicealert;

import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.module.eiot.api.device.dto.DevicePropertyCache;
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
    private static final String STATUS_KEY_ONLINE = "online";

    /**
     * 评估条件（仅基于当前消息，不合并缓存）
     */
    public boolean evaluate(List<DeviceAlertConfig.AlertCondition> conditions, ThingModelMessage message) {
        if (conditions == null || conditions.isEmpty()) {
            return false;
        }
        Map<String, Object> dataMap = messageToMap(message);
        return evaluateConditions(conditions, dataMap, null);
    }

    /**
     * 评估条件（合并设备缓存属性和在线状态）
     *
     * @param conditions  条件列表
     * @param message     当前消息
     * @param cachedProps 设备缓存属性（可为null）
     * @param isOnline    设备是否在线（可为null）
     * @param logic       逻辑 AND/OR（默认AND）
     */
    public boolean evaluate(List<DeviceAlertConfig.AlertCondition> conditions,
                            ThingModelMessage message,
                            Map<String, DevicePropertyCache> cachedProps,
                            Boolean isOnline,
                            String logic) {
        if (conditions == null || conditions.isEmpty()) {
            return false;
        }

        // 合并数据：缓存属性 + 当前消息属性（当前消息属性覆盖缓存）
        Map<String, Object> dataMap = new HashMap<>();

        // 添加缓存属性
        if (cachedProps != null) {
            cachedProps.forEach((key, cache) -> {
                if (cache != null && cache.getValue() != null) {
                    dataMap.put(key, cache.getValue());
                }
            });
        }

        // 添加当前消息属性（覆盖缓存）
        Map<String, Object> messageData = messageToMap(message);
        dataMap.putAll(messageData);

        // 添加设备在线状态
        if (isOnline != null) {
            dataMap.put(STATUS_KEY_ONLINE, isOnline ? "online" : "offline");
        }

        return evaluateConditions(conditions, dataMap, logic);
    }

    private boolean evaluateConditions(List<DeviceAlertConfig.AlertCondition> conditions,
                                       Map<String, Object> dataMap,
                                       String logic) {
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

        // 根据逻辑决定返回值
        if (LOGIC_OR.equalsIgnoreCase(logic)) {
            // OR: 任一条件满足即为真
            return hasTrue;
        } else {
            // AND (默认): 所有条件都满足才为真
            return hasTrue && !hasFalse;
        }
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
            actualValue = dataMap.get(STATUS_KEY_ONLINE);
            // status 类型判断设备在线状态
            if (actualValue == null) {
                return false;
            }
            String onlineStatus = String.valueOf(actualValue);
            return compare(onlineStatus, operator, value);
        }
        return false;
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