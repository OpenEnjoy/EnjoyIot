package com.enjoyiot.eiot.component.udp.DTO;

import lombok.Data;

/**
 * @title: UWBForwardedDataStatusOneEntity
 * @description: display数据
 * @date: 2025/5/27
 * @author: stuil
 * @copyright: Copyright (c) 2025
 * @version: 1.0
 */
@Data
public class UWBForwardedDataStatusOneDTO extends UWBDataBaseDTO {
    /**
     * ID 预留主键
     */
    private Long id;
    /**
     * 子类型  TAG  标签  ANC  基站
     */
    private String subType;
    /**
     * [VBAT]：电池电量，形如 90%
     */
    private String vbat;

    /**
     * 层编号，为10进制整数
     */
    private Integer layId;

    /**
     * 矢量速率，形如 1.02,1.00,0.00
     */
    private String velo;

    /**
     * 注：SOS为标签当前的状态，为16进制状态位掩码。目前，01表示SOS告警，02表示静止状态，04表示休眠状态。
     * 当前定义以标签的实际定义为准。
     */
    private String sos;

    /**
     * [NAME]：设备名称
     */
    private String name;

    /**
     * [NAME]：设备名称
     */
    private String hwv;

    /**
     * [NAME]：设备名称
     */
    private String fwv;

    /**
     * [POS]：矢量速率1
     */
    private String veloOne;
    /**
     * [POS]：矢量速率2
     */
    private String veloTwo;
    /**
     * [POS]：矢量速率3
     */
    private String veloThree;

    /***********************      基站数据            **********************    */
    /**
     * [IPV4]：IPv4地址，形如 192.168.1.1.172  （标签无）
     */
    private String ipv4;
    /**
     * [RGNID]
     */
    private String rgnid;
    /**
     * [X]
     */
    private String x;
    /**
     * [Y]
     */
    private String y;
    /**
     * [Z]
     */
    private String z;

    /***********************      基站数据            **********************    */

}
