package com.enjoyiot.eiot.component.udp.model;

import com.enjoyiot.eiot.component.udp.enums.UDPReportTypeEnum;

import java.util.Map;

/**
 * @title: DeviceData
 * @description:
 * @date: 2025/5/29
 * @author: stuil
 * @copyright: Copyright (c) 2025
 * @version: 1.0
 */

public interface DeviceData {
    /**
     * 唯一标识设备的 ID （转换到当前业务就是DN）
     */
    String getDeviceId();

    /**
     * 是否是属性上报（true）还是事件上报（false）
     */
    UDPReportTypeEnum reportType();

    /**
     * 转换为 map，用于属性上报
     */
    Map<String, Object> toMap();
}
