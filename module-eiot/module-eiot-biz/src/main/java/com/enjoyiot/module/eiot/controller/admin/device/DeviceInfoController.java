
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
package com.enjoyiot.module.eiot.controller.admin.device;

import cn.hutool.core.util.ObjectUtil;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.module.eiot.api.IdReqVo;
import com.enjoyiot.module.eiot.api.device.dto.*;
import com.enjoyiot.module.eiot.controller.admin.device.vo.DeviceInfoRespVO;
import com.enjoyiot.module.eiot.controller.admin.device.vo.*;
import com.enjoyiot.module.eiot.controller.admin.device.vo.deviceconfig.DeviceConfigAddBo;
import com.enjoyiot.module.eiot.controller.admin.device.vo.deviceconfig.DeviceConfigVo;
import com.enjoyiot.module.eiot.controller.admin.device.vo.devicegroup.*;
import com.enjoyiot.module.eiot.controller.admin.iot.vo.DeviceIdReqVo;
import com.enjoyiot.module.eiot.controller.admin.sip.vo.SipRelation;
import com.enjoyiot.module.eiot.controller.admin.thingmodel.vo.ThingModelMessageBo;
import com.enjoyiot.module.eiot.service.device.DeviceInfoService;
import com.enjoyiot.module.eiot.service.device.DeviceManagerService;
import com.enjoyiot.module.eiot.service.sip.SipRelationService;
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

import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 设备信息")
@RestController
@RequestMapping("/eiot/device")
@Validated
public class DeviceInfoController {

    @Resource
    private DeviceInfoService deviceInfoService;

    @Resource
    private DeviceManagerService deviceManagerService;

    @Resource
    private SipRelationService sipRelationService;

    @PostMapping("/create")
    @Operation(summary = "创建设备信息")
    @PreAuthorize("@ss.hasPermission('iot:device-info:create')")
    public CommonResult<Long> createDeviceInfo(@Valid @RequestBody DeviceInfoSaveReqVO createReqVO) {
        return success(deviceInfoService.createDeviceInfo(createReqVO));
    }

    /**
     * 导入设备-批量添加设备
     */
    @Operation(summary = "导入设备")
    @PreAuthorize("@ss.hasPermission('iot:device-info:create')")
    @PostMapping("/importData")
    public CommonResult<DeviceImportRespVO> importDevice(@RequestPart("file") MultipartFile file, @RequestParam("productId") Long productId) throws IOException {
        List<DeviceInfoImportVo> list = ExcelUtils.read(file, DeviceInfoImportVo.class);

        return CommonResult.success(deviceInfoService.importDevice(list, productId));
    }

    /**
     * 获取导入设备模板
     */
    @Operation(summary ="下载设备模板")
    @GetMapping("/exportData")
    public void exportDeviceTemplate(HttpServletResponse response) throws IOException {
        List<DeviceInfoImportVo> list = Arrays.asList(
                DeviceInfoImportVo.builder().name("测试").dn("00:00:00:00:00").serialNo("1111").build()
        );
        ExcelUtils.write(response, "设备模板.xls","设备", DeviceInfoImportVo.class ,list);
    }


