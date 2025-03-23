
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

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 物联网产品 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ProductRespVO {

    @Schema(description = "产品id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31556")
    @ExcelProperty("产品id")
    private Long id;

    @Schema(description = "产品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("产品名称")
    private String name;

    @Schema(description = "产品分类id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19389")
    @ExcelProperty("产品分类id")
    private Long categoryId;
    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "19389")
    private String categoryName;

    @Schema(description = "productKey", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("productKey")
    private String productKey;

    @Schema(description = "mcu code")
    @ExcelProperty("mcu code")
    private String mcuCode;

    @Schema(description = "功能介绍")
    @ExcelProperty("功能介绍")
    private String remark1;

    @Schema(description = "图片url", example = "http://www.enjoy-iot.cn")
    @ExcelProperty("图片url")
    private String imgUrl;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "状态（0启用 1禁用）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态（0启用 1禁用）")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "设备类型(0 网关设备, 1 网关子设备, 2 直连设备, 3 非联网设备 )", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("设备类型(0 网关设备, 1 网关子设备, 2 直连设备, 3 非联网设备 )")
    private Integer nodeType;

    @Schema(description = "协议code", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("协议code")
    private String protocolCode;

    @Schema(description = "保活时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("保活时间")
    private Long keepAliveTime;

    @Schema(description = "产品密钥", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("产品密钥")
    private String productSecret;

    @Schema(description = "是否透传", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否透传")
    private Boolean transparent;


    @Schema(description = "定位方式", requiredMode = Schema.RequiredMode.REQUIRED)

    private Integer locateType;

}
