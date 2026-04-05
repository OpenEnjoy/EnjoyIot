package com.enjoyiot.eiot.ruleengine.devicealert;

import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.ruleengine.handler.DeviceMessageHandler;
import com.enjoyiot.module.eiot.api.devicealert.dto.DeviceAlertConfig;
import com.enjoyiot.module.eiot.service.devicealert.DeviceAlertConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceAlertChecker implements DeviceMessageHandler {

    private final DeviceAlertConfigService deviceAlertConfigService;
    private final DeviceAlertAction deviceAlertAction;
    private final ConditionEvaluator conditionEvaluator;

    @Override
    public void handle(ThingModelMessage message) {
        Long deviceId = message.getDeviceId();
        if (deviceId == null) {
            return;
        }

        Long productId = getProductId(deviceId);
        if (productId == null) {
            return;
        }

        List<DeviceAlertConfig> configs = deviceAlertConfigService.getDeviceAlertConfigListByDeviceId(deviceId);
        List<DeviceAlertConfig> productConfigs = deviceAlertConfigService.getDeviceAlertConfigListByProductId(productId);

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

    private Long getProductId(Long deviceId) {
        try {
            return deviceAlertAction.getProductIdByDeviceId(deviceId);
        } catch (Exception e) {
            log.warn("get productId failed, deviceId: {}", deviceId, e);
            return null;
        }
    }
}
