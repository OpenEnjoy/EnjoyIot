
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
package com.enjoyiot.module.eiot.controller.admin.component.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 组件配置 Base VO")
@Data
@SuperBuilder
@NoArgsConstructor
public class ComponentBaseVO {

    @Schema(description = "组件名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "测试组件")
    @NotNull(message = "组件名称不能为空")
    private String name;

    @Schema(description = "组件类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "mqtt")
    @NotNull(message = "组件类型不能为空")
    private String type;

    @Schema(description = "组件配置", requiredMode = Schema.RequiredMode.REQUIRED)
    private String config;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "备注", example = "我是备注")
    private String remark;

}