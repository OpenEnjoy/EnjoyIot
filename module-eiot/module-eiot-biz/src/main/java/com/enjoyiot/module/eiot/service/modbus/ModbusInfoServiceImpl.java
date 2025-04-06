package com.enjoyiot.module.eiot.service.modbus;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.idev.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.enjoyiot.framework.common.exception.ServiceException;
import com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.framework.excel.core.util.ExcelUtils;
import com.enjoyiot.module.eiot.api.enums.ErrorCodeConstants;
import com.enjoyiot.module.eiot.api.modbus.ModbusInfoApi;
import com.enjoyiot.module.eiot.api.modbus.ModbusThingModelApi;
import com.enjoyiot.module.eiot.api.modbus.dto.ModbusInfo;
import com.enjoyiot.module.eiot.api.modbus.dto.ModbusThingModel;
import com.enjoyiot.module.eiot.api.product.ProductApi;
import com.enjoyiot.module.eiot.api.product.dto.Product;
import com.enjoyiot.module.eiot.api.thingmodel.dto.ThingModel;
import com.enjoyiot.module.eiot.controller.admin.modbus.vo.ModbusInfoVo;
import com.enjoyiot.module.eiot.controller.admin.modbus.vo.ModbusThingModelImportVo;
import com.enjoyiot.module.eiot.controller.admin.modbus.vo.ModbusThingModelVo;
import com.enjoyiot.module.eiot.controller.admin.product.vo.ProductSaveReqVO;
import com.enjoyiot.module.eiot.controller.admin.thingmodel.vo.ThingModelSaveReqVO;
import com.enjoyiot.module.eiot.convert.ModbusInfoConvert;
import com.enjoyiot.module.eiot.dal.dataobject.modbus.ModbusInfoDO;
import com.enjoyiot.module.eiot.dal.mysql.modbus.ModbusInfoMapper;
import com.enjoyiot.module.eiot.service.product.ProductService;
import com.enjoyiot.module.eiot.service.product.ThingModelService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ModbusInfoServiceImpl implements ModbusInfoService {

    @Resource
    private ModbusInfoApi modbusInfoApi;

    @Resource
    private ModbusThingModelApi modbusThingModelApi;

    @Resource
    private ProductService productService;

    @Resource
    private ThingModelService thingModelService;

    @Resource
    private ProductApi productApi;

    @Resource
    private ModbusInfoMapper modbusInfoMapper;


    @Override
    public PageResult<ModbusInfo> selectPageList(ModbusInfoVo data) {
        LambdaQueryWrapper<ModbusInfoDO> qw = new LambdaQueryWrapper<ModbusInfoDO>()
                .eq(Objects.nonNull(data.getId()), ModbusInfoDO::getId, data.getId())
                .eq(StrUtil.isNotEmpty(data.getProductKey()), ModbusInfoDO::getProductKey, data.getProductKey())
                .like(StrUtil.isNotEmpty(data.getName()), ModbusInfoDO::getName, data.getName());
        PageResult<ModbusInfoDO> pageResult = modbusInfoMapper.selectPage(data, qw);
        return ModbusInfoConvert.INSTANCE.convertPage(pageResult);
    }

    @Override
    @Transactional
    public ModbusInfo createModbus(ModbusInfoVo data) {
        ModbusInfoDO bean = BeanUtil.toBean(data, ModbusInfoDO.class);
        //模板名称不可重复
        ModbusInfoDO old = modbusInfoMapper.selectOne(ModbusInfoDO::getName, bean.getName());
        if (old != null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.TEMPLATE_NAME_ALREADY);
        }

        //productKey在产品中不可重复
        Product oldProduct = productApi.getProduct(bean.getProductKey());
        if (oldProduct != null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.PRODUCT_KEY_REPEAT);
        }

        //productKey在物模型中不可重复
        old = modbusInfoMapper.findByProductKey(bean.getProductKey());
        if (old != null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.PRODUCT_KEY_REPEAT);
        }

        modbusInfoMapper.insert(bean);

        //生成对应的product
        ProductSaveReqVO productVo = new ProductSaveReqVO();
        productVo.setProductKey(data.getProductKey());
        productVo.setName(data.getName());
        /**
         * [{value:0,label:'网关设备',},{value:1,label:'网关子设备',},{value:2,label:'直连设备'},]
         */
        productVo.setNodeType(1);
        productVo.setCategoryId(0L);
        productVo.setStatus(1);
        productVo.setTransparent(true);
        productVo.setKeepAliveTime(0L);
        productVo.setProtocolCode("modbus-tcp");
        productVo.setMcuCode("N");
        productVo.setLocateType(0);
        productService.createProduct(productVo);

        return ModbusInfoConvert.INSTANCE.convert(bean);
    }

    @Override
    public boolean updateModbus(ModbusInfoVo data) {
        ModbusInfoDO bean = BeanUtil.toBean(data, ModbusInfoDO.class);
        //模板名称不可重复
        ModbusInfoDO old = modbusInfoMapper.selectOne(ModbusInfoDO::getName, bean.getName());
        if (old != null && !Objects.equals(old.getId(), data.getId())) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.TEMPLATE_NAME_ALREADY);
        }

        return modbusInfoMapper.updateById(bean) > 0;
    }

    @Override
    public ModbusInfo getModbus(Long data) {
        return ModbusInfoConvert.INSTANCE.convert(modbusInfoMapper.selectById(data));
    }

    @Override
    public boolean deleteModbus(Long data) {
        ModbusInfoDO info = modbusInfoMapper.selectById(data);

        Product product = productApi.getProduct(info.getProductKey());
        if (product != null) {
            //存在关联产品
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.RELATED_PRODUCTS_EXIST);
        }
        return modbusInfoMapper.deleteById(data) > 0;
    }

    @Override
    public ModbusThingModel getThingModelByProductKey(String data) {
        return modbusThingModelApi.findByProductKey(data);
    }

    @Override
    @Transactional
    public boolean saveThingModel(ModbusThingModelVo data) {
        String productKey = data.getProductKey();
        ModbusThingModel thingModel = new ModbusThingModel();
        thingModel.setProductKey(productKey);
        thingModel.setModel(JsonUtils.parseObject(data.getModel(), ModbusThingModel.Model.class));
        return saveThingModel(productKey, thingModel);
    }

    public boolean saveThingModel(String productKey, ModbusThingModel thingModel) {
        ModbusThingModel oldTm = modbusThingModelApi.findByProductKey(productKey);
        //验证物模型合法性
        List<ModbusThingModel.Property> properties = thingModel.getModel().getProperties();
        for (ModbusThingModel.Property property : properties) {
            //属性标识符合法性校验
            String identifier = property.getIdentifier();
            if (StringUtils.isBlank(identifier)) {
                throw new ServiceException(400, "属性标识符不能为空");
            }
            if (!identifier.matches("^[a-zA-Z].*")) {
                throw new ServiceException(400, "属性标识符【" + identifier + "】不合法");
            }
        }
        if (oldTm != null) {
            thingModel.setId(oldTm.getId());
        }
        modbusThingModelApi.save(thingModel);
        return true;
    }

    @Override
    public boolean syncToProduct(ModbusThingModelVo data) {
        String productKey = data.getProductKey();

        Product product = productApi.getProduct(productKey);
        if (product == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.PRODUCT_NOT_EXISTS);
        }

        ModbusThingModel modbusThingModel = modbusThingModelApi.findByProductKey(productKey);

        ThingModel.Model model = new ThingModel.Model();

        //将Property私有属性封装到proData中
        List<ThingModel.Property> properties = new ArrayList<>();

        if (CollectionUtil.isNotEmpty(modbusThingModel.getModel().getProperties())) {
            properties = modbusThingModel.getModel().getProperties().stream().map(p -> {
                ThingModel.Property property = BeanUtil.copyProperties(p, ThingModel.Property.class);
                property.setProData(JSONUtil.toJsonStr(BeanUtil.copyProperties(p, ModbusThingModel.ProData.class)));
                return property;
            }).collect(Collectors.toList());
        }
        model.setProperties(properties);

        //services events 数据封装
        ThingModelSaveReqVO tbspModel = new ThingModelSaveReqVO();
        tbspModel.setProductKey(productKey);
        tbspModel.setModel(JSONUtil.toJsonStr(model));

        thingModelService.saveThingModel(tbspModel);
        return false;
    }


    @Override
    public String importData(List<ModbusThingModelImportVo> objects, String productKey) {
        if (CollectionUtil.isEmpty(objects)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.DATA_NOT_EXIST);
        }
        List<ModbusThingModel.Property> properties = new ArrayList<>();
        for (ModbusThingModelImportVo vo : objects) {
            ModbusThingModel.Property convert = BeanUtil.toBean(vo, ModbusThingModel.Property.class, CopyOptions.create().setIgnoreProperties("dataType"));
            convert.setDataType(JSONUtil.toBean(vo.getDataType(), ModbusThingModel.DataType.class));
            properties.add(convert);
        }

        ModbusThingModel.Model model = new ModbusThingModel.Model();
        model.setProperties(properties);

        ModbusThingModel modbusThingModel = new ModbusThingModel();
        modbusThingModel.setModel(model);
        modbusThingModel.setProductKey(productKey);

        return this.saveThingModel(productKey, modbusThingModel) ? "导入成功" : "导入失败";
    }
}
