package com.enjoyiot.module.eiot.dal.mysql.modbus;

import com.enjoyiot.framework.mybatis.core.mapper.BaseMapperX;
import com.enjoyiot.module.eiot.dal.dataobject.modbus.ModbusInfoDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ModbusInfoMapper extends BaseMapperX<ModbusInfoDO> {

    default ModbusInfoDO findByProductKey(String productKey) {
        return selectOne(ModbusInfoDO::getProductKey, productKey);
    }
}
