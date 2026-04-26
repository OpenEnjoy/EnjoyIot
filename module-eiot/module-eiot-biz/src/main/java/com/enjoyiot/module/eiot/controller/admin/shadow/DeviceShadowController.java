/*
 * Copyright [2025] [Enjoy-iot]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.enjoyiot.module.eiot.controller.admin.shadow;


import com.enjoyiot.framework.common.pojo.CommonResult;
import com.enjoyiot.module.eiot.api.shadow.DeviceShadowApi;
import com.enjoyiot.module.eiot.controller.admin.shadow.vo.*;
import com.enjoyiot.module.eiot.convert.DeviceShadowConvert;
import com.enjoyiot.module.eiot.dal.dataobject.shadow.DeviceShadowDO;
import com.enjoyiot.module.eiot.service.shadow.DeviceShadowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import static com.enjoyiot.framework.common.pojo.CommonResult.success;

/**
 * 设备影子 Controller（用于设备详情页）
 *
 * @author EnjoyIot
 */
@Tag(name = "管理后台 - 设备影子")
@RestController
@RequestMapping("/eiot/device-shadow")
@Validated
@Slf4j
public class DeviceShadowController {

    @Resource
    private DeviceShadowService deviceShadowService;

    @Resource
    private DeviceShadowApi deviceShadowApi;

    @GetMapping("/get")
    @Operation(summary = "查询设备影子")
    @PreAuthorize("@ss.hasPermission('iot:device:query')")
    public CommonResult<DeviceShadowRespVO> getDeviceShadow(@RequestParam("deviceId") Long deviceId) {
        
        DeviceShadowDO shadow = deviceShadowService.getByDeviceId(deviceId);
        if (shadow == null) {
            return success(null);
        }

        DeviceShadowRespVO respVO = buildShadowRespVO(shadow);
        return success(respVO);
    }

    @PostMapping("/update")
    @Operation(summary = "更新设备影子（下发期望状态）")
    @PreAuthorize("@ss.hasPermission('iot:device:update')")
    public CommonResult<Boolean> updateDeviceShadow(
            @RequestParam("deviceId") Long deviceId,
            @RequestBody Map<String, Object> desired,
            @RequestParam("version") Long version) {
        
        // 更新期望状态
        deviceShadowService.updateDesired(deviceId, desired, version);
        
        // 推送控制消息到设备
        deviceShadowApi.pushControlToDevice(deviceId);
        
        return success(true);
    }

    /**
     * 构建影子响应VO（包含delta计算）
     */
    private DeviceShadowRespVO buildShadowRespVO(DeviceShadowDO shadow) {
        DeviceShadowRespVO respVO = DeviceShadowConvert.INSTANCE.convertToRespVO(shadow);
        
        // 计算delta
        Map<String, Object> delta = deviceShadowService.getDelta(shadow.getDeviceId());
        respVO.setDelta(delta);
        
        return respVO;
    }
}
