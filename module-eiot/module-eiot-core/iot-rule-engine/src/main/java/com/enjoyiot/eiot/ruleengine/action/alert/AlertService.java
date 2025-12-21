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
package com.enjoyiot.eiot.ruleengine.action.alert;

import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.ruleengine.action.ScriptService;
import com.enjoyiot.eiot.message.service.MessageService;
import com.enjoyiot.framework.tenant.core.context.TenantContextHolder;
import com.enjoyiot.module.eiot.api.alert.dto.Message;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author sjg
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class AlertService extends ScriptService {

    private Message message;

    private MessageService messageService;

    /**
     * 告警解除脚本，未配置则复用触发脚本
     */
    private String recoverScript;

    private final ScriptService recoverExecutor = new ScriptService();

    public void initDeviceApi(DeviceApi deviceApi) {
        super.setDeviceApi(deviceApi);
        recoverExecutor.setDeviceApi(deviceApi);
    }

    public void setRecoverScript(String recoverScript) {
        this.recoverScript = recoverScript;
        if (StringUtils.isNotBlank(recoverScript)) {
            recoverExecutor.setScript(recoverScript);
        }
    }

    @SneakyThrows
    public String execute(ThingModelMessage msg) {
        return executeInternal(msg, false);
    }

    public String executeRecover(ThingModelMessage msg) {
        return executeInternal(msg, true);
    }

    @SneakyThrows
    private String executeInternal(ThingModelMessage msg, boolean recover) {
        // 执行转换脚本
        Map<String, Object> result = executeScript(msg, recover);
        
        // 验证脚本执行结果
        if (!validateScriptResult(result, recover)) {
            return "execScript result is null";
        }
        
        // 为消息增加状态标记，便于模板取值
        enrichMessageResult(result, recover);
        
        // 发送告警消息
        sendAlertMessage(result);
        
        return getResultMessage(recover);
    }

    /**
     * 执行脚本：根据是否为恢复操作选择对应的脚本执行器
     * 
     * @param msg 物模型消息
     * @param recover 是否为恢复操作
     * @return 脚本执行结果
     */
    private Map<String, Object> executeScript(ThingModelMessage msg, boolean recover) {
        if (recover && StringUtils.isNotBlank(recoverScript)) {
            // 使用恢复脚本执行器
            return recoverExecutor.execScript(new TypeReference<Map<String, Object>>() {}, msg);
        } else {
            // 使用触发脚本执行器
            return execScript(new TypeReference<Map<String, Object>>() {}, msg);
        }
    }

    /**
     * 丰富消息结果：添加状态标记等信息
     * 
     * @param result 脚本执行结果
     * @param recover 是否为恢复操作
     */
    private void enrichMessageResult(Map<String, Object> result, boolean recover) {
        result.putIfAbsent("alertState", recover ? "recover" : "alert");
    }

    /**
     * 发送告警消息
     * 
     * @param result 消息参数
     */
    private void sendAlertMessage(Map<String, Object> result) {
        message.setParam(result);
        message.setTenantId(TenantContextHolder.getTenantId());
        messageService.sendMessage(message);
    }

    /**
     * 验证脚本执行结果
     * 
     * @param result 脚本执行结果
     * @param recover 是否为恢复操作
     * @return 验证是否通过
     */
    private boolean validateScriptResult(Map<String, Object> result, boolean recover) {
        if (result == null) {
            log.warn("execScript result is null, recover={}", recover);
            return false;
        }
        return true;
    }

    /**
     * 获取执行结果消息
     * 
     * @param recover 是否为恢复操作
     * @return 结果消息
     */
    private String getResultMessage(boolean recover) {
        return recover ? "recover_ok" : "ok";
    }
}
