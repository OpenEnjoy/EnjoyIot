
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
package com.enjoyiot.module.eiot.dal.mysql.deviceinfo;

import cn.hutool.core.util.ObjectUtil;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.mybatis.core.mapper.BaseMapperX;
import com.enjoyiot.framework.mybatis.core.query.MPJLambdaWrapperX;
import com.enjoyiot.module.eiot.api.device.dto.DeviceShortInfo;
import com.enjoyiot.module.eiot.controller.admin.device.vo.DeviceInfoPageReqVO;
import com.enjoyiot.module.eiot.dal.dataobject.DeviceGroupDO;
import com.enjoyiot.module.eiot.dal.dataobject.deviceinfo.EiotDeviceInfoDO;
import com.enjoyiot.module.eiot.dal.dataobject.product.ProductDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备信息 Mapper
 *
 * @author EnjoyIot
 */
@Mapper
public interface EiotDeviceInfoMapper extends BaseMapperX<EiotDeviceInfoDO> {

    default PageResult<DeviceShortInfo> selectPage(DeviceInfoPageReqVO reqVO) {
        MPJLambdaWrapperX<EiotDeviceInfoDO> q = new MPJLambdaWrapperX<EiotDeviceInfoDO>()
                .selectAll(EiotDeviceInfoDO.class)
                .selectAs(ProductDO::getName, DeviceShortInfo::getProductName)
                .likeIfPresent(EiotDeviceInfoDO::getDn, reqVO.getDn())
                .eqIfPresent(EiotDeviceInfoDO::getProductKey, reqVO.getProductKey())
                .eqIfPresent(EiotDeviceInfoDO::getDeptId, reqVO.getDeptId())
                .betweenIfPresent(EiotDeviceInfoDO::getCreateTime, reqVO.getCreateTime())
                .likeIfPresent(EiotDeviceInfoDO::getName, reqVO.getName())
                .eqIfPresent(EiotDeviceInfoDO::getState, reqVO.getState())
                .eqIfPresent(EiotDeviceInfoDO::getSerialNo, reqVO.getSerialNo())
                .orderByDesc(EiotDeviceInfoDO::getId);
        q.leftJoin(ProductDO.class, ProductDO::getProductKey, EiotDeviceInfoDO::getProductKey);
        if(ObjectUtil.isNotNull(reqVO.getGroupId())){
            q.innerJoin(DeviceGroupDO.class, DeviceGroupDO::getDeviceId, EiotDeviceInfoDO::getId);
            q.eq(DeviceGroupDO::getGroupId, reqVO.getGroupId());
        }
        q.eq(ObjectUtil.isNotNull(reqVO.getNodeType()), ProductDO::getNodeType, reqVO.getNodeType());
        return selectJoinPage(reqVO, DeviceShortInfo.class, q);
    }

    default Long selectCountByProductKey(String productKey){
        return selectCount(EiotDeviceInfoDO::getProductKey, productKey);
    }
}
