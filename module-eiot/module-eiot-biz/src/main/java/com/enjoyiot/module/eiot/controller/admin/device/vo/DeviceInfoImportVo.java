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

package com.enjoyiot.module.eiot.controller.admin.device.vo;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class DeviceInfoImportVo {
    private static final long serialVersionUID = -1L;

    @ExcelProperty(value = "序列号")
    private String serialNo;

    @ExcelProperty(value = "设备名称")
    private String name;

    @Schema(description = "设备唯一标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "设备唯一标识不能为空")
    @ExcelProperty(value = "设备唯一标识")
    private String dn;

    @Schema(description = "设备序列号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "设备序列号不能为空")


    @ExcelProperty(value = "设备型号")
    private String model;
}
