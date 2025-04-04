
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
package com.enjoyiot.module.eiot.dal.mysql.virtualdevice;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.mybatis.core.mapper.BaseMapperX;
import com.enjoyiot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.enjoyiot.module.eiot.controller.admin.virtualdevice.vo.EiotVirtualSaveScriptVo;
import com.enjoyiot.module.eiot.controller.admin.virtualdevice.vo.VirtualDevicePageReqVO;
import com.enjoyiot.module.eiot.dal.dataobject.virtualdevice.VirtualDeviceDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 规则引擎 Mapper
 *
 * @author EnjoyIot
 */
@Mapper
public interface EiotVirtualDeviceMapper extends BaseMapperX<VirtualDeviceDO> {


    default PageResult<VirtualDeviceDO> selectPage(VirtualDevicePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VirtualDeviceDO>()
                .eqIfPresent(VirtualDeviceDO::getState, reqVO.getState())
                .eqIfPresent(VirtualDeviceDO::getProductKey, reqVO.getProductKey())
                .orderByDesc(VirtualDeviceDO::getId));
    }

    default int updateScriptById(EiotVirtualSaveScriptVo saveScriptVo) {
        LambdaUpdateWrapper<VirtualDeviceDO> up = new LambdaUpdateWrapper<>();
        up.set(VirtualDeviceDO::getScript, saveScriptVo.getScript());
        up.eq(VirtualDeviceDO::getId, saveScriptVo.getId());
        return update(up);
    }

}
