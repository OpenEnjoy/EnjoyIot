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
package com.enjoyiot.module.eiot.dal.dataobject.deviceconfig;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.enjoyiot.framework.tenant.core.db.TenantBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备配置 DO
 */
@TableName("eiot_device_config")
@KeySequence("eiot_device_config_seq") // 兼容 Oracle/PostgreSQL 等，MySQL 可忽略
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceConfigDO extends TenantBaseDO {

    /**
     * 配置id
     */
    @TableId
    private Long id;

    /**
     * 配置内容（JSON）
     */
    private String config;

    /**
     * 产品key
     */
    private String productKey;

    /**
     * 设备唯一编码
     */
    private String dn;
}

