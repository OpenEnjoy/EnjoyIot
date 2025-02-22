
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
package com.enjoyiot.module.eiot.controller.admin.alertconfig;

import com.enjoyiot.module.eiot.api.alert.dto.AlertConfig;
import com.enjoyiot.module.eiot.api.alert.dto.AlertConfigPageReqVO;
import com.enjoyiot.module.eiot.service.alert.AlertConfigService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.enjoyiot.framework.common.pojo.PageParam;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.pojo.CommonResult;
import com.enjoyiot.framework.common.util.object.BeanUtils;
import static com.enjoyiot.framework.common.pojo.CommonResult.success;

import com.enjoyiot.framework.excel.core.util.ExcelUtils;

import com.enjoyiot.framework.apilog.core.annotation.ApiAccessLog;
import static com.enjoyiot.framework.apilog.core.enums.OperateTypeEnum.*;

import com.enjoyiot.module.eiot.controller.admin.alertconfig.vo.*;

@Tag(name = "管理后台 - 报警配置")
@RestController
@RequestMapping("/eiot/alert-config")
@Validated
public class AlertConfigController {

    @Resource
    private AlertConfigService alertConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建报警配置")
    @PreAuthorize("@ss.hasPermission('iot:alert-config:create')")
    public CommonResult<Long> createAlertConfig(@Valid @RequestBody AlertConfigSaveReqVO createReqVO) {
        return success(alertConfigService.createAlertConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新报警配置")
    @PreAuthorize("@ss.hasPermission('iot:alert-config:update')")
    public CommonResult<Boolean> updateAlertConfig(@Valid @RequestBody AlertConfigSaveReqVO updateReqVO) {
        alertConfigService.updateAlertConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除报警配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('iot:alert-config:delete')")
    public CommonResult<Boolean> deleteAlertConfig(@RequestParam("id") Long id) {
        alertConfigService.deleteAlertConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得报警配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('iot:alert-config:query')")
    public CommonResult<AlertConfigRespVO> getAlertConfig(@RequestParam("id") Long id) {
        AlertConfig alertConfig = alertConfigService.getAlertConfig(id);
        return success(BeanUtils.toBean(alertConfig, AlertConfigRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得报警配置分页")
    @PreAuthorize("@ss.hasPermission('iot:alert-config:query')")
    public CommonResult<PageResult<AlertConfigRespVO>> getAlertConfigPage(@Valid AlertConfigPageReqVO pageReqVO) {
        PageResult<AlertConfig> pageResult = alertConfigService.getAlertConfigPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AlertConfigRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出报警配置 Excel")
    @PreAuthorize("@ss.hasPermission('iot:alert-config:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportAlertConfigExcel(@Valid AlertConfigPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<AlertConfig> list = alertConfigService.getAlertConfigPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "报警配置.xls", "数据", AlertConfigRespVO.class,
                        BeanUtils.toBean(list, AlertConfigRespVO.class));
    }

}
