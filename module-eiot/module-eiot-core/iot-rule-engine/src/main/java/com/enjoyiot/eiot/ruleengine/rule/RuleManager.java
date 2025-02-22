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


import com.enjoyiot.eiot.ruleengine.action.Action;
import com.enjoyiot.eiot.ruleengine.action.alert.AlertAction;
import com.enjoyiot.eiot.ruleengine.action.alert.AlertService;
import com.enjoyiot.eiot.ruleengine.action.device.DeviceAction;
import com.enjoyiot.eiot.ruleengine.action.device.DeviceActionService;
import com.enjoyiot.eiot.ruleengine.action.http.HttpAction;
import com.enjoyiot.eiot.ruleengine.action.http.HttpService;
import com.enjoyiot.eiot.ruleengine.action.kafka.KafkaAction;
import com.enjoyiot.eiot.ruleengine.action.kafka.KafkaService;
import com.enjoyiot.eiot.ruleengine.action.mqtt.MqttAction;
import com.enjoyiot.eiot.ruleengine.action.mqtt.MqttService;
import com.enjoyiot.eiot.ruleengine.action.tcp.TcpAction;
import com.enjoyiot.eiot.ruleengine.action.tcp.TcpService;
import com.enjoyiot.eiot.ruleengine.filter.DeviceFilter;
import com.enjoyiot.eiot.ruleengine.filter.Filter;
import com.enjoyiot.eiot.ruleengine.link.LinkFactory;
import com.enjoyiot.eiot.ruleengine.listener.DeviceCondition;
import com.enjoyiot.eiot.ruleengine.listener.DeviceListener;
import com.enjoyiot.eiot.ruleengine.listener.Listener;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.eiot.message.service.MessageService;
import com.enjoyiot.module.eiot.api.alert.AlertApi;
import com.enjoyiot.module.eiot.api.alert.dto.AlertConfig;
import com.enjoyiot.module.eiot.api.alert.dto.AlertConfigPageReqVO;
import com.enjoyiot.module.eiot.api.alert.dto.Message;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.rule.dto.FilterConfig;
import com.enjoyiot.module.eiot.api.rule.dto.RuleInfo;
import com.enjoyiot.module.eiot.api.task.dto.RuleAction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class RuleManager {

    @Resource
    private RuleMessageHandler ruleMessageHandler;

    @Resource
    private DeviceActionService deviceActionService;

    @Resource
    private AlertApi alertApi;

    @Resource
    private DeviceApi deviceApi;

    @Autowired
    private MessageService messageService;

    public void add(RuleInfo ruleInfo) {
        if (RuleInfo.STATE_STOPPED.equals(ruleInfo.getState())) {
            return;
        }
        Rule rule = parseRule(ruleInfo);
        ruleMessageHandler.putRule(rule);
    }

    public void remove(Long ruleId) {
        ruleMessageHandler.removeRule(ruleId);
        // 移出link连接
        LinkFactory.ruleClose(ruleId);
    }

    public void pause(Long ruleId) {
        remove(ruleId);
    }

    public void resume(RuleInfo ruleInfo) {
        add(ruleInfo);
    }

    private Rule parseRule(RuleInfo ruleInfo) {
        List<Listener<?>> listeners = new ArrayList<>();
        for (FilterConfig listener : ruleInfo.getListeners()) {
            if (StringUtils.isBlank(listener.getConfig())) {
                continue;
            }
            listeners.add(parseListener(listener.getType(), listener.getConfig()));
        }

        List<Filter<?>> filters = new ArrayList<>();
        for (FilterConfig filter : ruleInfo.getFilters()) {
            if (StringUtils.isBlank(filter.getConfig())) {
                continue;
            }
            filters.add(parseFilter(filter.getType(), filter.getConfig()));
        }

        List<Action<?>> actions = new ArrayList<>();
        for (RuleAction action : ruleInfo.getActions()) {
            if (StringUtils.isBlank(action.getConfig())) {
                continue;
            }
            actions.add(parseAction(ruleInfo.getId(), action.getType(), action.getConfig()));
        }

        return new Rule(ruleInfo.getId(), ruleInfo.getName(), listeners, filters, actions, ruleInfo.getTenantId());
    }

    private Listener<?> parseListener(String type, String config) {
        if (DeviceListener.TYPE.equals(type)) {
            DeviceListener listener = parse(config, DeviceListener.class);
            for (DeviceCondition condition : listener.getConditions()) {
                String dn = "#";
                if (StringUtils.isNotBlank(listener.getDn())) {
                    dn = listener.getDn();
                }
                condition.setDevice(listener.getPk() + "/" + dn);
            }
            return listener;
        }
        return null;
    }

    private Filter<?> parseFilter(String type, String config) {
        if (DeviceFilter.TYPE.equals(type)) {
            DeviceFilter filter = parse(config, DeviceFilter.class);
            for (com.enjoyiot.eiot.ruleengine.filter.DeviceCondition condition : filter.getConditions()) {
                String dn = "#";
                if (StringUtils.isNotBlank(filter.getDn())) {
                    dn = filter.getDn();
                }
                condition.setDevice(filter.getPk() + "/" + dn);
            }
            filter.setDeviceApi(deviceApi);
            return filter;
        }
        return null;
    }

    private Action<?> parseAction(Long ruleId, String type, String config) {
        if (DeviceAction.TYPE.equals(type)) {
            DeviceAction action = parse(config, DeviceAction.class);
            action.setDeviceActionService(deviceActionService);
            return action;
        } else if (HttpAction.TYPE.equals(type)) {
            HttpAction httpAction = parse(config, HttpAction.class);
            for (HttpService service : httpAction.getServices()) {
                service.setDeviceApi(deviceApi);
            }
            return httpAction;
        } else if (MqttAction.TYPE.equals(type)) {
            MqttAction mqttAction = parse(config, MqttAction.class);
            for (MqttService service : mqttAction.getServices()) {
                service.setDeviceApi(deviceApi);
                service.initLink(ruleId);
            }
            return mqttAction;
        } else if (KafkaAction.TYPE.equals(type)) {
            KafkaAction kafkaAction = parse(config, KafkaAction.class);
            for (KafkaService service : kafkaAction.getServices()) {
                service.setDeviceApi(deviceApi);
                service.initLink(ruleId);
            }
            return kafkaAction;
        } else if (TcpAction.TYPE.equals(type)) {
            TcpAction tcpAction = parse(config, TcpAction.class);
            for (TcpService service : tcpAction.getServices()) {
                service.setDeviceApi(deviceApi);
                service.initLink(ruleId);
            }
            return tcpAction;
        } else if (AlertAction.TYPE.equals(type)) {


            AlertAction alertAction = parse(config, AlertAction.class);
            String script = alertAction.getServices().get(0).getScript();

            List<AlertService> alertServices = new ArrayList<>();

            Integer pageSize = 1000;
            for (int idx = 1; idx < 100; idx++) {
                AlertConfigPageReqVO pageReqVO = new AlertConfigPageReqVO();
                pageReqVO.setRuleInfoId(ruleId);
                pageReqVO.setPageNo(idx);
                pageReqVO.setPageSize(pageSize);

                PageResult<AlertConfig> alertConfigPage = alertApi.getAlertConfigPage(pageReqVO);

                for (AlertConfig alertConfig : alertConfigPage.getList()) {
                    if (!alertConfig.isEnable()) {
                        continue;
                    }

                    AlertService service = new AlertService();
                    service.setScript(script);
                    service.setDeviceApi(deviceApi);
                    service.setMessageService(messageService);

                    Message message = alertApi.getNotifyMessage(alertConfig);
                    if (message == null) {
                        continue;
                    }

                    service.setMessage(message);
                    alertServices.add(service);
                }
                if (alertConfigPage.getTotal() < pageSize) {
                    break;
                }
            }
            alertAction.setServices(alertServices);
            return alertAction;
        }
        return null;
    }

    private <T> T parse(String config, Class<T> cls) {
        return JsonUtils.parseObject(config, cls);
    }

}
