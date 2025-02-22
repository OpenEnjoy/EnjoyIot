
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
package com.enjoyiot.module.eiot.controller.admin.thingmodel;

import com.enjoyiot.module.eiot.api.thingmodel.dto.ThingModel;
import com.enjoyiot.module.eiot.controller.admin.thingmodel.vo.ThingModelSaveReqVO;
import com.enjoyiot.module.eiot.service.product.ThingModelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;

import com.enjoyiot.framework.common.pojo.CommonResult;

import static com.enjoyiot.framework.common.pojo.CommonResult.success;


@Tag(name = "管理后台 - 产品物模型")
@RestController
@RequestMapping("/eiot/thing-model")
@Validated
public class ThingModelController {

    @Resource
    private ThingModelService thingModelService;


    @PutMapping("/save")
    @Operation(summary = "更新产品物模型")
    @PreAuthorize("@ss.hasPermission('iot:thing-model:update')")
    public CommonResult<Boolean> saveThingModel(@Valid @RequestBody ThingModelSaveReqVO updateReqVO) {
        thingModelService.saveThingModel(updateReqVO);
        return success(true);
    }


    @GetMapping("/get")
    @Operation(summary = "获得产品物模型")
    @Parameter(name = "productKey", description = "productKey", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('iot:thing-model:query')")
    public CommonResult<ThingModel> getThingModel(@RequestParam("productKey") String productKey) {
        ThingModel thingModel = thingModelService.getThingModelByProductKey(productKey);
        return success(thingModel);
    }

}
