package com.enjoyiot.module.eiot.controller.admin.devicealert;

import com.enjoyiot.framework.common.pojo.CommonResult;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.devicealert.dto.DeviceAlertConfig;
import com.enjoyiot.module.eiot.api.devicealert.dto.DeviceAlertRecord;
import com.enjoyiot.module.eiot.controller.admin.devicealert.vo.*;
import com.enjoyiot.module.eiot.service.devicealert.DeviceAlertConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "设备告警配置")
@RestController
@RequestMapping("/eiot/device-alert")
@Validated
public class DeviceAlertConfigController {

    @Resource
    private DeviceAlertConfigService deviceAlertConfigService;

    @Operation(summary = "创建设备告警配置")
    @PostMapping("/config/create")
    public CommonResult<Long> createDeviceAlertConfig(@Valid @RequestBody DeviceAlertConfigSaveReqVO createReqVO) {
        return CommonResult.success(deviceAlertConfigService.createDeviceAlertConfig(createReqVO));
    }

    @Operation(summary = "更新设备告警配置")
    @PutMapping("/config/update")
    public CommonResult<Boolean> updateDeviceAlertConfig(@Valid @RequestBody DeviceAlertConfigSaveReqVO updateReqVO) {
        deviceAlertConfigService.updateDeviceAlertConfig(updateReqVO);
        return CommonResult.success(true);
    }

    @Operation(summary = "删除设备告警配置")
    @DeleteMapping("/config/delete")
    public CommonResult<Boolean> deleteDeviceAlertConfig(@RequestParam("id") Long id) {
        deviceAlertConfigService.deleteDeviceAlertConfig(id);
        return CommonResult.success(true);
    }

    @Operation(summary = "获得设备告警配置")
    @GetMapping("/config/get")
    public CommonResult<DeviceAlertConfig> getDeviceAlertConfig(@RequestParam("id") Long id) {
        return CommonResult.success(deviceAlertConfigService.getDeviceAlertConfig(id));
    }

    @Operation(summary = "获得设备告警配置分页")
    @GetMapping("/config/page")
    public CommonResult<PageResult<DeviceAlertConfig>> getDeviceAlertConfigPage(@Valid DeviceAlertConfigPageReqVO pageReqVO) {
        return CommonResult.success(deviceAlertConfigService.getDeviceAlertConfigPage(pageReqVO));
    }

    @Operation(summary = "获得设备告警配置列表（根据设备ID）")
    @GetMapping("/config/list-by-device")
    public CommonResult<List<DeviceAlertConfig>> getDeviceAlertConfigListByDeviceId(@RequestParam("deviceId") Long deviceId) {
        return CommonResult.success(deviceAlertConfigService.getDeviceAlertConfigListByDeviceId(deviceId));
    }

    @Operation(summary = "获得设备告警配置列表（根据产品Key）")
    @GetMapping("/config/list-by-product")
    public CommonResult<List<DeviceAlertConfig>> getDeviceAlertConfigListByProductKey(@RequestParam("productKey") String productKey) {
        return CommonResult.success(deviceAlertConfigService.getDeviceAlertConfigListByProductKey(productKey));
    }

    @Operation(summary = "获得设备告警记录分页")
    @GetMapping("/record/page")
    public CommonResult<PageResult<DeviceAlertRecord>> getDeviceAlertRecordPage(@Valid DeviceAlertRecordPageReqVO pageReqVO) {
        return CommonResult.success(deviceAlertConfigService.getDeviceAlertRecordPage(pageReqVO));
    }

    @Operation(summary = "获得设备告警记录列表（根据设备ID）")
    @GetMapping("/record/list-by-device")
    public CommonResult<List<DeviceAlertRecord>> getDeviceAlertRecordListByDeviceId(@RequestParam("deviceId") Long deviceId) {
        return CommonResult.success(deviceAlertConfigService.getDeviceAlertRecordListByDeviceId(deviceId));
    }
}
