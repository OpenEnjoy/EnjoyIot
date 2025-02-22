
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
package com.enjoyiot.module.eiot.controller.admin.component;

import com.enjoyiot.framework.common.pojo.CommonResult;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.controller.admin.component.vo.*;
import com.enjoyiot.module.eiot.convert.ComponentConvert;
import com.enjoyiot.module.eiot.dal.dataobject.component.ComponentDO;
import com.enjoyiot.module.eiot.service.component.ComponentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.enjoyiot.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 组件配置")
@RestController
@RequestMapping("/eiot/component")
@Validated
public class ComponentController {

    @Resource
    private ComponentService componentService;

    @PostMapping("/create")
    @Operation(summary = "创建组件")
    @PreAuthorize("@ss.hasPermission('eiot:component:create')")
    public CommonResult<Long> createComponent(@Valid @RequestBody ComponentCreateReqVO createReqVO) {
        return success(componentService.createComponent(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新组件")
    @PreAuthorize("@ss.hasPermission('eiot:component:update')")
    public CommonResult<Boolean> updateComponent(@Valid @RequestBody ComponentUpdateReqVO updateReqVO) {
        componentService.updateComponent(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除组件")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('eiot:component:delete')")
    public CommonResult<Boolean> deleteComponent(@RequestParam("id") Long id) {
        componentService.deleteComponent(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得组件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('eiot:component:query')")
    public CommonResult<ComponentRespVO> getComponent(@RequestParam("id") Long id) {
        ComponentDO component = componentService.getComponent(id);
        return success(ComponentConvert.INSTANCE.convert(component));
    }

    @GetMapping("/page")
    @Operation(summary = "获得组件配置分页")
    @PreAuthorize("@ss.hasPermission('eiot:component:query')")
    public CommonResult<PageResult<ComponentRespVO>> getComponentPage(@Valid ComponentPageReqVO pageVO) {
        PageResult<ComponentDO> pageResult = componentService.getComponentPage(pageVO);
        return success(ComponentConvert.INSTANCE.convertPage(pageResult));
    }

}
