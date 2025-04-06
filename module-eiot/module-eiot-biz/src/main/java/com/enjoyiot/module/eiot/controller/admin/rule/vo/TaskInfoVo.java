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

package com.enjoyiot.module.eiot.controller.admin.rule.vo;


import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import com.enjoyiot.module.eiot.api.task.dto.RuleAction;
import com.enjoyiot.module.eiot.api.task.dto.TaskInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Schema(description = "TaskInfoVo")
@Data
@ExcelIgnoreUnannotated
public class TaskInfoVo implements Serializable {

    private static final long serialVersionUID = -1L;

    @Schema(description = "主键")
    @ExcelProperty(value = "主键")
    private String id;

    @Schema(description = "任务输出")
    @ExcelProperty(value = "任务输出")
    private List<RuleAction> actions;

    @Schema(description = "创建时间")
    @ExcelProperty(value = "创建时间")
    private Long createAt;

    @Schema(description = "描述")
    @ExcelProperty(value = "描述")
    private String remark;

    @Schema(description = "表达式")
    @ExcelProperty(value = "表达式")
    private String expression;

    @Schema(description = "任务名称")
    @ExcelProperty(value = "任务名称")
    private String name;

    @Schema(description = "操作备注")
    @ExcelProperty(value = "操作备注")
    private String reason;

    @Schema(description = "任务状态")
    @ExcelProperty(value = "任务状态")
    private String state;

    @Schema(description = "任务类型")
    @ExcelProperty(value = "任务类型")
    private String type;

    @Schema(description = "延时时长秒")
    private Integer seconds;

    @Schema(description = "创建者")
    @ExcelProperty(value = "创建者")
    private String uid;

    public void setSeconds(Integer seconds) {
        if (TaskInfo.TYPE_DELAY.equals(getType())) {
            setExpression("" + seconds);
        }
    }

    public Integer getSeconds() {
        if (TaskInfo.TYPE_DELAY.equals(getType())) {
            return Integer.parseInt(getExpression());
        }
        return null;
    }

}
