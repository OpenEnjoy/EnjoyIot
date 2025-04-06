package com.enjoyiot.module.eiot.api.modbus.dto;

import com.enjoyiot.module.eiot.api.TenantModel;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModbusInfo extends TenantModel {
    private static final long serialVersionUID = 1L;


    private Long id;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品key
     */
    private String productKey;

    /**
     * 说明
     */
    private String remark;
}