    @PutMapping("/update")
    @Operation(summary = "更新设备信息")
    @PreAuthorize("@ss.hasPermission('iot:device-info:update')")
    public CommonResult<Boolean> updateDeviceInfo(@Valid @RequestBody DeviceInfoSaveReqVO updateReqVO) {
        deviceInfoService.updateDeviceInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('iot:device-info:delete')")
    public CommonResult<Boolean> deleteDeviceInfo(@RequestParam("id") Long id) {
        deviceInfoService.deleteDeviceInfo(id);
        return success(true);
    }

    @DeleteMapping("/deleteBatch")
    @Operation(summary = "删除设备信息")
    @Parameter(name = "ids", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('iot:device-info:delete')")
    public CommonResult<Boolean> deleteByIds(@RequestParam("ids") List<Long> ids) {
        deviceInfoService.deleteByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('iot:device-info:query')")
    public CommonResult<DeviceInfoRespVO> getDeviceInfo(@RequestParam("id") Long id) {
        DeviceInfo deviceInfo = deviceInfoService.getDeviceInfo(id);
        return success(BeanUtils.toBean(deviceInfo, DeviceInfoRespVO.class));
    }

    @Operation(summary = "获得设备信息")
    @GetMapping(value = "/getDeviceBySerialNumber/{serialNumber}")
    @PreAuthorize("@ss.hasPermission('iot:device-info:query')")
    public CommonResult<DeviceInfoRespVO> getDeviceBySerialNumber(@PathVariable("serialNumber") String serialNumber) {
        DeviceInfo deviceInfo = deviceInfoService.getDeviceBySerialNo(serialNumber);
        DeviceInfoRespVO ret = BeanUtils.toBean(deviceInfo, DeviceInfoRespVO.class);

        if (ObjectUtil.isNotNull(deviceInfo)){
            //查询关联的监控设备
            SipRelation sipRelation = new SipRelation();
            sipRelation.setReDeviceId(deviceInfo.getId());
            List<SipRelation> sipRelationList = sipRelationService.selectSipRelationList(sipRelation);
            ret.setSipRelationList(sipRelationList);
        }

        return success(ret);
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备信息分页")
    @PreAuthorize("@ss.hasPermission('iot:device-info:query')")
    public CommonResult<PageResult<DeviceShortRespVO>> getDeviceInfoPage(@Valid DeviceInfoPageReqVO pageReqVO) {
        PageResult<DeviceShortInfo> pageResult = deviceInfoService.getDeviceInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DeviceShortRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备信息 Excel")
    @PreAuthorize("@ss.hasPermission('iot:device-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDeviceInfoExcel(@Valid DeviceInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DeviceShortInfo> list = deviceInfoService.getDeviceInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备信息.xls", "数据", DeviceInfoRespVO.class,
                        BeanUtils.toBean(list, DeviceInfoRespVO.class));
    }

    @Operation(summary = "设备物模型日志")
    @PreAuthorize("@ss.hasPermission('iot:deviceLog:query')")
    @PostMapping("/deviceLogs/list")
    public CommonResult<PageResult<ThingModelMessage>> logs(@Validated @RequestBody DeviceLogPageReqVo request) {
        return success( deviceManagerService.logs(request));
    }

    @Operation(summary = "获取设备属性历史数据")
    @PreAuthorize("@ss.hasPermission('iot:deviceLog:query')")
    @PostMapping("/deviceProperty/log/list")
    public CommonResult<List<DeviceProperty>> getPropertyHistory(@Validated @RequestBody
                                                                 DevicePropertyLogQueryBo data) {
        Long deviceId = data.getDeviceId();
        String name = data.getName();
        long start = data.getStart();
        long end = data.getEnd();
        return success(deviceManagerService.getPropertyHistory(deviceId, name, start, end, data.getPageNo(), data.getPageSize()));
    }

    @Operation(summary = "添加标签")
    @PreAuthorize("@ss.hasPermission('iot:device:edit')")
    @PostMapping("/tag/add")
    public CommonResult<Boolean>  addTag(@Validated @RequestBody DeviceTagAddBo bo) {
        return success( deviceManagerService.addTag(bo));
    }

    @Operation(summary = "模拟设备上报")
    @PreAuthorize("@ss.hasPermission('iot:device:query')")
    @PostMapping("/simulateSend")
    public CommonResult<Boolean>  simulateSend(
            @Validated @RequestBody ThingModelMessageBo bo) {
        ThingModelMessage message = BeanUtils.toBean(bo, ThingModelMessage.class);
        return success( deviceManagerService.simulateSend(message));
    }

    /**
     * 消费设备信息消息（实时推送设备信息）
     */
//    @Operation(summary = "消费设备信息消息（实时推送设备信息）")
//    @PreAuthorize("@ss.hasPermission('iot:device:query")
//    @PostMapping("/consumer")
//    public CommonResult< DeferredResult<ThingModelMessage> consumerDeviceInfo(
//            @Validated @RequestBody DeviceConsumerBo> bo
//    ) {
//        DeviceConsumerBo data = bo.getData();
//        return success( deviceManagerService.addConsumer(data.getDeviceId(), data.getClientId());
//    }

    /**
     * 获取分组列表
     */
    @Operation(summary = "获取分组列表")
    @PreAuthorize("@ss.hasPermission('iot:deviceGroup:query')")
    @PostMapping("/groups/list")
    public CommonResult< PageResult<DeviceGroup>> getDeviceGroups(
            @Validated @RequestBody DeviceGroupPageReqVO pageRequest) {
        return success( deviceManagerService.selectGroupPageList(pageRequest));
    }

    /**
     * 添加设备分组
     */
    @Operation(summary = "添加设备分组")
    @PreAuthorize("@ss.hasPermission('iot:deviceGroup:add')")
    @PostMapping("/group/add")
    public CommonResult<Boolean>  addGroup(@Validated @RequestBody DeviceGroupBo group) {
        return success( deviceManagerService.addGroup(group));
    }


    /**
     * 导入设备分组-批量添加设备分组
     */
    @Operation(summary = "导入设备分组")
    @PreAuthorize("@ss.hasPermission('iot:deviceGroup:add')")
    @PostMapping("/group/importData")
    public CommonResult<GroupImportRespVO>  importGroup(@RequestPart("file") MultipartFile file, @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws IOException {
        List<DeviceGroupImportVo> list = ExcelUtils.read(file, DeviceGroupImportVo.class);
        return success( deviceManagerService.importGroup(list, updateSupport));
    }

    /**
     * 获取导入模板
     */
    @Operation(summary = "下载设备分组模板")
    @GetMapping("/group/exportData")
    public  void exportGroupTemplate(HttpServletResponse response) throws IOException {
        List<DeviceGroupImportVo> list = Arrays.asList(
                DeviceGroupImportVo.builder().name("测试分组").groupOrder(99).remark("测试分组").build()
        );
        ExcelUtils.write(response, "设备分组模板.xls","设备分组", DeviceGroupImportVo.class ,list);
    }

    /**
     * 修改设备分组
     */
    @Operation(summary = "修改设备分组")
    @PreAuthorize("@ss.hasPermission('iot:deviceGroup:edit')")
    @PostMapping("/group/edit")
    public CommonResult<Boolean> editGroup(@RequestBody @Validated DeviceGroupBo bo) {
        return success( deviceManagerService.updateGroup(bo));

    }

    /**
     * 删除分组
     */
    @Operation(summary = "删除分组")
    @PreAuthorize("@ss.hasPermission('iot:deviceGroup:remove')")
    @PostMapping("/group/delete")
    public CommonResult<Boolean> deleteGroup(@Validated @RequestBody IdReqVo request) {

        return success( deviceManagerService.deleteGroup(request.getId()));
    }

    /**
     * 清空组下所有设备
     */
    @Operation(summary = "清空组下所有设备")
    @PreAuthorize("@ss.hasPermission('iot:deviceGroup:remove')")
    @PostMapping("/group/clear")
    public CommonResult<Boolean> clearGroup(@Validated @RequestBody IdReqVo req) {
        return success( deviceManagerService.clearGroup(req.getId()));
    }

    /**
     * 添加设备到组
     */
    @Operation(summary = "添加设备到组")
    @PreAuthorize("@ss.hasPermission('iot:deviceGroup:edit')")
    @PostMapping("/group/addDevices")
    public CommonResult<Boolean> addToGroup(@Validated @RequestBody DeviceAddGroupBo bo) {
        return success( deviceManagerService.addDevice2Group(bo));
    }

    /**
     * 将设备从组中移除
     */
    @Operation(summary = "将设备从组中移除")
    @PreAuthorize("@ss.hasPermission('iot:deviceGroup:edit')")
    @PostMapping("/group/removeDevices")
    public CommonResult<Boolean> removeDevices(@Validated @RequestBody DeviceAddGroupBo bo) {

        return success( deviceManagerService.removeDevicesInGroup(bo.getGroupId(), bo.getDeviceIds()));
    }

    /**
     * 保存设备配置
     */
    @Operation(summary = "保存设备配置")
    @PreAuthorize("@ss.hasPermission('iot:device:edit')")
    @PostMapping("/config/save")
    public CommonResult<Boolean> saveConfig(@Validated @RequestBody DeviceConfigAddBo request) {
        DeviceConfig data = BeanUtils.toBean(request, DeviceConfig.class);
        return success( deviceManagerService.saveConfig(data));
    }

    /**
     * 获取设备配置
     */
    @Operation(summary = "获取设备配置")
    @PreAuthorize("@ss.hasPermission('iot:device:query')")
    @PostMapping("/config/get")
    public CommonResult<DeviceConfigVo> getConfig(@Validated @RequestBody DeviceIdReqVo request) {
        Long deviceId = request.getDeviceId();
        return success( deviceManagerService.getConfig(deviceId));
    }


    @Operation(summary = "查询指定设备的属性信息")
    @PostMapping("/getDeviceWithProperty")
    public CommonResult<DeviceInfoWithPropertyVO> getDeviceInfoWithProperty(@RequestBody @Validated DeviceIdReqVo bo) {
        return success(deviceManagerService.getDeviceInfoWithProperty(bo.getDeviceId()));
    }

    @Operation(summary = "获取序列号")
    @GetMapping("/genSerialNO")
    @Parameter(name = "nodeType", description = "设备类型", required = true, example = "1")
    public CommonResult<String> genSerialNO(Integer nodeType) {
        return success(deviceManagerService.genSerialNO(nodeType));
    }

}
