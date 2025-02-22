
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
package com.enjoyiot.module.eiot.controller.admin.virtualdevice.vo;

import com.enjoyiot.module.eiot.api.rule.dto.FilterConfig;
import com.enjoyiot.module.eiot.api.task.dto.RuleAction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "管理后台 - 规则引擎新增/修改 Request VO")
@Data
public class EiotVirtualDeviceSaveReqVO {

    /**
     * 虚拟设备id
     */
    @Schema(description = "虚拟设备id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12640")
    private Long id;

    /**
     * 所属用户
     */
    private String uid;

    /**
     * 虚拟设备名称
     */
    @Schema(description = "虚拟设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "虚拟设备名称不能为空")
    private String name;

    /**
     * 产品key
     */
    private String productKey;

    /**
     * 虚拟的目标设备列表
     */
    @Schema(description = "虚拟的目标设备列表")
    private List<Long> devices = new ArrayList<>();

    /**
     * 虚拟类型
     */
    @Schema(description = "虚拟类型", example = "1")
    private String type;

    /**
     * 设备行为脚本
     */
    private String script;

    /**
     * 触发方式执行方式
     */
    @Schema(description = "触发方式执行方式", example = "1")
    private String trigger;

    /**
     * 触发表达式
     */
    @Schema(description = "触发表达式", example = "1")
    private String triggerExpression;

    /**
     * 运行状态
     */
    @Schema(description = "状态(0启用 1禁用)", example = "1")
    private Integer state;


}
