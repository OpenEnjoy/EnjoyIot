/*
 * Copyright [2025] [Enjoy-iot]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.enjoyiot.module.eiot.api.shadow;

import com.enjoyiot.module.eiot.api.shadow.dto.DeviceShadowDTO;

import java.util.List;
import java.util.Map;

/**
 * 设备影子 API 接口
 *
 * @author EnjoyIot
 */
public interface DeviceShadowApi {

    /**
     * 获取设备影子
     *
     * @param deviceId 设备ID
     * @return 设备影子
     */
    DeviceShadowDTO getByDeviceId(Long deviceId);



    /**
     * 更新上报状态（设备上行消息）- 增量合并
     *
     * @param deviceId 设备ID
     * @param reported 上报状态
     * @param version 设备上报的版本号
     * @return 更新后的版本号
     */
    Long updateReported(Long deviceId, Map<String, Object> reported, Long version);

    /**
     * 删除上报状态中的指定属性（设备上行消息）
     *
     * @param deviceId 设备ID
     * @param keys 要删除的属性key列表
     * @param version 设备上报的版本号
     * @return 更新后的版本号
     */
    Long deleteReportedProperties(Long deviceId, List<String> keys, Long version);

    /**
     * 完全替换上报状态（设备上行消息）- 用于清空或完全替换
     *
     * @param deviceId 设备ID
     * @param reported 新的上报状态（传空map表示清空）
     * @param version 设备上报的版本号
     * @return 更新后的版本号
     */
    Long replaceReported(Long deviceId, Map<String, Object> reported, Long version);

    /**
     * 清空期望状态（设备上行消息）
     *
     * @param deviceId 设备ID
     * @param version 设备上报的版本号
     * @return 更新后的版本号
     */
    Long clearDesired(Long deviceId, Long version);

    /**
     * 清空设备影子数据（version = -1 时调用）
     * 将 desired 和 reported 清空，version 重置为 0
     *
     * @param deviceId 设备ID
     * @return 更新后的版本号（固定为 0）
     */
    Long clearShadow(Long deviceId);



    /**
     * 推送控制消息到设备
     *
     * @param deviceId 设备ID
     */
    void pushControlToDevice(Long deviceId);
}
