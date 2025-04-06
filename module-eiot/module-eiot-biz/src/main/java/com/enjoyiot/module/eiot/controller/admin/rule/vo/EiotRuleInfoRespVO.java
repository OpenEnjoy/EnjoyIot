
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
package com.enjoyiot.module.eiot.controller.admin.rule.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import com.enjoyiot.module.eiot.api.rule.dto.FilterConfig;
import com.enjoyiot.module.eiot.api.task.dto.RuleAction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 规则引擎 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EiotRuleInfoRespVO {

    @Schema(description = "规则id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12640")
    @ExcelProperty("规则id")
    private Long id;

    @Schema(description = "规则名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("规则名称")
    private String name;

    @Schema(description = "类型(flow数据流转 scene场景联动)")
    @ExcelProperty("类型")
    private String typ;

    @Schema(description = "状态(0启用 1禁用)", example = "1")
    @ExcelProperty("状态(0启用 1禁用)")
    private Integer state;

    @Schema(description = "描述")
    @ExcelProperty("描述")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "机构id", example = "21724")
    @ExcelProperty("机构id")
    private Long deptId;

    @Schema(description = "过滤器")
    private List<FilterConfig> filters;

    @Schema(description = "监听器")
    private List<FilterConfig> listeners;

    @Schema(description = "动作")
    private List<RuleAction> actions;
}
