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
package com.enjoyiot.module.eiot.dal.mysql.deviceconfig;

import com.enjoyiot.framework.mybatis.core.mapper.BaseMapperX;
import com.enjoyiot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.enjoyiot.module.eiot.dal.dataobject.deviceconfig.DeviceConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备配置 Mapper
 */
@Mapper
public interface DeviceConfigMapper extends BaseMapperX<DeviceConfigDO> {

    /**
     * 根据产品key和设备dn获取配置
     */
    default DeviceConfigDO selectByPkDn(String productKey, String dn) {
        return selectOne(new LambdaQueryWrapperX<DeviceConfigDO>()
                .eq(DeviceConfigDO::getProductKey, productKey)
                .eq(DeviceConfigDO::getDn, dn));
    }
}

