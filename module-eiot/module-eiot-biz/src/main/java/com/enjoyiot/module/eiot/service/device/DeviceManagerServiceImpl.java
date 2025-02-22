
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
package com.enjoyiot.module.eiot.service.device;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.enjoyiot.eiot.IDevicePropertyData;
import com.enjoyiot.eiot.IThingModelMessageData;
import com.enjoyiot.eiot.common.constant.Constants;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.common.utils.UniqueIdUtil;
import com.enjoyiot.eiot.message.core.MqProducer;
import com.enjoyiot.framework.common.exception.ServiceException;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.util.object.BeanUtils;
import com.enjoyiot.framework.common.util.validation.ValidationUtils;
import com.enjoyiot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.enjoyiot.framework.web.core.util.WebFrameworkUtils;
import com.enjoyiot.module.eiot.api.device.dto.*;
import com.enjoyiot.module.eiot.api.thingmodel.dto.ThingModel;
import com.enjoyiot.module.eiot.controller.admin.device.vo.*;
import com.enjoyiot.module.eiot.controller.admin.device.vo.deviceconfig.DeviceConfigVo;
import com.enjoyiot.module.eiot.controller.admin.device.vo.devicegroup.DeviceAddGroupBo;
import com.enjoyiot.module.eiot.controller.admin.device.vo.devicegroup.DeviceGroupBo;
import com.enjoyiot.module.eiot.controller.admin.device.vo.devicegroup.DeviceGroupImportVo;
import com.enjoyiot.module.eiot.controller.admin.device.vo.devicegroup.GroupImportRespVO;
import com.enjoyiot.module.eiot.controller.admin.product.vo.PropertyVO;
import com.enjoyiot.module.eiot.convert.DeviceGroupConvert;
import com.enjoyiot.module.eiot.dal.dataobject.DeviceGroupDO;
import com.enjoyiot.module.eiot.dal.dataobject.GroupDO;
import com.enjoyiot.module.eiot.dal.mysql.EiotIotDeviceGroupMapper;
import com.enjoyiot.module.eiot.dal.mysql.EiotIotGroupMapper;
import com.enjoyiot.module.eiot.service.product.ThingModelService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * @Author: EnjoyIot
 * @Date: 2025/1/8 18:20
 * @Version: V1.0
 * @Description: 设备管理实现类
 */
@Service
public class DeviceManagerServiceImpl implements DeviceManagerService {

    @Resource
    private DeviceInfoService deviceInfoService;

    @Resource
    private ThingModelService thingModelService;

    @Lazy
    @Resource
    private IThingModelMessageData thingModelMessageData;
    @Lazy
    @Resource
    private IDevicePropertyData devicePropertyData;

    @Resource
    private EiotIotGroupMapper iotGroupMapper;

    @Resource
    private EiotIotDeviceGroupMapper deviceGroupMapper;

    @Resource
    private MqProducer<ThingModelMessage> producer;

    @Override
    public PageResult<ThingModelMessage> logs(DeviceLogPageReqVo req) {
        return thingModelMessageData.findByTypeAndIdentifier(req.getDeviceId(), req.getType(), req.getIdentifier(), req.getPageNo(), req.getPageSize());

    }

    @Override
    public List<DeviceProperty> getPropertyHistory(Long deviceId, String name, long start, long end, Integer pageNo, Integer pageSize) {
        return devicePropertyData.findDevicePropertyHistory(deviceId, name, start, end, pageSize);
    }

    @Override
    public Boolean addTag(DeviceTagAddBo bo) {
        DeviceInfo.Tag tag = BeanUtil.toBean(bo, DeviceInfo.Tag.class);
        return true;
    }

    @Override
    public Boolean simulateSend(ThingModelMessage message) {
        DeviceInfo device = deviceInfoService.getDeviceInfo(message.getDeviceId());

        message.setMid(UniqueIdUtil.newRequestId());
        message.setOccurred(System.currentTimeMillis());
        message.setTime(System.currentTimeMillis());
        message.setToClient(Boolean.FALSE);
        producer.publish(Constants.THING_MODEL_MESSAGE_TOPIC, message);
        return true;
    }

    @Override
    public PageResult<DeviceGroup> selectGroupPageList(DeviceGroupPageReqVO pageRequest) {
        return DeviceGroupConvert.INSTANCE.convertPage(iotGroupMapper.selectPage(pageRequest));
    }

    @Override
    public Boolean addGroup(DeviceGroupBo group) {
        if (ObjectUtil.isNotNull(group.getId())) {
            return updateGroup(group);
        } else {
            DeviceGroup g = BeanUtil.toBean(group, DeviceGroup.class);
            g.setUid(WebFrameworkUtils.getLoginUserId());
            return iotGroupMapper.insert(DeviceGroupConvert.INSTANCE.convertDo(g)) > 0;
        }
    }

    @Override
    public Boolean updateGroup(DeviceGroupBo bo) {
        DeviceGroup g = BeanUtil.toBean(bo, DeviceGroup.class);
        LambdaUpdateWrapper<GroupDO> up = new LambdaUpdateWrapper<>(GroupDO.class);
        up.eq(GroupDO::getId, g.getId());
        up.set(GroupDO::getName, g.getName()).set(
                GroupDO::getRemark, g.getRemark());
        iotGroupMapper.update(null, up);
        return true;
    }

    @Override
    public Boolean deleteGroup(Long id) {
        //移除设备信息中的分组
        clearGroup(id);
        //删除分组
        iotGroupMapper.deleteById(id);
        return true;
    }

