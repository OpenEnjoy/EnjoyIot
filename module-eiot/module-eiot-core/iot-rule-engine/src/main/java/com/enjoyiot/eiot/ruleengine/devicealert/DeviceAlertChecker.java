package com.enjoyiot.eiot.ruleengine.devicealert;

import cn.hutool.core.collection.CollectionUtil;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.ruleengine.handler.DeviceMessageHandler;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.devicealert.dto.DeviceAlertConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceAlertChecker implements DeviceMessageHandler {

    private final DeviceApi deviceApi;
    private final DeviceAlertAction deviceAlertAction;
    private final ConditionEvaluator conditionEvaluator;

    @Override
    public void handle(ThingModelMessage message) {
        Long deviceId = message.getDeviceId();
        if (deviceId == null) {
            return;
        }

        String productKey = message.getProductKey();
        if (productKey == null) {
            return;
        }

        List<DeviceAlertConfig> configs = deviceApi.getDeviceAlertConfigListByDeviceId(deviceId);
        List<DeviceAlertConfig> productConfigs = deviceApi.getDeviceAlertConfigListByProductKey(productKey);
        if(CollectionUtil.isEmpty(configs) && CollectionUtil.isEmpty(productConfigs)){
            return;
        }

        for (DeviceAlertConfig config : configs) {
            if (!config.isEnable()) {
                continue;
            }
            processAlertConfig(config, message);
        }

        for (DeviceAlertConfig config : productConfigs) {
            if (!config.isEnable()) {
                continue;
            }
            processAlertConfig(config, message);
        }
    }

    private void processAlertConfig(DeviceAlertConfig config, ThingModelMessage message) {
        if (config.getConditions() == null || config.getConditions().isEmpty()) {
            return;
        }

        if (!conditionEvaluator.evaluate(config.getConditions(), message)) {
            if (Boolean.TRUE.equals(config.getTriggerOptions().getEnableRecover())) {
                deviceAlertAction.doRecover(config, message);
            }
            return;
        }

        deviceAlertAction.doAlert(config, message);
    }
}
