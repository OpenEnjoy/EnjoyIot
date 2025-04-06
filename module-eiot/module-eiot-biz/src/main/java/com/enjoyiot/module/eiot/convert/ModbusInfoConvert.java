package com.enjoyiot.module.eiot.convert;

import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.modbus.dto.ModbusInfo;
import com.enjoyiot.module.eiot.dal.dataobject.modbus.ModbusInfoDO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface ModbusInfoConvert {
    ModbusInfoConvert INSTANCE = Mappers.getMapper(ModbusInfoConvert.class);

    ModbusInfo convert(ModbusInfoDO modbusInfoDO);

    PageResult<ModbusInfo> convertPage(PageResult<ModbusInfoDO> selectPage);
}
