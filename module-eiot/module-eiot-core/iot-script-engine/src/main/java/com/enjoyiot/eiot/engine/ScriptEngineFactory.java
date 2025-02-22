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

package com.enjoyiot.eiot.engine;


import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @author sjg
 */
public class ScriptEngineFactory {

    public static IScriptEngine getScriptEngine(String type) {
        if (type == null) {
            type = "js";
        }
        switch (type) {
            case "python":
            case "lua":
                return new IScriptEngine() {

                    @Override
                    public void setScript(String key) {

                    }

                    @Override
                    public void putScriptEnv(String key, Object val) {

                    }

                    @Override
                    public void invokeMethod(String methodName, Object... args) {

                    }

                    @Override
                    public <T> T invokeMethod(TypeReference<T> type, String methodName, Object... args) {
                        return null;
                    }

                    @Override
                    public String invokeMethod(String methodName, String args) {
                        return null;
                    }
                };
            case "js":
            default:
                return new JavaScriptEngine();
        }
    }

    public static IScriptEngine getJsEngine(String script) {
        JavaScriptEngine scriptEngine = new JavaScriptEngine();
        scriptEngine.setScript(script);
        return scriptEngine;
    }

}
