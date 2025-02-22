
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
package com.enjoyiot.module.eiot.dal.mysql.sip;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.mybatis.core.mapper.BaseMapperX;
import com.enjoyiot.framework.mybatis.core.query.MPJLambdaWrapperX;
import com.enjoyiot.module.eiot.controller.admin.sip.vo.SipRelation;
import com.enjoyiot.module.eiot.controller.admin.sip.vo.SipRelationPageReqVO;
import com.enjoyiot.module.eiot.dal.dataobject.DeviceChannelDO;
import com.enjoyiot.module.eiot.dal.dataobject.sip.SipRelationDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 监控设备关联 Mapper
 *
 * @author EnjoyIot
 */
@Mapper
public interface SipRelationMapper extends BaseMapperX<SipRelationDO> {

    default List<SipRelation> selectSipRelationList(SipRelation sipRelation) {
        MPJLambdaWrapperX<SipRelationDO> query = new MPJLambdaWrapperX<SipRelationDO>()
                .selectAll(SipRelationDO.class)
                .selectAs(SipRelation::getStatus, DeviceChannelDO::getStatus)
                .selectAs(SipRelation::getDeviceSipId, DeviceChannelDO::getDeviceSipId)
                .selectAs(SipRelation::getChannelName, DeviceChannelDO::getChannelName)
                .selectAs(SipRelation::getModel, DeviceChannelDO::getModel)
                .eqIfPresent(SipRelationDO::getChannelId, sipRelation.getChannelId())
                .eqIfPresent(SipRelationDO::getReDeviceId, sipRelation.getDeviceSipId())
                .eqIfPresent(SipRelationDO::getReSceneModelId, sipRelation.getReSceneModelId());
        query.leftJoin(DeviceChannelDO.class, DeviceChannelDO::getChannelSipId, SipRelationDO::getChannelId);
        return selectJoinList(SipRelation.class, query);


    }

    default PageResult<SipRelation> selectPage(SipRelationPageReqVO reqVO) {
        MPJLambdaWrapperX<SipRelationDO> query = new MPJLambdaWrapperX<SipRelationDO>()
                .selectAll(SipRelationDO.class)
                .selectAs(SipRelation::getStatus, DeviceChannelDO::getStatus)
                .selectAs(SipRelation::getDeviceSipId, DeviceChannelDO::getDeviceSipId)
                .selectAs(SipRelation::getChannelName, DeviceChannelDO::getChannelName)
                .selectAs(SipRelation::getModel, DeviceChannelDO::getModel)
                .eqIfPresent(SipRelationDO::getChannelId, reqVO.getChannelId())
                .eqIfPresent(SipRelationDO::getReDeviceId, reqVO.getReDeviceId())
                .eqIfPresent(SipRelationDO::getReSceneModelId, reqVO.getReSceneModelId());
        query.leftJoin(DeviceChannelDO.class, DeviceChannelDO::getChannelSipId, SipRelationDO::getChannelId);
        return selectJoinPage(reqVO,SipRelation.class, query);
    }

    default SipRelationDO selectByChannelId(String channelId){
        return selectOne(SipRelationDO::getChannelId, channelId);
    }

    default int updateByChannelId(SipRelation sipRelation){
        LambdaUpdateWrapper<SipRelationDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SipRelationDO::getReDeviceId, sipRelation.getReDeviceId())
                .set(SipRelationDO::getReSceneModelId, sipRelation.getReSceneModelId())
                .eq(SipRelationDO::getId, sipRelation.getId());

        return update(null, updateWrapper);
    }
}
