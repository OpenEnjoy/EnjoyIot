package com.enjoyiot.module.eiot.api.modbus;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.framework.tenant.core.util.TenantUtils;
import com.enjoyiot.module.eiot.api.modbus.dto.ModbusThingModel;
import com.enjoyiot.module.eiot.dal.dataobject.modbus.ModbusThingModelDO;
import com.enjoyiot.module.eiot.dal.mysql.modbus.ModbusThingModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ModbusThingModelApiImpl implements ModbusThingModelApi {

    @Resource
    private ModbusThingModelMapper modbusThingModelMapper;
    @Override
    public ModbusThingModel findByProductKey(String productKey) {
        ModbusThingModelDO model = TenantUtils.executeIgnoreResult(() -> modbusThingModelMapper.findByProductKey(productKey));
        if (model != null) {
            ModbusThingModel thingModel = BeanUtil.toBean(model, ModbusThingModel.class, CopyOptions.create().setIgnoreProperties("model"));
            thingModel.setModel(JsonUtils.parseObject(model.getModel(), ModbusThingModel.Model.class));
            return thingModel;
        }
        return null;
    }

    @Override
    public ModbusThingModel save(ModbusThingModel thingModel) {
        ModbusThingModelDO to = BeanUtil.copyProperties(thingModel, ModbusThingModelDO.class, "model");
        to.setModel(JsonUtils.toJsonString(thingModel.getModel()));
        modbusThingModelMapper.insertOrUpdate(to);
        thingModel.setId(to.getId());
        return thingModel;
    }
}
