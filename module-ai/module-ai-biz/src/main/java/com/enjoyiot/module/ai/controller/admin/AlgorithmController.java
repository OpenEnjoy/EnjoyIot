
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
package com.enjoyiot.module.ai.controller.admin;

import com.enjoyiot.framework.common.pojo.CommonResult;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.util.object.BeanUtils;
import com.enjoyiot.module.ai.controller.admin.vo.AlgorithmCreateReqVO;
import com.enjoyiot.module.ai.controller.admin.vo.AlgorithmPageReqVO;
import com.enjoyiot.module.ai.controller.admin.vo.AlgorithmRespVO;
import com.enjoyiot.module.ai.controller.admin.vo.AlgorithmUpdateReqVO;
import com.enjoyiot.module.ai.dal.dataobject.AlgorithmDO;

import com.enjoyiot.module.ai.service.AlgorithmService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.Valid;

import static com.enjoyiot.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 算法模型")
@RestController
@RequestMapping("/ai/algorithm")
@Validated
public class AlgorithmController {

    @Resource
    private AlgorithmService algorithmService;

    @PostMapping("/create")
    @Operation(summary = "创建算法模型")
    @PreAuthorize("@ss.hasPermission('ai:algorithm:create')")
    public CommonResult<Long> createAlgorithm(@Valid @RequestBody AlgorithmCreateReqVO createReqVO) {
        return success(algorithmService.createAlgorithm(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新算法模型")
    @PreAuthorize("@ss.hasPermission('ai:algorithm:update')")
    public CommonResult<Boolean> updateAlgorithm(@Valid @RequestBody AlgorithmUpdateReqVO updateReqVO) {
        algorithmService.updateAlgorithm(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除算法模型")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('ai:algorithm:delete')")
    public CommonResult<Boolean> deleteAlgorithm(@RequestParam("id") Long id) {
        algorithmService.deleteAlgorithm(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得算法模型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('ai:algorithm:query')")
    public CommonResult<AlgorithmRespVO> getAlgorithm(@RequestParam("id") Long id) {
        AlgorithmDO algorithm = algorithmService.getAlgorithm(id);
        return success(BeanUtils.toBean(algorithm, AlgorithmRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得算法模型分页")
    @PreAuthorize("@ss.hasPermission('ai:algorithm:query')")
    public CommonResult<PageResult<AlgorithmRespVO>> getAlgorithmPage(@Valid AlgorithmPageReqVO pageVO) {
        PageResult<AlgorithmDO> pageResult = algorithmService.getAlgorithmPage(pageVO);
        return success(BeanUtils.toBean(pageResult, AlgorithmRespVO.class));
    }

}