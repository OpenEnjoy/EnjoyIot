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

package com.enjoyiot.module.eiot.controller.admin.ota;


import cn.hutool.core.util.ObjectUtil;

import com.enjoyiot.framework.common.pojo.CommonResult;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.IdReqVo;
import com.enjoyiot.module.eiot.controller.admin.ota.vo.*;
import com.enjoyiot.module.eiot.service.ota.OtaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.enjoyiot.module.eiot.api.enums.ErrorCodeConstants.FILE_NOT_NULL;

/**
 * @Author: Enjoy-iot
 * @Date: 2023/5/19 20:42
 * @Description:
 */
@Tag(name= "ota升级管理")
@Slf4j
@RestController
@RequestMapping("/eiot/ota")
public class OtaController  {

    @Resource
    private OtaService otaService;

    @Operation(summary ="升级包上传")
    @PreAuthorize("@ss.hasPermission('iot:ota:add')")
    @PostMapping(value = "/package/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResult<OtaPackageUploadVo> packageUpload(@RequestPart("file") MultipartFile file) throws Exception {
        if (ObjectUtil.isNull(file)) {
            throw exception(FILE_NOT_NULL);
        }
        return CommonResult.success(otaService.uploadFile(file));
    }

    @Operation(summary ="新增升级包")
    @PreAuthorize("@ss.hasPermission('iot:ota:add')")
    @PostMapping("/package/add")
    public CommonResult<Long> packageAdd(@RequestBody @Valid OtaPackageBo request) {
        return CommonResult.success(otaService.addOtaPackage(request));
    }

    @Operation(summary ="删除升级包")
    @PreAuthorize("@ss.hasPermission('iot:ota:remove')")
    @PostMapping("/package/delById")
    public CommonResult<Boolean> delPackageById(@RequestBody @Valid IdReqVo request) {
        return CommonResult.success(otaService.delOtaPackageById(request.getId()));
    }

    @Operation(summary ="升级包列表")
    @PreAuthorize("@ss.hasPermission('iot:ota:query')")
    @PostMapping("/package/getList")
    public CommonResult<PageResult<OtaPackage>> packageList(@RequestBody @Validated OtaPackagePageReq request) {
        return CommonResult.success(otaService.getOtaPackagePageList(request));
    }

    @Operation(summary ="OTA升级")
    @PreAuthorize("@ss.hasPermission('iot:ota:upgrade')")
    @PostMapping("/device/upgrade")
    public CommonResult<DeviceUpgradeVo> deviceUpgrade(@RequestBody DeviceUpgradeBo request) {
        String result = otaService.startUpgrade(request.getOtaId(), request.getDeviceIds());
        return CommonResult.success(DeviceUpgradeVo.builder().result(result).build());
    }

    @Operation(summary ="设备升级结果查询")
    @PreAuthorize("@ss.hasPermission('iot:ota:query')")
    @PostMapping("/device/detail")
    public CommonResult<PageResult<DeviceOtaDetailVo>> otaDeviceDetail(@RequestBody DeviceOtaDetailPageReq request) {
        return CommonResult.success(otaService.otaDeviceDetail(request));
    }

    @Operation(summary ="设备升级批次查询")
    @PreAuthorize("@ss.hasPermission('iot:ota:query')")
    @PostMapping("/device/info")
    public CommonResult<PageResult<DeviceOtaInfoVo>> otaDeviceInfo(@RequestBody DeviceOtaPageReq request) {
        return CommonResult.success(otaService.otaDeviceInfo(request));
    }

    @Operation(summary ="ota升级测试')")
    @PostMapping("/testStartUpgrade")
    public void testStartUpgrade( ) {
        otaService.testStartUpgrade();
    }

}
