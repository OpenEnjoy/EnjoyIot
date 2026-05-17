package com.enjoyiot.eiot.ruleengine.devicealert;

import cn.hutool.core.collection.CollectionUtil;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.ruleengine.handler.DeviceMessageHandler;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.device.DeviceInfo;
import com.enjoyiot.module.eiot.api.device.dto.DevicePropertyCache;
import com.enjoyiot.module.eiot.api.devicealert.dto.DeviceAlertConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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

        // 获取设备信息（包含在线状态）
        DeviceInfo deviceInfo = deviceApi.getDeviceInfoFromCache(deviceId);
        // 获取设备缓存属性
        Map<String, DevicePropertyCache> cachedProps = deviceApi.getPropertiesFromCache(deviceId);

        List<DeviceAlertConfig> configs = deviceApi.getDeviceAlertConfigListByDeviceId(deviceId);
        List<DeviceAlertConfig> productConfigs = deviceApi.getDeviceAlertConfigListByProductKey(productKey);
        if(CollectionUtil.isEmpty(configs) && CollectionUtil.isEmpty(productConfigs)){
            return;
        }

        for (DeviceAlertConfig config : configs) {
            if (!config.isEnable()) {
                continue;
            }
            processAlertConfig(config, message, deviceInfo, cachedProps);
        }

        for (DeviceAlertConfig config : productConfigs) {
            if (!config.isEnable()) {
                continue;
            }
            processAlertConfig(config, message, deviceInfo, cachedProps);
        }
    }

    private void processAlertConfig(DeviceAlertConfig config,
                                    ThingModelMessage message,
                                    DeviceInfo deviceInfo,
                                    Map<String, DevicePropertyCache> cachedProps) {
        if (config.getConditions() == null || config.getConditions().isEmpty()) {
            return;
        }

        // 获取设备在线状态
        Boolean isOnline = deviceInfo != null ? deviceInfo.isOnline() : null;

        // 评估条件：合并缓存属性 + 当前消息 + 设备状态
        boolean conditionMet = conditionEvaluator.evaluate(
                config.getConditions(),
                message,
                cachedProps,
                isOnline,
                config.getLogic()
        );

        if (!conditionMet) {
            // 条件不满足时，检查是否需要恢复
            DeviceAlertConfig.TriggerOptions triggerOptions = config.getTriggerOptions();
            if (triggerOptions != null && Boolean.TRUE.equals(triggerOptions.getEnableRecover())) {
                deviceAlertAction.doRecover(config, message);
            }
            return;
        }

        deviceAlertAction.doAlert(config, message);
    }
}