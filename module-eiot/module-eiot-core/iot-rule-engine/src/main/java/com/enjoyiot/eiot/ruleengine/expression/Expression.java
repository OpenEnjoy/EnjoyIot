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
package com.enjoyiot.eiot.ruleengine.expression;


import com.enjoyiot.eiot.engine.IScriptEngine;
import com.enjoyiot.eiot.engine.ScriptEngineFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static com.enjoyiot.eiot.common.enums.ErrorCodeConstants.DATA_BLANK;
import static com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * @author sjg
 */
@Slf4j
public class Expression {


    /**
     * 校验字符串是否为有效数字
     * @param str 待校验字符串
     * @return 是否为有效数字
     */
    private static boolean isValidNumber(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean eval(String comparator, String value,  String triggerValue) {
        boolean result = false;
        // 操作符比较
        switch (comparator) {
            case "==":
                result = value.equals(triggerValue);
                break;
            case "!=":
                result = !value.equals(triggerValue);
                break;
            case ">":
                if (!isValidNumber(value) || !isValidNumber(triggerValue)) {
                    log.warn("数值比较操作符 '>' 要求value和triggerValue都为有效数字，value: {}, triggerValue: {}，忽略此次比较", value, triggerValue);
                    result = false;
                } else {
                    result = Double.parseDouble(value) > Double.parseDouble(triggerValue);
                }
                break;
            case "<":
                if (!isValidNumber(value) || !isValidNumber(triggerValue)) {
                    log.warn("数值比较操作符 '<' 要求value和triggerValue都为有效数字，value: {}, triggerValue: {}，忽略此次比较", value, triggerValue);
                    result = false;
                } else {
                    result = Double.parseDouble(value) < Double.parseDouble(triggerValue);
                }
                break;
            case ">=":
                if (!isValidNumber(value) || !isValidNumber(triggerValue)) {
                    log.warn("数值比较操作符 '>=' 要求value和triggerValue都为有效数字，value: {}, triggerValue: {}，忽略此次比较", value, triggerValue);
                    result = false;
                } else {
                    result = Double.parseDouble(value) >= Double.parseDouble(triggerValue);
                }
                break;
            case "<=":
                if (!isValidNumber(value) || !isValidNumber(triggerValue)) {
                    log.warn("数值比较操作符 '<=' 要求value和triggerValue都为有效数字，value: {}, triggerValue: {}，忽略此次比较", value, triggerValue);
                    result = false;
                } else {
                    result = Double.parseDouble(value) <= Double.parseDouble(triggerValue);
                }
                break;
            case "between":
                // value是规则设置的范围值（格式：最小值-最大值），triggerValue是设备上报的值
                String[] valueRange = value.split("-");
                if (valueRange.length != 2) {
                    log.warn("between操作符要求value格式为'最小值-最大值'，当前值: {}，忽略此次比较", value);
                    result = false;
                } else if (!isValidNumber(triggerValue) || !isValidNumber(valueRange[0]) || !isValidNumber(valueRange[1])) {
                    log.warn("between操作符要求triggerValue和value都为有效数字，triggerValue: {}, value: {}，忽略此次比较", triggerValue, value);
                    result = false;
                } else {
                    double min = Double.parseDouble(valueRange[0]);
                    double max = Double.parseDouble(valueRange[1]);
                    if (min > max) {
                        log.warn("between操作符要求最小值小于等于最大值，当前值: {}，忽略此次比较", value);
                        result = false;
                    } else {
                        result = Double.parseDouble(triggerValue) >= min && Double.parseDouble(triggerValue) <= max;
                    }
                }
                break;
            case "notBetween":
                // value是规则设置的范围值（格式：最小值-最大值），triggerValue是设备上报的值
                String[] valueRangeNot = value.split("-");
                if (valueRangeNot.length != 2) {
                    log.warn("notBetween操作符要求value格式为'最小值-最大值'，当前值: {}，忽略此次比较", value);
                    result = false;
                } else if (!isValidNumber(triggerValue) || !isValidNumber(valueRangeNot[0]) || !isValidNumber(valueRangeNot[1])) {
                    log.warn("notBetween操作符要求triggerValue和value都为有效数字，triggerValue: {}, value: {}，忽略此次比较", triggerValue, value);
                    result = false;
                } else {
                    double notMin = Double.parseDouble(valueRangeNot[0]);
                    double notMax = Double.parseDouble(valueRangeNot[1]);
                    if (notMin > notMax) {
                        log.warn("notBetween操作符要求最小值小于等于最大值，当前值: {}，忽略此次比较", value);
                        result = false;
                    } else {
                        result = Double.parseDouble(triggerValue) < notMin || Double.parseDouble(triggerValue) > notMax;
                    }
                }
                break;
            case "contain":
                result = value.contains(triggerValue);
                break;
            case "notContain":
                result = !value.contains(triggerValue);
                break;
            default:
                break;
        }
        return result;
    }

}