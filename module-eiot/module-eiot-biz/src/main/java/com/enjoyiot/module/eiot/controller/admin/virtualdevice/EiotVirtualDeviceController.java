/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot]
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
package com.enjoyiot.module.eiot.controller.admin.virtualdevice;

import com.enjoyiot.eiot.IVirtualDeviceLogData;
import com.enjoyiot.framework.common.pojo.CommonResult;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.util.object.BeanUtils;
import com.enjoyiot.module.eiot.api.IdReqVo;
import com.enjoyiot.module.eiot.api.virtualdevice.dto.VirtualDevice;
import com.enjoyiot.module.eiot.api.virtualdevice.dto.VirtualDeviceLog;
import com.enjoyiot.module.eiot.controller.admin.virtualdevice.vo.*;
import com.enjoyiot.module.eiot.service.virtualdevice.VirtualDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.enjoyiot.framework.common.pojo.CommonResult.success;

@Slf4j
@Tag(name = "管理后台 - 虚拟设备")
@Validated
@RestController
@RequestMapping("/eiot/virtual_device")
public class EiotVirtualDeviceController {

    @Autowired
    private VirtualDeviceService virtualDeviceService;


    @PostMapping("/list")
    @Operation(summary = "获得规则引擎分页")
    @PreAuthorize("@ss.hasPermission('eiot:virtual-device:query')")
    public CommonResult<PageResult<VirtualDevice>> selectPage(@Valid @RequestBody VirtualDevicePageReqVO reqVO) {
        PageResult<VirtualDevice> pageResult = virtualDeviceService.selectPage(reqVO);
        return success(BeanUtils.toBean(pageResult, VirtualDevice.class));
    }

    @GetMapping("/getDetail")
    @Operation(summary = "获取虚拟设备")
    @Parameter(name = "id", description = "虚拟设备设备id", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('eiot:virtual-device:query')")
    public CommonResult<VirtualDevice> getVirtualDevice(@RequestParam("id") Long id) {
        VirtualDevice virtualDevice = virtualDeviceService.getVirtualDevice(id);
        return success(virtualDevice);
    }

    /**
     * 添加虚拟设备
     */
    @PostMapping("/add")
    @Operation(summary = "添加虚拟设备")
    @PreAuthorize("@ss.hasPermission('eiot:virtual-device:create')")
    public CommonResult<Long> addVirtualDevice(@Valid @RequestBody EiotVirtualDeviceSaveReqVO virtualDevice) {
        return success(virtualDeviceService.saveVirtualDevice(virtualDevice));
    }

    /**
     * 修改虚拟设备
     */
    @Operation(summary = "更新虚拟设备")
    @PostMapping("/update")
    @PreAuthorize("@ss.hasPermission('eiot:virtual-device:update')")
    public CommonResult<Boolean> updateVirtualDevice(@RequestBody VirtualDevice virtualDevice) {
        virtualDeviceService.updateVirtualDevice(virtualDevice);
        return CommonResult.success(true);
    }

    /**
     * 保存虚拟设备映射
     */
    @Operation(summary = "保存虚拟设备映射")
    @PostMapping("/saveDevices")
    @PreAuthorize("@ss.hasPermission('eiot:virtual-device:update')")
    public CommonResult<Boolean> saveDevices(@RequestBody EiotVirtualSaveDevicesMappingVo virtualDevice) {
        virtualDeviceService.saveVirtualDeviceMapping(virtualDevice);
        return CommonResult.success(true);
    }

    /**
     * 批量删除
     */
    @PostMapping("/batchDelete")
    @Operation(summary = "删除虚拟设备")
    @Parameter(name = "id", description = "设备id", required = true)
    @PreAuthorize("@ss.hasPermission('eiot:virtual-device:delete')")
    public CommonResult<Boolean> batchDeleteVirtualDevice(@RequestBody List<Long> ids) {
        ids.forEach(this::deleteVirtualDevice);
        return success(true);
    }


    /**
     * 删除
     */
    @PostMapping("/delete")
    @Operation(summary = "删除虚拟设备")
    @Parameter(name = "id", description = "设备id", required = true)
    @PreAuthorize("@ss.hasPermission('eiot:virtual-device:delete')")
    public CommonResult<Boolean> deleteVirtualDevice(@RequestBody Long id) {
        virtualDeviceService.deleteVirtualDevice(id);
        return success(true);
    }

    @PostMapping("/run")
    @Operation(summary = "手动执行虚拟设备")
    @Parameter(name = "id", description = "虚拟设备设备id", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('eiot:virtual-device:update')")
    public CommonResult<Boolean> run(@RequestBody IdReqVo reqVo) {
        virtualDeviceService.run(reqVo.getId());
        return success(true);
    }


    @PostMapping("/setState")
    @Operation(summary = "设置虚拟设备状态")
    @Parameter(name = "id", description = "虚拟设备设备id", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('iot:virtual-device:setState')")
    public CommonResult<Boolean> setState(@Valid @RequestBody EiotVirtualDeviceSetStateReqVO reqest) {
        if (!VirtualDevice.STATE_RUNNING.equals(reqest.getState())
                && !VirtualDevice.STATE_STOPPED.equals(reqest.getState())) {
            throw new RuntimeException("state is illegal");
        }
        virtualDeviceService.setState(reqest.getId(), reqest.getState());
        return success(true);
    }

    @PostMapping("/saveScript")
    @Operation(summary = "保存运行脚本")
    @Parameter(name = "id", description = "保存运行脚本", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('iot:virtual-device:query')")
    public CommonResult<Boolean> getVirtualDevice(@Valid @RequestBody EiotVirtualSaveScriptVo saveScriptVo) {
        virtualDeviceService.saveScript(saveScriptVo);
        return success(true);
    }


    /**
     * 取虚拟设备执行日志
     */
    @PostMapping("/logs/list")
    @Operation(summary = "取虚拟设备执行日志")
    @Parameter(name = "id", description = "取虚拟设备执行日志", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('iot:virtual-device:query')")
    public CommonResult<PageResult<VirtualDeviceLog>> getLogs(@Validated @RequestBody VirtualDeviceLogPageReqVO data) {
        PageResult<VirtualDeviceLog> pageResult = virtualDeviceService.findByVirtualDeviceId(data.getVirtualDeviceId(), data.getPageNo(), data.getPageSize());
        return CommonResult.success(pageResult);
    }
}
