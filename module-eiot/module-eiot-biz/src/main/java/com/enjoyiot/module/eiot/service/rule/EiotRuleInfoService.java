
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
package com.enjoyiot.module.eiot.service.rule;

import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.rule.dto.RuleInfo;
import com.enjoyiot.module.eiot.api.rule.dto.RuleInfoPageReqVO;
import com.enjoyiot.module.eiot.api.task.dto.TaskInfo;
import com.enjoyiot.module.eiot.api.task.dto.TaskInfoPageReq;
import com.enjoyiot.module.eiot.api.task.dto.TaskLog;
import com.enjoyiot.module.eiot.controller.admin.rule.vo.*;

/**
 * 规则引擎 Service 接口
 *
 * @author EnjoyIot
 */
public interface EiotRuleInfoService {

    /**
     * 删除规则引擎
     *
     * @param id 编号
     */
    void deleteRuleInfo(Long id);

    /**
     * 获得规则引擎
     *
     * @param id 编号
     * @return 规则引擎
     */
    RuleInfo getRuleInfo(Long id);

    /**
     * 获得规则引擎分页
     *
     * @param pageReqVO 分页查询
     * @return 规则引擎分页
     */
    PageResult<RuleInfo> getRuleInfoPage(RuleInfoPageReqVO pageReqVO);

    Long saveRule(EiotRuleInfoSaveReqVO createReqVO);

    boolean clearRuleLogByRuleId(Long ruleId);

    PageResult<TaskInfoVo> selectTaskPageList(TaskInfoPageReq request);

    Long saveTask(TaskInfoSaveReqVo taskInfo);

    void updateTask(TaskInfo taskInfo);

    TaskInfo getTask(Long id);

    Boolean pauseTask(Long id);

    Boolean resumeTask(Long id);

    Boolean renewTask(Long id);

    Boolean deleteTask(Long id);

    PageResult<TaskLog> selectTaskLogPageList(TaskLogPageReq request);

    Boolean clearTaskLogs(Long taskId);

    PageResult<RuleLogVo> selectRuleLogPage(RuleLogPageReq request);

    Boolean resumeRule(Long id);

    Boolean pauseRule(Long id);
}