    @Override
    public Boolean clearGroup(Long id) {
        GroupDO groupDO = iotGroupMapper.selectById(id);
        if (groupDO == null) {
            throw new ServiceException(400, "分组不存在");
        }

        //移除设备信息中的分组
        LambdaQueryWrapperX<DeviceGroupDO> eq = new LambdaQueryWrapperX<DeviceGroupDO>()
                .eq(DeviceGroupDO::getGroupId, id);
        return deviceGroupMapper.delete(eq) > 0;
    }

    @Override
    public DeviceConfigVo getConfig(Long deviceId) {
        return null;
    }

    @Override
    public Boolean saveConfig(DeviceConfig data) {
        return null;
    }

    @Override
    public Boolean removeDevicesInGroup(Long groupId, List<Long> deviceIds) {
        return deviceGroupMapper.removeDevicesInGroup(groupId, deviceIds) == deviceIds.size();
    }

    @Override
    public Boolean addDevice2Group(DeviceAddGroupBo bo) {
        Long groupId = bo.getGroupId();
        List<Long> deviceIds = bo.getDeviceIds();
        GroupDO groupDO = iotGroupMapper.selectById(groupId);

        if (groupDO == null) {
            throw new ServiceException(400, "分组不存在");
        }

        for (Long deviceId : deviceIds) {
            DeviceInfo deviceInfo = deviceInfoService.getDeviceInfo(deviceId);
            if (deviceInfo == null) {
                continue;
            }

            //添加设备到组
            LambdaQueryWrapperX<DeviceGroupDO> q = new LambdaQueryWrapperX<DeviceGroupDO>()
                    .eq(DeviceGroupDO::getGroupId, groupId)
                    .eq(DeviceGroupDO::getDeviceId, deviceId);
            Long count = deviceGroupMapper.selectCount(q);
            if (count > 0) {
                return Boolean.TRUE;
            }

            return deviceGroupMapper.insert(
                    DeviceGroupDO.builder().groupId(groupId).deviceId(deviceId).build()) > 0;
        }
        return true;
    }

    @Override
    public DeviceInfoWithPropertyVO getDeviceInfoWithProperty(Long deviceId) {
        DeviceInfo deviceInfo = deviceInfoService.getDeviceInfo(deviceId);
        if (ObjectUtil.isNull(deviceInfo)) {
            return null;
        }
        Map<String, DevicePropertyCache> propertiesFromCache = deviceInfoService.getPropertiesFromCache(deviceId);
        ThingModel thingModel = thingModelService.getThingModelByProductKeyFromCache(deviceInfo.getProductKey());
        DeviceInfoWithPropertyVO resVo = new DeviceInfoWithPropertyVO();
        resVo.setDeviceId(deviceInfo.getId());
        resVo.setProductKey(deviceInfo.getProductKey());
        resVo.setDn(deviceInfo.getDn());
        resVo.setOnline(deviceInfo.isOnline());
        resVo.setOnlineTime(deviceInfo.getOnlineTime());
        resVo.setOfflineTime(deviceInfo.getOfflineTime());
        Map<String, PropertyVO> identifier2property = new HashMap<>();

        if (ObjectUtil.isNotNull(thingModel)) {
            for (ThingModel.Property property : thingModel.getModel().getProperties()) {
                PropertyVO openPropertyVo = new PropertyVO(property.getIdentifier(), property.getDataType(), property.getName(), property.getAccessMode(), property.getDescription(), property.getUnit());
                if (ObjectUtil.isNotEmpty(propertiesFromCache)) {
                    DevicePropertyCache propertyCache = propertiesFromCache.get(openPropertyVo.getIdentifier());
                    if (ObjectUtil.isNotNull(propertyCache)) {
                        openPropertyVo.setTime(String.valueOf(propertyCache.getOccurred()));
                        openPropertyVo.setValue(String.valueOf(propertyCache.getValue()));
                    }
                }

                identifier2property.put(property.getIdentifier(), openPropertyVo);
            }
            resVo.setIdentifier2property(identifier2property);
        }
        return resVo;
    }

    @Override
    public String genSerialNO(Integer nodeType) {
        String nodeList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Integer ind = nodeType % nodeList.length();
        return nodeList.charAt(ind) + IdUtil.fastSimpleUUID().toUpperCase();
    }

    @Override
    public GroupImportRespVO importGroup(List<DeviceGroupImportVo> list, Boolean updateSupport) {
        // 1.1 参数校验
        if (CollUtil.isEmpty(list)) {
            new ServiceException(400, "导入数据不许为空");
        }
        // 2. 遍历，逐个创建 or 更新
        GroupImportRespVO respVO = GroupImportRespVO.builder().createGroupnames(new ArrayList<>())
                .updateGroupnames(new ArrayList<>()).failureGroupnames(new LinkedHashMap<>()).build();
        list.forEach(groupInfo -> {
            DeviceGroupBo deviceGroupBo = BeanUtils.toBean(groupInfo, DeviceGroupBo.class);
            try {
                ValidationUtils.validate(deviceGroupBo);
            } catch (ConstraintViolationException ex) {
                respVO.getFailureGroupnames().put(groupInfo.getName(), ex.getMessage());
                return;
            }
            try {
                validateGroupForCreateOrUpdate(null, groupInfo.getName(), groupInfo.getGroupOrder());
            } catch (ServiceException ex) {
                respVO.getFailureGroupnames().put(groupInfo.getName(), ex.getMessage());
                return;
            }
            // 2.2.1 判断如果不存在，在进行插入
            // 2.2.2 如果存在，判断是否允许更新
            // 暂时用直接插入
            if (addGroup(deviceGroupBo)) {
                respVO.getCreateGroupnames().add(groupInfo.getName());
            }
        });
        return respVO;
    }

    private void validateGroupForCreateOrUpdate(Long id, String name, Integer groupOrder) {
    }


}
