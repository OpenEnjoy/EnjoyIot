
/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com | Tel: 19918996474
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot] | Tel: 19918996474
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */
package com.enjoyiot.module.eiot.dal.dataobject.virtualdevice;

import com.baomidou.mybatisplus.annotation.*;
import com.enjoyiot.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

/**
 * 虚拟设备DO
 *
 * @author clickear
 */
@TableName("eiot_virtual_device")
@KeySequence("eiot_virtual_device_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VirtualDeviceDO extends TenantBaseDO {

    /**
     * 虚拟设备id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 虚拟设备名称
     */
    private String name;

    /**
     * 产品key
     */
    private String productKey;

    /**
     * 虚拟类型
     */
    private String type;

    /**
     * 设备行为脚本
     */
    private String script;

    /**
     * 触发方式执行方式
     */
    @TableField("`trigger`") //TODO 启用 PostgreSQL、KaiwuDB 需要注释掉这个注解
    private String trigger;

    /**
     * 触发表达式
     */
    private String triggerExpression;

    /**
     * 运行状态
     */
    private String state;

}
