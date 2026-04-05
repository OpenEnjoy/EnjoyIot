package com.enjoyiot.eiot.ruleengine.devicealert;

import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.module.eiot.api.devicealert.dto.DeviceAlertConfig;
import com.enjoyiot.module.eiot.api.devicealert.dto.DeviceAlertRecord;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.service.devicealert.DeviceAlertConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceAlertAction {

    private final DeviceAlertConfigService deviceAlertConfigService;

    public void doAlert(DeviceAlertConfig config, ThingModelMessage message) {
        DeviceAlertRecord record = DeviceAlertRecord.builder()
                .deviceId(message.getDeviceId())
                .productId(config.getProductId() != null ? config.getProductId() : getProductIdByDeviceId(message.getDeviceId()))
                .alertConfigId(config.getId())
                .alertTime(System.currentTimeMillis())
                .alertState("alert")
                .level(config.getLevel())
                .name(config.getName())
                .details(buildDetails(config, message))
                .readFlg(false)
                .build();
        deviceAlertConfigService.addDeviceAlertRecord(record);
        log.info("device alert triggered, configId: {}, deviceId: {}", config.getId(), message.getDeviceId());
    }

    public void doRecover(DeviceAlertConfig config, ThingModelMessage message) {
        DeviceAlertRecord record = DeviceAlertRecord.builder()
                .deviceId(message.getDeviceId())
                .productId(config.getProductId() != null ? config.getProductId() : getProductIdByDeviceId(message.getDeviceId()))
                .alertConfigId(config.getId())
                .alertTime(System.currentTimeMillis())
                .alertState("recover")
                .level(config.getLevel())
                .name(config.getName())
                .details("Condition not satisfied, alert recovered")
                .readFlg(false)
                .build();
        deviceAlertConfigService.addDeviceAlertRecord(record);
        log.info("device alert recovered, configId: {}, deviceId: {}", config.getId(), message.getDeviceId());
    }

    public Long getProductIdByDeviceId(Long deviceId) {
        try {
            return deviceAlertConfigService.getDeviceInfoFromCache(deviceId).getProductId();
        } catch (Exception e) {
            log.warn("get productId failed, deviceId: {}", deviceId, e);
            return null;
        }
    }

    private String buildDetails(DeviceAlertConfig config, ThingModelMessage message) {
        StringBuilder sb = new StringBuilder();
        sb.append("Alert: ").append(config.getName());
        if (config.getConditions() != null) {
            sb.append(", Conditions: ").append(config.getConditions());
        }
        return sb.toString();
    }
}
