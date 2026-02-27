/*
 * Copyright [2025] [Enjoy-iot]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.enjoyiot.module.eiot.api.shadow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 设备影子 DTO
 *
 * @author EnjoyIot
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceShadowDTO {

    /**
     * 主键ID
     */
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
    private String dn;

    /**
     * 期望状态
     */
    private Map<String, Object> desired;

    /**
     * 上报状态
     */
    private Map<String, Object> reported;

    /**
     * 元数据
     */
    private ShadowMetadata metadata;

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

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 影子元数据
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShadowMetadata {
        /**
         * 期望状态元数据
         */
        private Map<String, PropertyMetadata> desired;

        /**
         * 上报状态元数据
         */
        private Map<String, PropertyMetadata> reported;
    }

    /**
     * 属性元数据
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PropertyMetadata {
        /**
         * 时间戳
         */
        private Long timestamp;
    }
}
