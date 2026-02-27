/*
 * Copyright [2025] [Enjoy-iot]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.enjoyiot.module.eiot.controller.admin.shadow.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 设备影子响应 VO
 *
 * @author EnjoyIot
 */
@Schema(description = "管理后台 - 设备影子响应 VO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceShadowRespVO {

    @Schema(description = "主键ID", example = "1")
    private Long id;

    @Schema(description = "设备ID", example = "1001")
    private Long deviceId;

    @Schema(description = "产品Key", example = "a1b2c3d4")
    private String productKey;

    @Schema(description = "设备唯一标识", example = "device001")
    private String dn;

    @Schema(description = "期望状态")
    private Map<String, Object> desired;

    @Schema(description = "上报状态")
    private Map<String, Object> reported;

    @Schema(description = "差异状态(期望与上报的差异)")
    private Map<String, Object> delta;

    @Schema(description = "元数据")
    private Map<String, Object> metadata;

    @Schema(description = "影子版本号", example = "10")
    private Long version;

    @Schema(description = "期望状态版本号", example = "5")
    private Long desiredVersion;

    @Schema(description = "上报状态版本号", example = "8")
    private Long reportedVersion;

    @Schema(description = "最后期望状态更新时间")
    private LocalDateTime lastDesiredTime;

    @Schema(description = "最后上报状态更新时间")
    private LocalDateTime lastReportedTime;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
