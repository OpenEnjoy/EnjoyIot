
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
package com.enjoyiot.module.eiot.controller.admin.showmodel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 产品显示模型新增/修改 Request VO")
@Data
public class ShowModelSaveReqVO {

    @Schema(description = "显示id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4925")
    private Long id;

    @Schema(description = "显示内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "显示内容不能为空")
    private String cnf;

    @Schema(description = "类型 0:详情页, 1配置页", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "类型 0:详情页, 1配置页不能为空")
    private Integer typ;

    @Schema(description = "配置名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "配置名称不能为空")
    private String name;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "模型code", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotEmpty(message = "模型code不能为空")
    private String modelCode;

    @Schema(description = "状态（0正常 1禁用）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态（0正常 1禁用）不能为空")
    private Integer status;

    @NotEmpty(message = "产品key不能为空")
    private String productKey;
}
