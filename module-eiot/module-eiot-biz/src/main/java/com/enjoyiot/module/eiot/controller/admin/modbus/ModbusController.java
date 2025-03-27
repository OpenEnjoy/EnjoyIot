package com.enjoyiot.module.eiot.controller.admin.modbus;


import cn.hutool.core.util.StrUtil;
import cn.idev.excel.EasyExcel;
import com.enjoyiot.framework.common.exception.ServiceException;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.modbus.dto.ModbusInfo;
import com.enjoyiot.module.eiot.api.modbus.dto.ModbusThingModel;
import com.enjoyiot.module.eiot.controller.admin.modbus.vo.ModbusInfoVo;
import com.enjoyiot.module.eiot.controller.admin.modbus.vo.ModbusThingModelImportVo;
import com.enjoyiot.module.eiot.controller.admin.modbus.vo.ModbusThingModelVo;
import com.enjoyiot.module.eiot.service.modbus.ModbusInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


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
    public PageResult<ModbusInfo> getDevices(@Validated @RequestBody ModbusInfoVo data) {
        return modbusInfoService.selectPageList(data);
    }


    @Operation(summary = "新建ModbusInfo")
    @PreAuthorize("@ss.hasPermission('iot:modbus:add')")
    @PostMapping("/add")
    public ModbusInfo create(@Validated @RequestBody ModbusInfoVo data) {
        return modbusInfoService.createModbus(data);
    }

    @Operation(summary = "编辑ModbusInfo")
    @PreAuthorize("@ss.hasPermission('iot:modbus:edit')")
    @PostMapping("/edit")
    public boolean edit(@Validated @RequestBody ModbusInfoVo data) {
        return modbusInfoService.updateModbus(data);
    }

    @Operation(summary = "查看ModbusInfo详情")
    @PreAuthorize("@ss.hasPermission('iot:modbus:query')")
    @PostMapping("/getDetail")
    public ModbusInfo getDetail(@RequestParam("id") Long id) {
        return modbusInfoService.getModbus(id);
    }

    @Operation(summary = "删除ModbusInfo")
    @PreAuthorize("@ss.hasPermission('iot:modbus:remove')")
    @PostMapping("/deleteModbus")
    public boolean deleteProduct(@RequestParam("id") Long id) {
        return modbusInfoService.deleteModbus(id);
    }

    /**
     * 导入点位模型
     */
    @Operation(summary = "导入点位模型")
    @PreAuthorize("@ss.hasPermission('iot:modbus:add')")
    @PostMapping("/importData")
    public String importData(@RequestPart("file") MultipartFile file, @RequestParam("productKey") String productKey) {
        if(StrUtil.isBlank(productKey)){
           throw new ServiceException(400, "缺少productKey");
        }

        return modbusInfoService.importData(file,productKey);
    }

    @SneakyThrows
    @Operation(summary = "下载点位模版")
    @PreAuthorize("@ss.hasPermission('iot:modbus:query')")
    @PostMapping("/exportData")
    public void exportDeviceTemplate(HttpServletResponse response) {
        EasyExcel.write(response.getOutputStream(), ModbusThingModelImportVo.class);
    }


    @Operation(summary = "查看点位物模型")
    @PreAuthorize("@ss.hasPermission('iot:modbus:query')")
    @PostMapping("/getThingModelByProductKey")
    public ModbusThingModel getThingModelByProductKey(@RequestParam("productKey") String productKey) {
        return modbusInfoService.getThingModelByProductKey(productKey);
    }

    @Operation(summary = "保存点位物模型")
    @PreAuthorize("@ss.hasPermission('iot:modbus:edit')")
    @PostMapping("/thingModel/save")
    public boolean saveThingModel(@Validated @RequestBody ModbusThingModelVo modbusThingModelVo) {
        return modbusInfoService.saveThingModel(modbusThingModelVo);
    }


    @Operation(summary = "同步点位物模型到产品")
    @PreAuthorize("@ss.hasPermission('iot:modbus:edit')")
    @PostMapping("/syncToProduct")
    public boolean syncToProduct(@Validated @RequestBody ModbusThingModelVo modbusThingModelVo) {
        return modbusInfoService.syncToProduct(modbusThingModelVo);
    }



}
