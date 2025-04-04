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
package com.enjoyiot.module.eiot.api.rule.dto;

import com.enjoyiot.module.eiot.api.task.dto.RuleAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleInfo {

    public static final Integer STATE_STOPPED = 0;
    public static final Integer STATE_RUNNING = 1;

    public static final String TYPE_SCENE = "scene";
    public static final String TYPE_FLOW = "flow";

    private Long id;

    private String name;

    private String typ;

    private List<FilterConfig> listeners;

    private List<FilterConfig> filters;

    private List<RuleAction> actions;



    private Integer state;

    private String remark;


    private Long tenantId;

    private LocalDateTime createTime;

    public List<FilterConfig> getListeners() {
        return listeners == null ? new ArrayList<>() : listeners;
    }

    public List<FilterConfig> getFilters() {
        return filters == null ? new ArrayList<>() : filters;
    }

    public List<RuleAction> getActions() {
        return actions == null ? new ArrayList<>() : actions;
    }
}
