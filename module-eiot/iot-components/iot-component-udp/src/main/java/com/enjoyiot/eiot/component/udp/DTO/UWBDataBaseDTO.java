package com.enjoyiot.eiot.component.udp.DTO;

import com.enjoyiot.eiot.component.udp.enums.UDPReportTypeEnum;
import com.enjoyiot.eiot.component.udp.model.DeviceData;
import com.enjoyiot.eiot.component.udp.utils.MapToEntityUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @title: UWBDataBaseDTO
 * @description: uwb数据公共部分
 * @date: 2025/4/19
 * @author: stuil
 * @copyright: Copyright (c) 2025
 * @version: 1.0
 */
@Data
public class UWBDataBaseDTO implements Serializable, DeviceData {
    private static final long serialVersionUID = 1L;
    @Override
    public String getDeviceId() {
        return devId;
    }

    @Override
    public Map<String, Object> toMap() {
        try {
            return MapToEntityUtil.entityToMap(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UDPReportTypeEnum reportType() {
        // 判断上报类型
        if(this instanceof UWBForwardedDataDisplayDTO || this instanceof UWBForwardedDataStatusOneDTO){
            return UDPReportTypeEnum.PROPERTY_REPORT;
        }
        return UDPReportTypeEnum.EVENT_REPORT;
    }
    /**
     * 报文长度，共3个字节，不足3个字节的，左侧补空格。
     */
    private Integer len;

    /**
     * 设备ID
     */
    private String devId;

    /**
     * [TIMESTAMP]：时间戳，形如YY-MM-DD hh:mm:ss.SSS
     */
    private String uwbTimestamp;
    /**
     * 原始报文
     */
    private String originalMessage;



    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    private String remark;

    private int deviceSpecies;

    private String  tenantId;
}
