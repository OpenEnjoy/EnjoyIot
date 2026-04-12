package com.enjoyiot.eiot.ruleengine.devicealert;

import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.devicealert.dto.DeviceAlertConfig;
import com.enjoyiot.module.eiot.api.devicealert.dto.DeviceAlertRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceAlertAction {

    private final DeviceApi deviceApi;

    public void doAlert(DeviceAlertConfig config, ThingModelMessage message) {
        DeviceAlertRecord record = DeviceAlertRecord.builder()
                .deviceId(message.getDeviceId())
                .productKey(message.getProductKey() != null ? message.getProductKey() : getProductKeyByDeviceId(message.getDeviceId()))
                .alertConfigId(config.getId())
                .alertTime(System.currentTimeMillis())
                .alertState("alert")
                .level(config.getLevel())
                .name(config.getName())
                .details(buildDetails(config, message))
                .readFlg(false)
                .build();
        deviceApi.addDeviceAlertRecord(record);
        log.info("device alert triggered, configId: {}, deviceId: {}", config.getId(), message.getDeviceId());
    }

    public void doRecover(DeviceAlertConfig config, ThingModelMessage message) {
        deviceApi.recoverDeviceAlertRecord(message.getDeviceId(), config.getName());
        log.info("device alert recovered, configId: {}, deviceId: {}", config.getId(), message.getDeviceId());
    }

    public String getProductKeyByDeviceId(Long deviceId) {
        try {
            return deviceApi.getDeviceInfoFromCache(deviceId).getProductKey();
        } catch (Exception e) {
            log.warn("get productKey failed, deviceId: {}", deviceId, e);
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
