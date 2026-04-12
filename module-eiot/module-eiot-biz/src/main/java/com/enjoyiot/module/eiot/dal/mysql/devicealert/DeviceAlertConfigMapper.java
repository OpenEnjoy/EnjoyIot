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
package com.enjoyiot.module.eiot.dal.mysql.devicealert;

import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.mybatis.core.mapper.BaseMapperX;
import com.enjoyiot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.enjoyiot.module.eiot.controller.admin.devicealert.vo.DeviceAlertConfigPageReqVO;
import com.enjoyiot.module.eiot.dal.dataobject.devicealert.DeviceAlertConfigDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeviceAlertConfigMapper extends BaseMapperX<DeviceAlertConfigDO> {

    default PageResult<DeviceAlertConfigDO> selectPage(DeviceAlertConfigPageReqVO reqVO) {
        String productKey = reqVO.getProductKey();
        Long deviceId = reqVO.getDeviceId();
        String name = reqVO.getName();
        Integer status = reqVO.getStatus();

        return selectPage(reqVO, new LambdaQueryWrapperX<DeviceAlertConfigDO>()
                .eqIfPresent(DeviceAlertConfigDO::getProductKey, productKey)
                .eqIfPresent(DeviceAlertConfigDO::getDeviceId, deviceId)
                .likeIfPresent(DeviceAlertConfigDO::getName, name)
                .eqIfPresent(DeviceAlertConfigDO::getStatus, status)
                .orderByDesc(DeviceAlertConfigDO::getId));
    }

    default List<DeviceAlertConfigDO> selectByProductKey(String productKey) {
        return selectList(new LambdaQueryWrapperX<DeviceAlertConfigDO>()
                .eq(DeviceAlertConfigDO::getProductKey, productKey)
                .eq(DeviceAlertConfigDO::getStatus, 0));
    }

    default List<DeviceAlertConfigDO> selectByDeviceId(Long deviceId) {
        return selectList(new LambdaQueryWrapperX<DeviceAlertConfigDO>()
                .eq(DeviceAlertConfigDO::getDeviceId, deviceId)
                .eq(DeviceAlertConfigDO::getStatus, 0));
    }
}
