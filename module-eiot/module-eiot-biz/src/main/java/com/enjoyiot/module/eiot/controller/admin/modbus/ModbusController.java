package com.enjoyiot.module.eiot.controller.admin.modbus;


import cn.hutool.core.util.StrUtil;
import cn.idev.excel.EasyExcel;
import com.enjoyiot.framework.apilog.core.annotation.ApiAccessLog;
import com.enjoyiot.framework.common.exception.ServiceException;
import com.enjoyiot.framework.common.pojo.CommonResult;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.util.object.BeanUtils;
import com.enjoyiot.framework.excel.core.util.ExcelUtils;
import com.enjoyiot.module.eiot.api.modbus.dto.ModbusInfo;
import com.enjoyiot.module.eiot.api.modbus.dto.ModbusThingModel;
import com.enjoyiot.module.eiot.controller.admin.modbus.vo.ModbusInfoRespVo;
import com.enjoyiot.module.eiot.controller.admin.modbus.vo.ModbusInfoVo;
import com.enjoyiot.module.eiot.controller.admin.modbus.vo.ModbusThingModelImportVo;
import com.enjoyiot.module.eiot.controller.admin.modbus.vo.ModbusThingModelVo;
import com.enjoyiot.module.eiot.service.modbus.ModbusInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.enjoyiot.framework.apilog.core.enums.OperateTypeEnum.IMPORT;


@Tag(name = "管理后台 - Modbus管理")
@Slf4j
@RestController
@RequestMapping("/eiot/modbus")
@Validated
public class ModbusController {

    @Resource
    private ModbusInfoService modbusInfoService;


    @Operation(summary = "ModbusInfo模版列表")
    @PreAuthorize("@ss.hasPermission('iot:modbus:list')")
    @PostMapping("/list")
    public CommonResult<PageResult<ModbusInfoRespVo>> getDevices(@RequestBody ModbusInfoVo data) {
        PageResult<ModbusInfo> result = modbusInfoService.selectPageList(data);
        return CommonResult.success(BeanUtils.toBean(result, ModbusInfoRespVo.class));
    }


    @Operation(summary = "新建ModbusInfo")
    @PreAuthorize("@ss.hasPermission('iot:modbus:add')")
    @PostMapping("/add")
    public CommonResult<ModbusInfoRespVo> create(@RequestBody ModbusInfoVo data) {
        ModbusInfo modbus = modbusInfoService.createModbus(data);
        return CommonResult.success(BeanUtils.toBean(modbus, ModbusInfoRespVo.class));
    }

    @Operation(summary = "编辑ModbusInfo")
    @PreAuthorize("@ss.hasPermission('iot:modbus:edit')")
    @PostMapping("/edit")
    public CommonResult<Boolean> edit(@RequestBody ModbusInfoVo data) {
        modbusInfoService.updateModbus(data);
        return CommonResult.success(true);
    }

    @Operation(summary = "查看ModbusInfo详情")
    @PreAuthorize("@ss.hasPermission('iot:modbus:query')")
    @PostMapping("/getDetail")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<ModbusInfoRespVo> getDetail(@RequestParam("id") Long id) {
        ModbusInfo modbus = modbusInfoService.getModbus(id);
        return CommonResult.success(BeanUtils.toBean(modbus, ModbusInfoRespVo.class));
    }

    @Operation(summary = "删除ModbusInfo")
    @PreAuthorize("@ss.hasPermission('iot:modbus:remove')")
    @PostMapping("/deleteModbus")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<Boolean> deleteProduct(@RequestParam("id") Long id) {
        modbusInfoService.deleteModbus(id);
        return CommonResult.success(true);
    }

    /**
     * 导入点位模型
     */
    @SneakyThrows
    @Operation(summary = "导入点位模型")
    @PreAuthorize("@ss.hasPermission('iot:modbus:add')")
    @ApiAccessLog(operateType = IMPORT)
    @PostMapping("/importData")
    @Parameter(name = "productKey", description = "productKey", required = true, example = "1024")
    public CommonResult<String> importData(@RequestPart("file") MultipartFile file, String productKey) {
        if(StrUtil.isBlank(productKey)){
           throw new ServiceException(400, "缺少productKey");
        }
        List<ModbusThingModelImportVo> objects = ExcelUtils.read(file, ModbusThingModelImportVo.class);
        return CommonResult.success(modbusInfoService.importData(objects,productKey));
    }

    @SneakyThrows
    @Operation(summary = "下载点位模版")
    @PreAuthorize("@ss.hasPermission('iot:modbus:query')")
    @GetMapping("/exportData")
    public void exportDeviceTemplate(HttpServletResponse response) {
        ExcelUtils.write(response, "点位模版.xls","点位", ModbusThingModelImportVo.class ,null);
    }


    @Operation(summary = "查看点位物模型")
    @PreAuthorize("@ss.hasPermission('iot:modbus:query')")
    @PostMapping("/getThingModelByProductKey")
    @Parameter(name = "pk", description = "productKey", required = true, example = "1024")
    public CommonResult<ModbusThingModel> getThingModelByProductKey(@RequestParam("pk") String productKey) {
        return CommonResult.success(modbusInfoService.getThingModelByProductKey(productKey));
    }

    @Operation(summary = "保存点位物模型")
    @PreAuthorize("@ss.hasPermission('iot:modbus:edit')")
    @PostMapping("/thingModel/save")
    public CommonResult<Boolean> saveThingModel(@RequestBody ModbusThingModelVo modbusThingModelVo) {
        modbusInfoService.saveThingModel(modbusThingModelVo);
        return CommonResult.success(true);
    }


    @Operation(summary = "同步点位物模型到产品")
    @PreAuthorize("@ss.hasPermission('iot:modbus:edit')")
    @PostMapping("/syncToProduct")
    public CommonResult<Boolean> syncToProduct(@RequestBody ModbusThingModelVo modbusThingModelVo) {
        modbusInfoService.syncToProduct(modbusThingModelVo);
        return CommonResult.success(true);
    }



}
