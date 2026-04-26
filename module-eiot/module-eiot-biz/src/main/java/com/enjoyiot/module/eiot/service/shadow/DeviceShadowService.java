/*
 * Copyright [2025] [Enjoy-iot]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.enjoyiot.module.eiot.service.shadow;


import com.enjoyiot.module.eiot.dal.dataobject.shadow.DeviceShadowDO;

import java.util.Map;

/**
 * 设备影子 Service 接口
 *
 * @author EnjoyIot
 */
public interface DeviceShadowService {

    /**
     * 获取设备影子
     *
     * @param deviceId 设备ID
     * @return 设备影子
     */
    DeviceShadowDO getByDeviceId(Long deviceId);



    /**
     * 创建设备影子并缓存
     *
     * @param deviceId 设备ID
     * @return 新创建的设备影子
     */
    DeviceShadowDO createShadow(Long deviceId);

    /**
     * 更新期望状态（下行消息）
     *
     * @param deviceId 设备ID
     * @param desired 期望状态
     * @param version 版本号（用于版本校验）
     */
    void updateDesired(Long deviceId, Map<String, Object> desired, Long version);



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
    Long deleteReportedProperties(Long deviceId, java.util.List<String> keys, Long version);

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
     * @return 更新后的版本号
     */
    Long clearShadow(Long deviceId);

    /**
     * 获取差异状态（期望与上报的差异）
     *
     * @param deviceId 设备ID
     * @return 差异状态
     */
    Map<String, Object> getDelta(Long deviceId);


}
