
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
package com.enjoyiot.module.infra.dal.dataobject.config;

import com.enjoyiot.framework.mybatis.core.dataobject.BaseDO;
import com.enjoyiot.module.infra.enums.config.ConfigTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 参数配置表
 *
 * @author EnjoyIot
 */
@TableName("infra_config")
@KeySequence("infra_config_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ConfigDO extends BaseDO {

    /**
     * 参数主键
     */
    @TableId
    private Long id;
    /**
     * 参数分类
     */
    private String category;
    /**
     * 参数名称
     */
    private String name;
    /**
     * 参数键名
     *
     * 支持多 DB 类型时，无法直接使用 key + @TableField("config_key") 来实现转换，原因是 "config_key" AS key 而存在报错
     */
    private String configKey;
    /**
     * 参数键值
     */
    private String value;
    /**
     * 参数类型
     *
     * 枚举 {@link ConfigTypeEnum}
     */
    private Integer type;
    /**
     * 是否可见
     *
     * 不可见的参数，一般是敏感参数，前端不可获取
     */
    private Boolean visible;
    /**
     * 备注
     */
    private String remark;

}
