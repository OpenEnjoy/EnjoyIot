package com.enjoyiot.module.eiot.service.devicealert;

import com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.util.json.JsonUtils;
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
import com.fasterxml.jackson.core.type.TypeReference;
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
        if (createReqVO.getConditions() != null) {
            config.setConditions(JsonUtils.toJsonString(createReqVO.getConditions()));
        }
        if (createReqVO.getTriggerOptions() != null) {
            config.setTriggerOptions(JsonUtils.toJsonString(createReqVO.getTriggerOptions()));
        }
        deviceAlertConfigMapper.insert(config);
        return config.getId();
    }

    @Override
    public void updateDeviceAlertConfig(DeviceAlertConfigSaveReqVO updateReqVO) {
        validateDeviceAlertConfigExists(updateReqVO.getId());
        DeviceAlertConfigDO updateObj = BeanUtils.toBean(updateReqVO, DeviceAlertConfigDO.class);
        if (updateReqVO.getConditions() != null) {
            updateObj.setConditions(JsonUtils.toJsonString(updateReqVO.getConditions()));
        }
        if (updateReqVO.getTriggerOptions() != null) {
            updateObj.setTriggerOptions(JsonUtils.toJsonString(updateReqVO.getTriggerOptions()));
        }
        deviceAlertConfigMapper.updateById(updateObj);
    }

    @Override
    public void deleteDeviceAlertConfig(Long id) {
        validateDeviceAlertConfigExists(id);
        deviceAlertConfigMapper.deleteById(id);
    }

    @Override
    public DeviceAlertConfig getDeviceAlertConfig(Long id) {
        return convertToDeviceAlertConfig(deviceAlertConfigMapper.selectById(id));
    }

    @Override
    public PageResult<DeviceAlertConfig> getDeviceAlertConfigPage(DeviceAlertConfigPageReqVO pageReqVO) {
        PageResult<DeviceAlertConfigDO> pageResult = deviceAlertConfigMapper.selectPage(
                pageReqVO
        );
        return new PageResult<>(
                pageResult.getList().stream()
                        .map(this::convertToDeviceAlertConfig)
                        .toList(),
                pageResult.getTotal()
        );
    }

    @Override
    public List<DeviceAlertConfig> getDeviceAlertConfigListByDeviceId(Long deviceId) {
        return deviceAlertConfigMapper.selectByDeviceId(deviceId).stream()
                .map(this::convertToDeviceAlertConfig)
                .toList();
    }

    @Override
    public List<DeviceAlertConfig> getDeviceAlertConfigListByProductKey(String productKey) {
        return deviceAlertConfigMapper.selectByProductKey(productKey).stream()
                .map(this::convertToDeviceAlertConfig)
                .toList();
    }

    @Override
    public PageResult<DeviceAlertRecord> getDeviceAlertRecordPage(DeviceAlertRecordPageReqVO pageReqVO) {
        PageResult<DeviceAlertRecordDO> pageResult = deviceAlertRecordMapper.selectPage(
                pageReqVO
        );
        return BeanUtils.toBean(pageResult, DeviceAlertRecord.class);
    }

    @Override
    public List<DeviceAlertRecord> getDeviceAlertRecordListByDeviceId(Long deviceId) {
        List<DeviceAlertRecordDO> list = deviceAlertRecordMapper.selectListByDeviceId(deviceId);
        return BeanUtils.toBean(list, DeviceAlertRecord.class);
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

    private DeviceAlertConfig convertToDeviceAlertConfig(DeviceAlertConfigDO configDO) {
        if (configDO == null) {
            return null;
        }
        DeviceAlertConfig config = new DeviceAlertConfig();
        config.setId(configDO.getId());
        config.setName(configDO.getName());
        config.setProductKey(configDO.getProductKey());
        config.setDeviceId(configDO.getDeviceId());
        config.setLevel(configDO.getLevel());
        config.setStatus(configDO.getStatus());
        config.setRemark(configDO.getRemark());

        if (configDO.getConditions() != null && !configDO.getConditions().isEmpty()) {
            try {
                List<DeviceAlertConfig.AlertCondition> conditions = JsonUtils.parseObject(
                        configDO.getConditions(),
                        new TypeReference<List<DeviceAlertConfig.AlertCondition>>() {}
                );
                config.setConditions(conditions);
            } catch (Exception e) {
                log.error("Failed to parse conditions JSON: {}", configDO.getConditions(), e);
            }
        }

        if (configDO.getTriggerOptions() != null && !configDO.getTriggerOptions().isEmpty()) {
            try {
                DeviceAlertConfig.TriggerOptions triggerOptions = JsonUtils.parseObject(
                        configDO.getTriggerOptions(),
                        DeviceAlertConfig.TriggerOptions.class
                );
                config.setTriggerOptions(triggerOptions);
            } catch (Exception e) {
                log.error("Failed to parse triggerOptions JSON: {}", configDO.getTriggerOptions(), e);
            }
        }

        return config;
    }

    private void validateDeviceAlertConfigExists(Long id) {
        if (deviceAlertConfigMapper.selectById(id) == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.NOT_EXISTS);
        }
    }
}
