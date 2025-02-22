
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
package com.enjoyiot.module.eiot.controller.admin.sip;


import com.enjoyiot.framework.common.pojo.CommonResult;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.controller.admin.sip.vo.SipRelation;
import com.enjoyiot.module.eiot.controller.admin.sip.vo.SipRelationPageReqVO;
import com.enjoyiot.module.eiot.service.sip.SipRelationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 监控设备关联Controller
 *
 * @author kerwincui
 * @date 2024-06-06
 */
@RestController
@RequestMapping("/eiot/iot/relation")
@Tag(name = "监控设备关联")
public class SipRelationController {
    @Resource
    private SipRelationService sipRelationService;

    /**
     * 查询监控设备关联列表
     */
    @PreAuthorize("@ss.hasPermission('iot:relation:list')")
    @GetMapping("/list")
    @Operation(summary = "查询监控设备关联列表")
    public CommonResult<PageResult<SipRelation>> list(SipRelationPageReqVO reqVO) {
//      List<SipRelation> list = sipRelationService.selectSipRelationList(sipRelation);
        PageResult<SipRelation> list = sipRelationService.selectSipRelationPage(reqVO);
        return CommonResult.success(list);
    }

    /**
     * 获取监控设备关联详细信息
     */
    @PreAuthorize("@ss.hasPermission('iot:relation:query')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "获取监控设备关联详细信息")
    public CommonResult<SipRelation> getInfo(@PathVariable("id") Long id) {
        return CommonResult.success(sipRelationService.selectSipRelationById(id));
    }

    @PreAuthorize("@ss.hasPermission('iot:relation:query')")
    @GetMapping(value = "/dev/{deviceId}")
    @Operation(summary = "根据设备id获取关联通道详细信息")
    public CommonResult<List<SipRelation>> getInfoByDeviceId(@PathVariable("deviceId") Long deviceId) {
        return CommonResult.success(sipRelationService.selectSipRelationByDeviceId(deviceId));
    }

    /**
     * 新增或更新监控设备关联
     */
    @PreAuthorize("@ss.hasPermission('iot:relation:add')")
    @PostMapping("/addOrUp")
    @Operation(summary = "新增或更新监控设备关联")
    public CommonResult<Integer> addOrUp(@RequestBody SipRelation sipRelation) {
        return CommonResult.success(sipRelationService.addOrUpdateSipRelation(sipRelation));
    }

    /**
     * 修改监控设备关联
     */
    @PreAuthorize("@ss.hasPermission('iot:relation:edit')")
    @PutMapping
    @Operation(summary = "修改监控设备关联")
    public CommonResult<Integer> edit(@RequestBody SipRelation sipRelation) {
        return CommonResult.success(sipRelationService.updateSipRelation(sipRelation));
    }

    /**
     * 删除监控设备关联
     */
    @PreAuthorize("@ss.hasPermission('iot:relation:remove')")
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除监控设备关联")
    public CommonResult<Integer> remove(@PathVariable Long[] ids) {
        return CommonResult.success(sipRelationService.deleteSipRelationByIds(ids));
    }
}
