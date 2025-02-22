
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
package com.enjoyiot.module.eiot.dal.dataobject.component;

import com.enjoyiot.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 组件配置 DO
 */
@TableName("eiot_component")
@KeySequence("eiot_component_seq") 
@Data
@EqualsAndHashCode(callSuper = true)
public class ComponentDO extends BaseDO {

    /**
     * 组件编号
     */
    @TableId
    private Long id;

    /**
     * 组件名称
     */
    private String name;

    /**
     * 组件类型
     */
    private String type;

    /**
     * 组件配置(JSON格式)
     */
    private String config;

    /**
     * 状态(0-停用, 1-启用)
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

}