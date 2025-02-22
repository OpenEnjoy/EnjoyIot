
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
package com.enjoyiot.module.eiot.api.rule;

import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.task.RuleTaskApi;
import com.enjoyiot.module.eiot.api.task.dto.TaskInfo;
import com.enjoyiot.module.eiot.api.task.dto.TaskInfoPageReq;
import com.enjoyiot.module.eiot.controller.admin.rule.vo.TaskInfoVo;
import com.enjoyiot.module.eiot.convert.TaskInfoConvert;
import com.enjoyiot.module.eiot.service.rule.EiotRuleInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.stream.Collectors;

@Service
public class RuleTaskApiImpl implements RuleTaskApi {

    @Resource
    private EiotRuleInfoService ruleInfoService;

    @Override
    public TaskInfo getTask(Long id) {
        return ruleInfoService.getTask(id);
    }

    @Override
    public void updateTask(TaskInfo req) {
        ruleInfoService.updateTask(req);
    }

    @Override
    public PageResult<TaskInfo> selectTaskPage(TaskInfoPageReq request) {
        PageResult<TaskInfoVo> pageResult = ruleInfoService.selectTaskPageList(request);
        return new PageResult<>(pageResult.getList().stream()
                .map(TaskInfoConvert.INSTANCE::convert).collect(Collectors.toList()),
                pageResult.getTotal());
    }

}
