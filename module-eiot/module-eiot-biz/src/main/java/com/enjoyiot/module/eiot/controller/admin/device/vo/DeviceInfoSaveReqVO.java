
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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Schema(description = "管理后台 - 设备信息新增/修改 Request VO")
@Data
public class DeviceInfoSaveReqVO {

    @Schema(description = "设备id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3442")
    private Long id;

    @Schema(description = "设备唯一标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "设备唯一标识不能为空")
    private String dn;

    @Schema(description = "产品key", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "产品key不能为空")
    private String productKey;

    @Schema(description = "机构id", example = "13057")
    private Long deptId;

    @Schema(description = "设备属性")
    private String properties;

    @Schema(description = "别名", example = "李四")
    private String name;

    @Schema(description = "设备序列号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "设备序列号不能为空")
    private String serialNo;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "经纬度")
    private Double lat;

    @Schema(description = "经纬度")
    private Double lon;

    private String model;

    @Schema(description = "父id")

    private Long parentId;
    @Schema(description = "设备密钥")

    private String secret;

    private Long tenantId;
    @Schema(description = "地址信息")

    private String addr;
    @Schema(description = "固件版本")

    private String firmVersion;

}
