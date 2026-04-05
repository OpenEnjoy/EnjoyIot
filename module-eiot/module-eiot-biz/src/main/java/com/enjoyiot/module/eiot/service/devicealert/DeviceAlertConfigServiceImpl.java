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
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.List;

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
                pageReqVO.getProductId(),
                pageReqVO.getDeviceId(),
                pageReqVO.getName(),
                pageReqVO.getStatus()
        );
        return BeanUtils.toPage(pageResult, DeviceAlertConfig.class);
    }

    @Override
    public List<DeviceAlertConfig> getDeviceAlertConfigListByDeviceId(Long deviceId) {
        return BeanUtils.toBean(deviceAlertConfigMapper.selectByDeviceId(deviceId), DeviceAlertConfig.class);
    }

    @Override
    public List<DeviceAlertConfig> getDeviceAlertConfigListByProductId(Long productId) {
        return BeanUtils.toBean(deviceAlertConfigMapper.selectByProductId(productId), DeviceAlertConfig.class);
    }

    @Override
    public PageResult<DeviceAlertRecord> getDeviceAlertRecordPage(DeviceAlertRecordPageReqVO pageReqVO) {
        PageResult<DeviceAlertRecordDO> pageResult = deviceAlertRecordMapper.selectPage(
                pageReqVO.getDeviceId(),
                pageReqVO.getProductId(),
                pageReqVO.getAlertState(),
                pageReqVO.getStartTime(),
                pageReqVO.getEndTime()
        );
        return BeanUtils.toPage(pageResult, DeviceAlertRecord.class);
    }

    @Override
    public void addDeviceAlertRecord(DeviceAlertRecord record) {
        DeviceAlertRecordDO recordDO = BeanUtils.toBean(record, DeviceAlertRecordDO.class);
        if (record.getId() == null) {
            deviceAlertRecordMapper.insert(recordDO);
        } else {
            deviceAlertRecordMapper.updateById(recordDO);
        }
    }

    @Override
    public DeviceInfo getDeviceInfoFromCache(Long deviceId) {
        return deviceManagerService.getDeviceInfo(deviceId);
    }

    private void validateDeviceAlertConfigExists(Long id) {
        if (deviceAlertConfigMapper.selectById(id) == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.NOT_EXISTS);
        }
    }
}
