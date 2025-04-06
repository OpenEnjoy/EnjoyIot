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
package com.enjoyiot.module.eiot.api.task.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskInfo  {

    public static String TYPE_TIMER = "timer";
    public static String TYPE_DELAY = "delay";

    public static String STATE_STOP = "stopped";
    public static String STATE_RUNNING = "running";
    public static String STATE_INVALID = "invalid";
    public static String STATE_FINISHED = "finished";

    private Long id;

    private String name;

    /**
     * 任务类型
     */
    private String type;

    /**
     * 表达式
     * 定时器使用cron表达式
     * 延时器使用延时时长（秒）
     */
    private String expression;

    /**
     * 描述
     */
    private String remark;

    /**
     * 任务输出
     */
    private List<RuleAction> actions;

    /**
     * 任务状态
     */
    private String state;

    /**
     * 创建者
     */
    private String uid;

    private LocalDateTime createTime;

    /**
     * 操作备注
     */
    private String reason;

    private Long tenantId;

    public Long delayTime() {
        if (!TYPE_DELAY.equals(type)) {
            return null;
        }
        if (expression == null) {
            return null;
        }
        return Long.parseLong(expression);
    }
}
