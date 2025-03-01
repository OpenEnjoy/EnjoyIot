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
package com.enjoyiot.module.eiot.controller.admin.alert;


import com.enjoyiot.framework.common.pojo.CommonResult;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.util.object.BeanUtils;
import com.enjoyiot.module.eiot.api.IdReqVo;
import com.enjoyiot.module.eiot.api.alert.dto.AlertConfig;
import com.enjoyiot.module.eiot.api.alert.dto.AlertConfigPageReqVO;
import com.enjoyiot.module.eiot.api.alert.dto.AlertRecord;
import com.enjoyiot.module.eiot.controller.admin.alertconfig.vo.AlertConfigRespVO;
import com.enjoyiot.module.eiot.controller.admin.alertconfig.vo.AlertConfigSaveReqVO;
import com.enjoyiot.module.eiot.controller.admin.alertconfig.vo.AlertRecordPageReq;
import com.enjoyiot.module.eiot.service.alert.AlertConfigService;
import com.enjoyiot.module.eiot.service.alert.AlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.enjoyiot.framework.common.pojo.CommonResult.success;


@Tag(name = "告警中心")
@Slf4j
@RestController
@RequestMapping("/alert")
public class AlertController {

    @Resource
    private AlertService alertService;

    @Resource
    private AlertConfigService alertConfigService;

    @Operation(summary ="新增告警中心配置")
    @PreAuthorize("@ss.hasPermission('iot:alertConfig:add')")
    @PostMapping("/createAlertConfig")
    public CommonResult<Long> createAlertConfig(@Valid @RequestBody AlertConfigSaveReqVO createReqVO) {
        return success(alertConfigService.createAlertConfig(createReqVO));
    }

    @Operation(summary ="编辑告警中心配置")
    @PreAuthorize("@ss.hasPermission('iot:alertConfig:edit')")
    @PostMapping("/updateAlertConfig")
    public CommonResult<Boolean> updateAlertConfig(@Valid @RequestBody AlertConfigSaveReqVO updateReqVO) {
        alertConfigService.updateAlertConfig(updateReqVO);
        return success(true);
    }
    @Operation(summary ="删除告警中心配置")
    @PreAuthorize("@ss.hasPermission('iot:alertConfig:remove')")
    @PostMapping("/deleteAlertConfigById")
    public CommonResult<Boolean> deleteAlertConfig(@RequestBody IdReqVo reqVo) {
        alertConfigService.deleteAlertConfig(reqVo.getId());
        return success(true);
    }

    @Operation(summary ="查询告警中心配置分页")
    @PreAuthorize("@ss.hasPermission('iot:alertConfig:query')")
    @PostMapping("/selectAlertConfigPage")
    public CommonResult<PageResult<AlertConfigRespVO>> getAlertConfigPage(@Valid @RequestBody AlertConfigPageReqVO pageReqVO) {
        PageResult<AlertConfig> pageResult = alertConfigService.getAlertConfigPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AlertConfigRespVO.class));
    }


    @Operation(summary ="查询告警消息分页")
    @PreAuthorize("@ss.hasPermission('iot:alert:query')")
    @PostMapping("/selectAlertRecordPage")
    public CommonResult<PageResult<AlertRecord>> selectAlertRecordPage(@RequestBody @Validated AlertRecordPageReq request) {
        return success(alertService.selectAlertRecordPage(request));
    }


}
