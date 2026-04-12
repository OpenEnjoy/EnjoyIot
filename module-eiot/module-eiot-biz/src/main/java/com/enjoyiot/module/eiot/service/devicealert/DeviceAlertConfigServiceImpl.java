package com.enjoyiot.module.eiot.service.devicealert;

import com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.util.object.BeanUtils;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.devicealert.dto.DeviceAlertConfig;
import com.enjoyiot.module.eiot.api.devicealert.dto.DeviceAlertRecord;
import com.enjoyiot.module.eiot.api.enums.ErrorCodeConstants;
import com.enjoyiot.module.eiot.controller.admin.devicealert.vo.*;
import com.enjoyiot.module.eiot.dal.dataobject.devicealert.DeviceAlertConfigDO;
import com.enjoyiot.module.eiot.dal.dataobject.devicealert.DeviceAlertRecordDO;
import com.enjoyiot.module.eiot.dal.mysql.devicealert.DeviceAlertConfigMapper;
import com.enjoyiot.module.eiot.dal.mysql.devicealert.DeviceAlertRecordMapper;
import com.enjoyiot.module.eiot.service.device.DeviceManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.List;

@Slf4j
@Service
@Validated
public class DeviceAlertConfigServiceImpl implements DeviceAlertConfigService {

    @Resource
    private DeviceAlertConfigMapper deviceAlertConfigMapper;

    @Resource
    private DeviceAlertRecordMapper deviceAlertRecordMapper;

    @Resource
    private DeviceManagerService deviceManagerService;

    @Override
    public Long createDeviceAlertConfig(DeviceAlertConfigSaveReqVO createReqVO) {
        DeviceAlertConfigDO config = BeanUtils.toBean(createReqVO, DeviceAlertConfigDO.class);
        deviceAlertConfigMapper.insert(config);
        return config.getId();
    }

    @Override
    public void updateDeviceAlertConfig(DeviceAlertConfigSaveReqVO updateReqVO) {
        validateDeviceAlertConfigExists(updateReqVO.getId());
        DeviceAlertConfigDO updateObj = BeanUtils.toBean(updateReqVO, DeviceAlertConfigDO.class);
        deviceAlertConfigMapper.updateById(updateObj);
    }

    @Override
    public void deleteDeviceAlertConfig(Long id) {
        validateDeviceAlertConfigExists(id);
        deviceAlertConfigMapper.deleteById(id);
    }

    @Override
    public DeviceAlertConfig getDeviceAlertConfig(Long id) {
        return BeanUtils.toBean(deviceAlertConfigMapper.selectById(id), DeviceAlertConfig.class);
    }

    @Override
    public PageResult<DeviceAlertConfig> getDeviceAlertConfigPage(DeviceAlertConfigPageReqVO pageReqVO) {
        PageResult<DeviceAlertConfigDO> pageResult = deviceAlertConfigMapper.selectPage(
                pageReqVO
        );
        return BeanUtils.toBean(pageResult, DeviceAlertConfig.class);
    }

    @Override
    public List<DeviceAlertConfig> getDeviceAlertConfigListByDeviceId(Long deviceId) {
        return BeanUtils.toBean(deviceAlertConfigMapper.selectByDeviceId(deviceId), DeviceAlertConfig.class);
    }

    @Override
    public List<DeviceAlertConfig> getDeviceAlertConfigListByProductKey(String productKey) {
        return BeanUtils.toBean(deviceAlertConfigMapper.selectByProductKey(productKey), DeviceAlertConfig.class);
    }

    @Override
    public PageResult<DeviceAlertRecord> getDeviceAlertRecordPage(DeviceAlertRecordPageReqVO pageReqVO) {
        PageResult<DeviceAlertRecordDO> pageResult = deviceAlertRecordMapper.selectPage(
                pageReqVO
        );
        return BeanUtils.toBean(pageResult, DeviceAlertRecord.class);
    }

    @Override
    public void addDeviceAlertRecord(DeviceAlertRecord record) {
        DeviceAlertRecordDO existActiveAlert = deviceAlertRecordMapper.selectActiveAlert(record.getDeviceId(), record.getName());
        if (existActiveAlert != null) {
            log.info("active alert already exists, deviceId: {}, alertName: {}", record.getDeviceId(), record.getName());
            return;
        }
        DeviceAlertRecordDO recordDO = BeanUtils.toBean(record, DeviceAlertRecordDO.class);
        deviceAlertRecordMapper.insert(recordDO);
    }

    @Override
    public void recoverDeviceAlertRecord(Long deviceId, String alertName) {
        DeviceAlertRecordDO activeAlert = deviceAlertRecordMapper.selectActiveAlert(deviceId, alertName);
        if (activeAlert != null) {
            activeAlert.setAlertState("recover");
            activeAlert.setRecoverTime(System.currentTimeMillis());
            deviceAlertRecordMapper.updateById(activeAlert);
        }
    }

    private void validateDeviceAlertConfigExists(Long id) {
        if (deviceAlertConfigMapper.selectById(id) == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.NOT_EXISTS);
        }
    }
}
