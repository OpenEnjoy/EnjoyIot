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

    private static final Map<String, String> COMPARATOR_MAP = new HashMap<>();

    private static IScriptEngine engine = ScriptEngineFactory.getScriptEngine("js");

    static {
        COMPARATOR_MAP.put(">", "gt");
        COMPARATOR_MAP.put(">=", "ge");
        COMPARATOR_MAP.put("==", "eq");
        COMPARATOR_MAP.put("<", "lt");
        COMPARATOR_MAP.put("<=", "le");
        COMPARATOR_MAP.put("!=", "neq");
        try {
            String script = IOUtils.resourceToString("script.js", Charset.defaultCharset(), Expression.class.getClassLoader());
            engine.setScript(script);
        } catch (IOException e) {
            log.error("read script.js error", e);
        }
    }

    public static boolean eval(String comparator, Object... args) {
        String name = COMPARATOR_MAP.get(comparator);
        if (name == null) {
            throw exception(DATA_BLANK);
        }

        return engine.invokeMethod(new TypeReference<Boolean>() {
        }, name, args);
    }

}
