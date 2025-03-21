
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
package com.enjoyiot.module.eiot.controller.admin.device.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 设备信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DeviceShortRespVO {

    @Schema(description = "设备id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3442")
    @ExcelProperty("设备id")
    private Long id;

    @Schema(description = "设备唯一标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备唯一标识")
    private String dn;

    @Schema(description = "产品key", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("产品key")
    private String productKey;

    @Schema(description = "机构id", example = "13057")
    @ExcelProperty("机构id")
    private Long deptId;

    @Schema(description = "设备属性")
    @ExcelProperty("设备属性")
    private String propertys;


    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "别名", example = "李四")
    @ExcelProperty("别名")
    private String name;

    @Schema(description = "在线(0:否, 1:在线,2未激活,3禁用)")
    @ExcelProperty("在线(0:否, 1:在线)")
    private Integer state;

    @Schema(description = "离线时间")
    @ExcelProperty("离线时间")
    private Long offlineTime;

    @Schema(description = "在线时间")
    @ExcelProperty("在线时间")
    private Long onlineTime;

    @Schema(description = "设备序列号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备序列号")
    private String serialNo;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "经纬度")
    @ExcelProperty("经纬度")
    private Double lat;

    @Schema(description = "经纬度")
    @ExcelProperty("经纬度")
    private Double lon;


    private String model;

    private Long parentId;

    private String secret;

    private Long tenantId;
    @Schema(description = "位置信息")

    private String addr;
    @Schema(description = "固件版本")

    private String firmVersion;
    @Schema(description = "产品名称")

    private String productName;

    private Integer nodeType;

}
