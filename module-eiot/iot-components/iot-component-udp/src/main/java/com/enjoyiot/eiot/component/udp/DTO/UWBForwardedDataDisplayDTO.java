package com.enjoyiot.eiot.component.udp.DTO;

import lombok.Data;

/**
 * @title: UWBForwardedDataDisplayVO
 * @description:
 * @date: 2025/5/27
 * @author: stuil
 * @copyright: Copyright (c) 2025
 * @version: 1.0
 */
@Data
public class UWBForwardedDataDisplayDTO extends UWBDataBaseDTO {
    private static final long serialVersionUID = 1L;
    /**
     * ID 预留主键
     */
    private Long id;
    /**
     * 包序，数据相关的采样序号，为0到255，或0到65535循环的数值
     */
    private Integer seq;

    /**
     * 层编号，为10进制整数
     */
    private Integer layId;

    /**
     * [POS]：矢量位置x,y,z，形如 15.25,27.15,1.50
     */
    private String pos;

    /**
     * [POS]：矢量位置x
     */
    private String posX;
    /**
     * [POS]：矢量位置Y
     */
    private String posY;
    /**
     * [POS]：矢量位置Z
     */
    private String posZ;

    /**
     * 组编号，为10进制整数
     */
    private Integer gid;

}
