
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
package com.enjoyiot.module.eiot.service.product;

import cn.hutool.core.util.ObjectUtil;
import com.enjoyiot.eiot.IDbStructureData;
import com.enjoyiot.framework.common.exception.ServiceException;
import com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.framework.common.util.object.BeanUtils;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.enums.ErrorCodeConstants;
import com.enjoyiot.module.eiot.api.thingmodel.dto.ThingModel;
import com.enjoyiot.module.eiot.controller.admin.product.vo.IotThingModelSaveReqVO;
import com.enjoyiot.module.eiot.controller.admin.thingmodel.vo.ThingModelSaveReqVO;
import com.enjoyiot.module.eiot.convert.ThingModelConvert;
import com.enjoyiot.module.eiot.dal.dataobject.thingmodel.ThingModelDO;
import com.enjoyiot.module.eiot.dal.mysql.thingmodel.ThingModelMapper;
import com.enjoyiot.module.eiot.dal.redis.RedisKeyConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 产品物模型 Service 实现类
 *
 * @author EnjoyIot
 */
@Service
@Validated
public class ThingModelServiceImpl implements ThingModelService {

    @Resource
    private ThingModelMapper thingModelMapper;

    @Resource
    private IDbStructureData dbStructureData;

    @Resource
    private DeviceApi deviceApi;


    @Override
    @CacheEvict(cacheNames = RedisKeyConstants.THING_MODEL, key = "#updateReqVO.productKey")
    public void saveThingModel(ThingModelSaveReqVO updateReqVO) {
        String productKey = updateReqVO.getProductKey();
        ThingModel oldData = getThingModelByProductKey(productKey);

        ThingModel thingModel = new ThingModel(1L, productKey, JsonUtils.parseObject(updateReqVO.getModel(), ThingModel.Model.class));

        //验证物模型合法性
        List<ThingModel.Property> properties = thingModel.getModel().getProperties();
        for (ThingModel.Property property : properties) {
            //属性标识符合法性校验
            String identifier = property.getIdentifier();
            if (StringUtils.isBlank(identifier)) {
                throw new ServiceException(400, "属性标识符不能为空");
            }
            if (!identifier.matches("^[a-zA-Z].*")) {
                throw new ServiceException(400, "属性标识符【" + identifier + "】不合法");
            }
        }

        if (ObjectUtil.isNull(oldData)) {
            //定义时序数据库物模型数据结构
            dbStructureData.defineThingModel(thingModel);
            // 校验存在
            IotThingModelSaveReqVO updateObj = BeanUtils.toBean(updateReqVO, IotThingModelSaveReqVO.class);
            // 插入
            thingModelMapper.insert(BeanUtils.toBean(updateObj, ThingModelDO.class));
        } else {
            thingModel.setId(oldData.getId());
            //更新时序数据库物模型数据结构
            dbStructureData.updateThingModel(thingModel);
            // 校验存在
            IotThingModelSaveReqVO updateObj = BeanUtils.toBean(updateReqVO, IotThingModelSaveReqVO.class);
            updateObj.setId(oldData.getId());
            // 校验存在
            validateThingModelExists(updateObj.getId());
            // 更新
            thingModelMapper.updateById(BeanUtils.toBean(updateObj, ThingModelDO.class));
            // 清除该产品下设备的属性缓存
            deviceApi.clearPropertiesCache(productKey);
        }

    }

    @Override
    public ThingModel getThingModel(Long id) {
        ThingModelDO thingModelDO = thingModelMapper.selectById(id);
        ThingModel convert = ThingModelConvert.INSTANCE.convert(thingModelDO);

        if (thingModelDO != null && convert != null) {
            convert.setModel(JsonUtils.parseObject(thingModelDO.getModel(), ThingModel.Model.class));
        }
        return convert;
    }

    @Override
    public ThingModel getThingModelByProductKey(String productKey) {
        ThingModelDO thingModelDO = thingModelMapper.selectOne(ThingModelDO::getProductKey, productKey);
        ThingModel convert = ThingModelConvert.INSTANCE.convert(thingModelDO);

        if (thingModelDO != null && convert != null) {
            convert.setModel(JsonUtils.parseObject(thingModelDO.getModel(), ThingModel.Model.class));
        }
        return convert;
    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.THING_MODEL, key = "#productKey",
            unless = "#result == null")
    public ThingModel getThingModelByProductKeyFromCache(String productKey) {
        return getThingModelByProductKey(productKey);
    }

    private void validateThingModelExists(Long id) {
        if (thingModelMapper.selectById(id) == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.NOT_EXISTS);
        }
    }
}
