
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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 通道配置新增/修改 Request VO")
@Data
public class ChannelConfigSaveReqVO {

    @Schema(description = "通道配置id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28160")
    private Long id;

    @Schema(description = "配置名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "配置名称不能为空")
    private String title;

    @Schema(description = "通道编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "通道编码不能为空")
    private String code;

    @Schema(description = "通道配置参数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "通道配置参数不能为空")
    private String param;

}
