
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
package com.enjoyiot.module.eiot.controller.admin.channelconfig;

import com.enjoyiot.module.eiot.service.alert.ChannelConfigService;
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

import com.enjoyiot.module.eiot.controller.admin.channelconfig.vo.*;

@Tag(name = "管理后台 - 通道配置")
@RestController
@RequestMapping("/eiot/channel-config")
@Validated
public class ChannelConfigController {

    @Resource
    private ChannelConfigService channelConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建通道配置")
    @PreAuthorize("@ss.hasPermission('iot:channel-config:create')")
    public CommonResult<Long> createChannelConfig(@Valid @RequestBody ChannelConfig createReqVO) {
        return success(channelConfigService.createChannelConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新通道配置")
    @PreAuthorize("@ss.hasPermission('iot:channel-config:update')")
    public CommonResult<Boolean> updateChannelConfig(@Valid @RequestBody ChannelConfig updateReqVO) {
        channelConfigService.updateChannelConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除通道配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('iot:channel-config:delete')")
    public CommonResult<Boolean> deleteChannelConfig(@RequestParam("id") Long id) {
        channelConfigService.deleteChannelConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得通道配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('iot:channel-config:query')")
    public CommonResult<ChannelConfigRespVO> getChannelConfig(@RequestParam("id") Long id) {
        ChannelConfig channelConfig = channelConfigService.getChannelConfig(id);
        return success(BeanUtils.toBean(channelConfig, ChannelConfigRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得通道配置分页")
    @PreAuthorize("@ss.hasPermission('iot:channel-config:query')")
    public CommonResult<PageResult<ChannelConfigRespVO>> getChannelConfigPage(@Valid ChannelConfigPageReqVO pageReqVO) {
        PageResult<ChannelConfig> pageResult = channelConfigService.getChannelConfigPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ChannelConfigRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出通道配置 Excel")
    @PreAuthorize("@ss.hasPermission('iot:channel-config:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportChannelConfigExcel(@Valid ChannelConfigPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ChannelConfig> list = channelConfigService.getChannelConfigPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "通道配置.xls", "数据", ChannelConfigRespVO.class,
                        BeanUtils.toBean(list, ChannelConfigRespVO.class));
    }

}
