package com.enjoyiot.module.eiot.api.modbus;

import com.enjoyiot.framework.tenant.core.util.TenantUtils;
import com.enjoyiot.module.eiot.api.modbus.dto.ModbusInfo;
import com.enjoyiot.module.eiot.convert.ModbusInfoConvert;
import com.enjoyiot.module.eiot.dal.dataobject.modbus.ModbusInfoDO;
import com.enjoyiot.module.eiot.dal.mysql.modbus.ModbusInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ModbusInfoApiImpl implements ModbusInfoApi {
    @Resource
    private ModbusInfoMapper modbusInfoMapper;
    @Override
    public ModbusInfo findByProductKey(String productKey) {
        ModbusInfoDO t = TenantUtils.executeIgnoreResult(() -> modbusInfoMapper.findByProductKey(productKey));
        return ModbusInfoConvert.INSTANCE.convert(t);
    }
}
