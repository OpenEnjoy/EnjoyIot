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
package com.enjoyiot.eiot.ruleengine.listener;

import com.enjoyiot.eiot.ruleengine.expression.Expression;
import com.enjoyiot.eiot.ruleengine.util.PathValueResolver;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
public class DeviceCondition {

    private String type;

    private String device;

    private String identifier;

    private List<Parameter> parameters;

    public boolean matches(String type, String identifier, Map<?, ?> parameter) {
        if (!this.type.equals(type)) {
            return false;
        }
        //通配规则，不需要判断其它条件
        if (this.identifier.endsWith(":*")) {
            return true;
        }
        if (!this.identifier.equals(identifier)) {
            return false;
        }
        for (Parameter p : this.parameters) {
            if (!p.matches(parameter)) {
                return false;
            }
        }
        return true;
    }

    @Data
    public static class Parameter {
        private String identifier;
        private String value;
        private String comparator;

        public boolean matches(Map<?, ?> parameter) {
            //任意匹配
            if ("*".equals(identifier)) {
                return true;
            }

            //存在参数或无参数条件，值任意匹配
            if ((StringUtils.isBlank(identifier) || parameter.containsKey(identifier))
                    && "*".equals(comparator)) {
                return true;
            }

            List<Object> values = StringUtils.isBlank(identifier)
                    ? Collections.emptyList()
                    : PathValueResolver.resolveValues(parameter, identifier);
            if (values.isEmpty()) {
                return false;
            }
            for (Object left : values) {
                if (left == null) {
                    continue;
                }
                if (left instanceof Map || left instanceof List || left.getClass().isArray()) {
                    if (Expression.eval(comparator, JsonUtils.toJsonString(left), value)) {
                        return true;
                    }
                    continue;
                }
                if (Expression.eval(comparator, String.valueOf(left), value)) {
                    return true;
                }
            }
            return false;
        }
    }
}
