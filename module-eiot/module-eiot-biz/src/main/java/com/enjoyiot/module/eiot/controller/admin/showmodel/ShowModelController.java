
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
package com.enjoyiot.module.eiot.controller.admin.showmodel;

import com.enjoyiot.module.eiot.controller.admin.showmodel.vo.ShowModelRespVO;
import com.enjoyiot.module.eiot.controller.admin.showmodel.vo.ShowModelSaveReqVO;
import com.enjoyiot.module.eiot.service.product.ShowModelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import java.util.*;

import com.enjoyiot.framework.common.pojo.CommonResult;

import static com.enjoyiot.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 产品显示模型")
@RestController
@RequestMapping("/eiot/show-model")
@Validated
public class ShowModelController {

    @Resource
    private ShowModelService showModelService;


    @PostMapping("/save")
    @Operation(summary = "保存产品显示模型")
    @PreAuthorize("@ss.hasPermission('iot:show-model:update')")
    public CommonResult<Boolean> updateShowModel(@Valid @RequestBody ShowModelSaveReqVO updateReqVO) {
        showModelService.saveShowModel(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除产品显示模型")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('iot:show-model:delete')")
    public CommonResult<Boolean> deleteShowModel(@RequestParam("id") Long id) {
        showModelService.deleteShowModel(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得产品显示模型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('iot:show-model:query')")
    public CommonResult<ShowModelRespVO> getShowModel(@RequestParam("id") Long id) {
        ShowModelRespVO showModel = showModelService.getShowModel(id);
        return success(showModel);
    }

    @GetMapping("/getByProductKey")
    @Operation(summary = "获得产品显示模型")
    @Parameter(name = "productKey", description = "productKey", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('iot:show-model:query')")
    public CommonResult<List<ShowModelRespVO>> getShowModelByProductKey(@RequestParam("productKey") String id) {
        List<ShowModelRespVO> res = showModelService.getShowModelByProductKey(id);
        return success(res);
    }


}
