/*
 * Copyright [2025] [Enjoy-iot]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.enjoyiot.module.eiot.enums.shadow;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 设备影子错误码枚举
 * 参考阿里云IoT设备影子数据流标准
 *
 * @author EnjoyIot
 */
@Getter
@AllArgsConstructor
public enum ShadowErrorCodeEnum {

    /**
     * 不正确的JSON格式
     */
    INVALID_JSON(400, "不正确的JSON格式"),

    /**
     * 影子数据缺少method信息
     */
    MISSING_METHOD(401, "影子数据缺少method信息"),

    /**
     * 影子数据缺少state字段
     */
    MISSING_STATE(402, "影子数据缺少state字段"),

    /**
     * 影子数据中version值不是数字
     */
    INVALID_VERSION(403, "影子数据中version值不是数字"),

    /**
     * 影子数据缺少reported字段
     */
    MISSING_REPORTED(404, "影子数据缺少reported字段"),

    /**
     * 影子数据中reported属性字段为空
     */
    EMPTY_REPORTED(405, "影子数据中reported属性字段为空"),

    /**
     * 影子数据中method是无效的方法
     */
    INVALID_METHOD(406, "影子数据中method是无效的方法"),

    /**
     * 影子内容为空
     */
    EMPTY_SHADOW(407, "影子内容为空"),

    /**
     * 影子数据中reported属性个数超过128个
     */
    TOO_MANY_ATTRIBUTES(408, "影子数据中reported属性个数超过128个"),

    /**
     * 影子版本冲突
     */
    VERSION_CONFLICT(409, "影子版本冲突"),

    /**
     * 服务端处理异常
     */
    SERVER_ERROR(500, "服务端处理异常");

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误信息
     */
    private final String message;

    /**
     * 根据错误码获取枚举
     */
    public static ShadowErrorCodeEnum getByCode(Integer code) {
        for (ShadowErrorCodeEnum errorCode : values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode;
            }
        }
        return null;
    }
}
