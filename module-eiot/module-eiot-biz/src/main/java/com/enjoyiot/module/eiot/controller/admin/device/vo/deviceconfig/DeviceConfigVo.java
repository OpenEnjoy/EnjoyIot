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

package com.enjoyiot.module.eiot.controller.admin.device.vo.deviceconfig;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


@Schema(description = "DeviceConfigVo")
@Data
@ExcelIgnoreUnannotated
public class DeviceConfigVo implements Serializable {

    private static final long serialVersionUID = -1L;

    @Schema(description = "设备配置id")
    @ExcelProperty(value = "设备配置id")
    private String id;

    @Schema(description = "设备配置json内容")
    @ExcelProperty(value = "设备配置json内容")
    private String config;

    @Schema(description = "创建时间")
    @ExcelProperty(value = "创建时间")
    private Long createAt;

    @Schema(description = "设备id")
    @ExcelProperty(value = "设备id")
    private String deviceId;

    @Schema(description = "设备名称")
    @ExcelProperty(value = "设备名称")
    private String deviceName;

    @Schema(description = "产品key")
    @ExcelProperty(value = "产品key")
    private String productKey;


}
