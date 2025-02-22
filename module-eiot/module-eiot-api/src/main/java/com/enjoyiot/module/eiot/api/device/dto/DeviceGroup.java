/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot]
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
package com.enjoyiot.module.eiot.api.device.dto;


import com.enjoyiot.module.eiot.api.TenantModel;
import lombok.Data;

/**
 * 设备分组
 */
@Data
public class DeviceGroup  extends TenantModel {

    /**
     * 分组id
     */
    private Long id;

    /**
     * 分组名称
     */
    private String name;

    /**
     * 所属用户
     */
    private Long uid;

    /**
     * 分组说明
     */
    private String remark;


    /**
     * 分组类型(0系统, 1用户, 字典)
     */
    private Integer typ;

}
