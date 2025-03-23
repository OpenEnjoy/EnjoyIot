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

package com.enjoyiot.module.eiot.controller.admin.product.vo;


import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


/**
 * 产品配置视图对象 product_config
 *
 * @author EnjoyIot
 * @date 2024-06-19
 */
@Data
public class ProductConfigVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 产品key
     */
    @ExcelProperty(value = "产品key")
    @Schema(description = "产品key")
    private String productKey;

    /**
     * 配置信息
     */
    @ExcelProperty(value = "配置信息")
    @Schema(description = "配置信息")
    private String config;

    /**
     * 配置项目(basic,interface,model,subBasic,subInterface,subModel)
     */
    @ExcelProperty(value = "配置项目(basic,interface,model,subBasic,subInterface,subModel)")
    @Schema(description = "配置项目(basic,interface,model,subBasic,subInterface,subModel)")
    private String configItems;


}
