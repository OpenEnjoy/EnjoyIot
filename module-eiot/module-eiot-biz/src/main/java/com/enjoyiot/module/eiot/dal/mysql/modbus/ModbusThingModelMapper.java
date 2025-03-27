package com.enjoyiot.module.eiot.dal.mysql.modbus;

import com.enjoyiot.framework.mybatis.core.mapper.BaseMapperX;
import com.enjoyiot.module.eiot.dal.dataobject.modbus.ModbusThingModelDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ModbusThingModelMapper extends BaseMapperX<ModbusThingModelDO> {
    default ModbusThingModelDO findByProductKey(String productKey) {
        return selectOne(ModbusThingModelDO::getProductKey, productKey);
    }
}
