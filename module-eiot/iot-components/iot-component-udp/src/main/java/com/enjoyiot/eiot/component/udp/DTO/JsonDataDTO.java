package com.enjoyiot.eiot.component.udp.DTO;

import com.enjoyiot.eiot.component.udp.enums.UDPReportTypeEnum;
import com.enjoyiot.eiot.component.udp.model.DeviceData;
import lombok.Data;

import java.util.Collections;
import java.util.Map;

/**
 * @title: JsonDataDTO  （示例  解析json数据的DTO）
 * @description:
 * @date: 2025/5/30
 * @author: stuil
 * @copyright: Copyright (c) 2025
 * @version: 1.0
 */
@Data
public class JsonDataDTO implements DeviceData {

    /**
     * 设备ID
     */
    private String devId;

    /**
     * 时间戳
     */
    private String uwbTimestamp;
    /**
     * 原始报文
     */
    private String jsonData;


    @Override
    public String getDeviceId() {
        return "";
    }

    @Override
    public UDPReportTypeEnum reportType() {
        return null;
    }

    @Override
    public Map<String, Object> toMap() {
        return Collections.emptyMap();
    }
}
