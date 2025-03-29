package com.enjoyiot.module.eiot.service.modbus;

import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.modbus.dto.ModbusInfo;
import com.enjoyiot.module.eiot.api.modbus.dto.ModbusThingModel;
import com.enjoyiot.module.eiot.controller.admin.modbus.vo.ModbusInfoVo;
import com.enjoyiot.module.eiot.controller.admin.modbus.vo.ModbusThingModelImportVo;
import com.enjoyiot.module.eiot.controller.admin.modbus.vo.ModbusThingModelVo;

import javax.validation.Valid;
import java.util.List;

public interface ModbusInfoService {

    PageResult<ModbusInfo> selectPageList(@Valid ModbusInfoVo data);

    ModbusInfo createModbus(@Valid ModbusInfoVo data);

    boolean updateModbus(@Valid ModbusInfoVo data);

    ModbusInfo getModbus(Long data);

    boolean deleteModbus(Long data);

    ModbusThingModel getThingModelByProductKey(String data);

    boolean saveThingModel(@Valid ModbusThingModelVo data);

    boolean syncToProduct(@Valid ModbusThingModelVo data);

    String importData(List<ModbusThingModelImportVo> objects, String productKey);
}
