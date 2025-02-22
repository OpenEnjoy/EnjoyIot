
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
package com.enjoyiot.module.eiot.dal.dataobject.product;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.enjoyiot.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

/**
 * 物联网产品 DO
 *
 * @author EnjoyIot
 */
@TableName("eiot_product")
@KeySequence("eiot_product_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDO extends TenantBaseDO {

    /**
     * 产品id
     */
    @TableId
    private Long id;
    /**
     * 产品名称
     */
    private String name;
    /**
     * 产品分类id
     */
    private Long categoryId;
    /**
     * productKey
     */
    private String productKey;
    /**
     * mcu code
     */
    private String mcuCode;
    /**
     * 功能介绍
     */
    private String remark1;
    /**
     * 图片url
     */
    private String imgUrl;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态（0启用 1禁用）
     */
    private Integer status;
    /**
     * 设备类型(0 网关设备, 1 网关子设备, 2 直连设备, 3 非联网设备 )
     */
    private Integer nodeType;
    /**
     * 协议code
     */
    private String protocolCode;
    /**
     * 保活时间
     */
    private Long keepAliveTime;
    /**
     * 产品密钥
     */
    private String productSecret;
    /**
     * 是否透传
     */
    private Boolean transparent;

    private Integer locateType;
}
