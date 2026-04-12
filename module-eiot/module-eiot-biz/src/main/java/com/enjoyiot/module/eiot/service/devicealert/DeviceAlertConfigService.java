package com.enjoyiot.module.eiot.service.devicealert;

import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.devicealert.dto.DeviceAlertConfig;
import com.enjoyiot.module.eiot.api.devicealert.dto.DeviceAlertRecord;
import com.enjoyiot.module.eiot.controller.admin.devicealert.vo.*;

import jakarta.validation.Valid;
import java.util.List;

public interface DeviceAlertConfigService {

    Long createDeviceAlertConfig(@Valid DeviceAlertConfigSaveReqVO createReqVO);

    void updateDeviceAlertConfig(@Valid DeviceAlertConfigSaveReqVO updateReqVO);

    void deleteDeviceAlertConfig(Long id);

    DeviceAlertConfig getDeviceAlertConfig(Long id);

    PageResult<DeviceAlertConfig> getDeviceAlertConfigPage(DeviceAlertConfigPageReqVO pageReqVO);

    List<DeviceAlertConfig> getDeviceAlertConfigListByDeviceId(Long deviceId);

    List<DeviceAlertConfig> getDeviceAlertConfigListByProductKey(String productKey);

    PageResult<DeviceAlertRecord> getDeviceAlertRecordPage(DeviceAlertRecordPageReqVO pageReqVO);

    List<DeviceAlertRecord> getDeviceAlertRecordListByDeviceId(Long deviceId);

    void addDeviceAlertRecord(DeviceAlertRecord record);

    void recoverDeviceAlertRecord(Long deviceId, String alertName);

}
