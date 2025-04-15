package com.enjoyiot.eiot.engine;/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com | Tel: 19918996474
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot] | Tel: 19918996474
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


import com.enjoyiot.eiot.engine.IScriptEngine;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

/**
 * @author sjg
 */
@Slf4j
public class JavaScriptEngine implements IScriptEngine {

    private final ThreadLocal<Context> contextThreadLocal = new ThreadLocal<>();

    /**
     * 原始脚本内容，仅记录。当前无特殊用途
     */
    private String originScript;

    /**
     * 编译后的源码
     */
    private Source compiledScript;

    @Override
    public void setScript(String script) {
        initContext();
        this.originScript = script;
        this.compiledScript = compileScript(script);
    }

    @Override
    public void putScriptEnv(String key, Object value) {
        Context context = getContext();
        context.getBindings("js").putMember(key, value);
    }

    @Override
    public void invokeMethod(String methodName, Object... args) {
        invokeMethod(new TypeReference<Void>() {
        }, methodName, args);
    }

    @Override
    public <T> T invokeMethod(TypeReference<T> type, String methodName, Object... args) {
        Value jsScript = getJsScript();
        Value member = jsScript.getMember("invoke");

        StringBuilder sbArgs = formatArgs(args);
        //通过调用invoke方法将目标方法返回结果转成json
        Value rst = member.execute(methodName, args);
        String json = rst.asString();
        log.info("invoke script={}, args={}, result={}", methodName, sbArgs, json);

        //没有返回值
        if (json == null || "null".equals(json)) {
            return null;
        }
        return JsonUtils.parseObject(json, type);
    }

    @Override
    public String invokeMethod(String methodName, String args) {
        Value jsScript = getJsScript();
        Value member = jsScript.getMember("invoke");
        //通过调用invoke方法将目标方法返回结果转成json
        Value rst = member.execute(methodName, JsonUtils.parseArray(args, Object.class));
        String json = rst.asString();
        log.info("invoke script={}, args={}, result={}", methodName, args, json);
        //没有返回值
        if (json == null || "null".equals(json)) {
            return null;
        }
        return json;
    }

    /**
     * 初始化context
     */
    private void initContext() {
        Context context = contextThreadLocal.get();
        if (context != null) {
            // 如果已经存在context,进行关闭。重新创建
            context.close();
            contextThreadLocal.remove();
        }
        // 初始化 threadLocal中的context
        getContext();
    }

    private Context getContext() {
        Context context = contextThreadLocal.get();
        if (context == null) {
            context = Context.newBuilder("js").option("engine.WarnInterpreterOnly", "false").allowHostAccess(HostAccess.ALL).build();
            contextThreadLocal.set(context);
        }
        return context;
    }

    private Value getJsScript() {
        return getContext().eval(compiledScript);
    }

    /**
     * 编译脚本，避免每次context.eval时，都进行编译source
     * 在每次 {@link JavaScriptEngine#setScript(String)}时，进行编译
     * todo 脚本编写错误抛出异常
     *
     * @param script 脚本内容
     * @return 编译后的source
     */
    private static Source compileScript(String script) {
        String wrapperJsScript = String.format(
                "new (function () {\n%s; " +
                        "   this.invoke=function(f,args){" +
                        "       for(i in args){" +
                        "           args[i]=JSON.parse(args[i]);" +
                        "       }" +
                        "       return JSON.stringify(this[f].apply(this,args));" +
                        "   }; " +
                        "})()", script);
        return Source.create("js", wrapperJsScript);
    }

    private static StringBuilder formatArgs(Object[] args) {
        StringBuilder sbArgs = new StringBuilder("[");
        //将入参转成json
        for (int i = 0; i < args.length; i++) {
            args[i] = JsonUtils.toJsonString(args[i]);
            sbArgs.append(args[i]).append(i != args.length - 1 ? "," : "");
        }
        sbArgs.append("]");
        return sbArgs;
    }

}
