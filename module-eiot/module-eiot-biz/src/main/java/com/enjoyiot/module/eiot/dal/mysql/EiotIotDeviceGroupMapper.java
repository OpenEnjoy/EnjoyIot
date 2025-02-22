
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
package com.enjoyiot.module.eiot.dal.mysql;

import com.enjoyiot.framework.mybatis.core.mapper.BaseMapperX;
import com.enjoyiot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.enjoyiot.module.eiot.dal.dataobject.DeviceGroupDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 设备分组 Mapper
 *
 * @author EnjoyIot
 */
@Mapper
public interface EiotIotDeviceGroupMapper extends BaseMapperX<DeviceGroupDO> {


    default void deleteByDeviceIds(List<Long> deviceIds){
        LambdaQueryWrapperX<DeviceGroupDO> queryWrapperX = new LambdaQueryWrapperX<DeviceGroupDO>().in(DeviceGroupDO::getDeviceId, deviceIds);
        delete(queryWrapperX);
    };

    default int removeDevicesInGroup(Long groupId,List<Long> deviceIds){
        LambdaQueryWrapperX<DeviceGroupDO> queryWrapperX = new LambdaQueryWrapperX<DeviceGroupDO>()
                .eq(DeviceGroupDO::getGroupId, groupId)
                .in(DeviceGroupDO::getDeviceId, deviceIds);
        return delete(queryWrapperX);
    };
}
