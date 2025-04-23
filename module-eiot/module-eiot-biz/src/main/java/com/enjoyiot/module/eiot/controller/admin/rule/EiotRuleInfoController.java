
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
package com.enjoyiot.module.eiot.controller.admin.rule;

import com.enjoyiot.module.eiot.api.IdReqVo;
import com.enjoyiot.module.eiot.api.rule.dto.RuleInfo;
import com.enjoyiot.module.eiot.api.rule.dto.RuleInfoPageReqVO;
import com.enjoyiot.module.eiot.api.task.dto.TaskInfoPageReq;
import com.enjoyiot.module.eiot.api.task.dto.TaskLog;
import com.enjoyiot.module.eiot.service.rule.EiotRuleInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;

import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.pojo.CommonResult;
import com.enjoyiot.framework.common.util.object.BeanUtils;

import static com.enjoyiot.framework.common.pojo.CommonResult.success;

import com.enjoyiot.module.eiot.controller.admin.rule.vo.*;

@Tag(name = "管理后台 - 规则引擎")
@RestController
@RequestMapping("/eiot/rule_engine")
@Validated
public class EiotRuleInfoController {

    @Resource
    private EiotRuleInfoService ruleEngineService;

    @PostMapping("/save")
    @Operation(summary = "保存规则引擎")
    @PreAuthorize("@ss.hasPermission('iot:rule-info:create')")
    public CommonResult<Long> createRuleInfo(@Valid @RequestBody EiotRuleInfoSaveReqVO createReqVO) {
        return success(ruleEngineService.saveRule(createReqVO));
    }

    @Operation(summary = "暂停规则")
    @PreAuthorize("@ss.hasPermission('iot:rule-info:pause')")
    @PostMapping("/pause")
    public CommonResult<Boolean> pauseRule(@Validated @RequestBody IdReqVo req) {
        return success(ruleEngineService.pauseRule(req.getId()));
    }

    @Operation(summary = "恢复规则")
    @PreAuthorize("@ss.hasPermission('iot:rule-info:resume')")
    @PostMapping("/resume")
    public CommonResult<Boolean> resumeRule(@Validated @RequestBody IdReqVo req) {
        return success(ruleEngineService.resumeRule(req.getId()));
    }

    @PostMapping("/delete")
    @Operation(summary = "删除规则引擎")
    @PreAuthorize("@ss.hasPermission('iot:rule-info:remove')")
    public CommonResult<Boolean> deleteRuleInfo(@Validated @RequestBody IdReqVo req) {
        ruleEngineService.deleteRuleInfo(req.getId());
        return success(true);
    }

    @PostMapping("/get")
    @Operation(summary = "获得规则引擎")
    @PreAuthorize("@ss.hasPermission('iot:rule-info:query')")
    public CommonResult<EiotRuleInfoRespVO> getRuleInfo(@Validated @RequestBody IdReqVo req) {
        RuleInfo ruleInfo = ruleEngineService.getRuleInfo(req.getId());
        return success(BeanUtils.toBean(ruleInfo, EiotRuleInfoRespVO.class));
    }

    @PostMapping("/page")
    @Operation(summary = "获得规则引擎分页")
    @PreAuthorize("@ss.hasPermission('iot:rule-info:query')")
    public CommonResult<PageResult<EiotRuleInfoRespVO>> getRuleInfoPage(@Valid @RequestBody RuleInfoPageReqVO pageReqVO) {
        PageResult<RuleInfo> pageResult = ruleEngineService.getRuleInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, EiotRuleInfoRespVO.class));
    }

    @Operation(summary = "规则日志")
    @PreAuthorize("@ss.hasPermission('iot:rule-info:query')")
    @PostMapping("/ruleLog/list")
    public CommonResult<PageResult<RuleLogVo>> getRuleLogs(
            @Validated @RequestBody RuleLogPageReq request
    ) {
        return success(ruleEngineService.selectRuleLogPage(request));
    }

    @Operation(summary = "清理日志")
    @PreAuthorize("@ss.hasPermission('iot:rule-info:remove')")
    @PostMapping("/ruleLog/clear")
    public CommonResult<Boolean> clearRuleLogs(@Validated @RequestBody RuleIdReq request) {
        Long ruleId = request.getId();
        return CommonResult.success(ruleEngineService.clearRuleLogByRuleId(ruleId));
    }

    @Operation(summary = "定时任务列表")
    @PreAuthorize("@ss.hasPermission('iot:task:query')")
    @PostMapping("/tasks/list")
    public CommonResult<PageResult<TaskInfoVo>> tasks(@Validated @RequestBody TaskInfoPageReq request) {
        return success(ruleEngineService.selectTaskPageList(request));
    }

    @Operation(summary = "保存定时任务")
    @PreAuthorize("@ss.hasPermission('iot:task:edit')")
    @PostMapping("/task/save")
    public CommonResult<Long> saveTask(@Validated @RequestBody TaskInfoSaveReqVo taskInfo) {
        return success(ruleEngineService.saveTask(taskInfo));
    }

    @Operation(summary = "停止定时任务")
    @PreAuthorize("@ss.hasPermission('iot:task:edit')")
    @PostMapping("/task/pause")
    public CommonResult<Boolean> pauseTask(@Validated @RequestBody IdReqVo reqVo) {

        return success(ruleEngineService.pauseTask(reqVo.getId()));
    }

    @Operation(summary = "恢复定时任务")
    @PreAuthorize("@ss.hasPermission('iot:task:edit')")
    @PostMapping("/task/resume")
    public CommonResult<Boolean> resumeTask(@Validated @RequestBody IdReqVo reqVo) {
        return success(ruleEngineService.resumeTask(reqVo.getId()));
    }

    @Operation(summary = "更新定时任务")
    @PreAuthorize("@ss.hasPermission('iot:task:edit')")
    @PostMapping("/task/renew")
    public CommonResult<Boolean> renewTask(@Validated @RequestBody IdReqVo reqVo) {
        return success(ruleEngineService.renewTask(reqVo.getId()));

    }

    @Operation(summary = "删除定时任务")
    @PreAuthorize("@ss.hasPermission('iot:task:remove')")
    @PostMapping("/task/delete")
    public CommonResult<Boolean> deleteTask(@Validated @RequestBody IdReqVo request) {

        return success(ruleEngineService.deleteTask(request.getId()));
    }

    @Operation(summary = "定时任务日志list")
    @PreAuthorize("@ss.hasPermission('iot:task:query')")
    @PostMapping("/taskLogs/list")
    public CommonResult<PageResult<TaskLog>> getTaskLogs(
            @Validated @RequestBody TaskLogPageReq request
    ) {
        return success(ruleEngineService.selectTaskLogPageList(request));
    }

    @Operation(summary = "清除定时任务日志")
    @PreAuthorize("@ss.hasPermission('iot:task:remove')")
    @PostMapping("/taskLogs/clear")
    public CommonResult<Boolean> clearTaskLogs(@Validated @RequestBody TaskIdReq req) {
        return success(ruleEngineService.clearTaskLogs(req.getId()));
    }
}
