package com.enjoyiot.module.eiot.dal.dataobject.modbus;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.enjoyiot.framework.tenant.core.db.TenantBaseDO;
import lombok.*;


@TableName("eiot_modbus_info")
@KeySequence("eiot_modbus_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModbusInfoDO extends TenantBaseDO {

    /**
     * modbus id
     */
    @TableId
    private Long id;

    /**
     * 产品名称
     */
    private String name;

    /**
     * productKey
     */
    private String productKey;

    /**
     * 说明
     */
    private String remark;


}
