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
import com.enjoyiot.module.eiot.controller.admin.devicealert.vo.DeviceAlertRecordPageReqVO;
import com.enjoyiot.module.eiot.dal.dataobject.devicealert.DeviceAlertRecordDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeviceAlertRecordMapper extends BaseMapperX<DeviceAlertRecordDO> {

    default List<DeviceAlertRecordDO> selectListByDeviceId(Long deviceId) {
        return selectList(new LambdaQueryWrapperX<DeviceAlertRecordDO>()
                .eq(DeviceAlertRecordDO::getDeviceId, deviceId)
                .orderByDesc(DeviceAlertRecordDO::getAlertTime));
    }

    default PageResult<DeviceAlertRecordDO> selectPage(DeviceAlertRecordPageReqVO pageReqVO) {

        Long deviceId = pageReqVO.getDeviceId();
        String productKey = pageReqVO.getProductKey();
        String alertState = pageReqVO.getAlertState();
        Long startTime = pageReqVO.getStartTime();
        Long endTime = pageReqVO.getEndTime();

        return selectPage(pageReqVO,new LambdaQueryWrapperX<DeviceAlertRecordDO>()
                .eqIfPresent(DeviceAlertRecordDO::getDeviceId, deviceId)
                .eqIfPresent(DeviceAlertRecordDO::getProductKey, productKey)
                .eqIfPresent(DeviceAlertRecordDO::getAlertState, alertState)
                .geIfPresent(DeviceAlertRecordDO::getAlertTime, startTime)
                .leIfPresent(DeviceAlertRecordDO::getAlertTime, endTime)
                .orderByDesc(DeviceAlertRecordDO::getId));
    }

    default DeviceAlertRecordDO selectActiveAlert(Long deviceId, String alertName) {
        return selectOne(new LambdaQueryWrapperX<DeviceAlertRecordDO>()
                .eq(DeviceAlertRecordDO::getDeviceId, deviceId)
                .eq(DeviceAlertRecordDO::getName, alertName)
                .eq(DeviceAlertRecordDO::getAlertState, "alert")
                .orderByDesc(DeviceAlertRecordDO::getAlertTime)
                .last("LIMIT 1"));
    }
}
