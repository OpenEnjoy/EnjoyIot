/*
 * Copyright [2025] [Enjoy-iot]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.enjoyiot.module.eiot.dal.dataobject.shadow;

import com.baomidou.mybatisplus.annotation.*;
import com.enjoyiot.framework.mybatis.core.dataobject.BaseDO;
import com.enjoyiot.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 设备影子 DO
 *
 * @author EnjoyIot
 */
@TableName("eiot_device_shadow")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceShadowDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 设备ID
     */
    private Long deviceId;

    /**
     * 产品Key
     */
    private String productKey;

    /**
     * 设备唯一标识
     */
    @TableField("dn")
    private String dn;

    /**
     * 期望状态JSON
     */
    private String desired;

    /**
     * 上报状态JSON
     */
    private String reported;

    /**
     * 元数据JSON
     */
    private String metadata;

    /**
     * 影子版本号
     */
    private Long version;

    /**
     * 最后期望状态更新时间
     */
    private LocalDateTime lastDesiredTime;

    /**
     * 最后上报状态更新时间
     */
    private LocalDateTime lastReportedTime;


}
