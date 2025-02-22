
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
package com.enjoyiot.module.eiot.controller.admin.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 物联网产品新增/修改 Request VO")
@Data
public class ProductSaveReqVO {

    @Schema(description = "产品id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31556")
    private Long id;

    @Schema(description = "产品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "产品名称不能为空")
    private String name;

    @Schema(description = "产品分类id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19389")
    @NotNull(message = "产品分类id不能为空")
    private Long categoryId;

    @Schema(description = "productKey", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "productKey不能为空")
    private String productKey;

    @Schema(description = "mcu code")
    private String mcuCode;

    @Schema(description = "功能介绍")
    private String remark1;

    @Schema(description = "图片url", example = "http://www.enjoy-iot.cn")
    private String imgUrl;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "状态（0启用 1禁用）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态（0启用 1禁用）不能为空")
    private Integer status;

    @Schema(description = "设备类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "设备类型不能为空")
    private Integer nodeType;

    @Schema(description = "协议code", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "协议code不能为空")
    private String protocolCode;

    @Schema(description = "保活时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "保活时间不能为空")
    private Long keepAliveTime;


    @Schema(description = "是否透传", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否透传不能为空")
    private Boolean transparent;


    @Schema(description = "定位方式", requiredMode = Schema.RequiredMode.REQUIRED)

    private Integer locateType;
}
