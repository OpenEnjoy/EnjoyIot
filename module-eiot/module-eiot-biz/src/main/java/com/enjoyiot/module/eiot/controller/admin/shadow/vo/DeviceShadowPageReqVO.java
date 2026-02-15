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

import com.enjoyiot.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 设备影子分页请求 VO
 *
 * @author EnjoyIot
 */
@Schema(description = "管理后台 - 设备影子分页请求 VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeviceShadowPageReqVO extends PageParam {

    @Schema(description = "产品Key", example = "a1b2c3d4")
    private String productKey;

    @Schema(description = "设备唯一标识", example = "device001")
    private String dn;

    @Schema(description = "设备ID", example = "1001")
    private Long deviceId;
}
