
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
package com.enjoyiot.module.eiot.controller.admin.channelconfig.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 通道配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ChannelConfigRespVO {

    @Schema(description = "通道配置id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28160")
    @ExcelProperty("通道配置id")
    private Long id;

    @Schema(description = "配置名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("配置名称")
    private String title;

    @Schema(description = "通道编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("通道编码")
    private String code;

    @Schema(description = "通道配置参数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("通道配置参数")
    private String param;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "机构id", example = "18017")
    @ExcelProperty("机构id")
    private Long deptId;

}
