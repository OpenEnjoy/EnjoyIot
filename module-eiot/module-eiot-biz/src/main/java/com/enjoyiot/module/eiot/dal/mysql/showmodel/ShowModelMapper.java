
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
package com.enjoyiot.module.eiot.dal.mysql.showmodel;

import com.enjoyiot.framework.mybatis.core.mapper.BaseMapperX;
import com.enjoyiot.module.eiot.dal.dataobject.showmodel.ShowModelDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 产品显示模型 Mapper
 *
 * @author EnjoyIot
 */
@Mapper
public interface ShowModelMapper extends BaseMapperX<ShowModelDO> {

//    default PageResult<ShowModelDO> selectPage(ShowModelPageReqVO reqVO) {
//        return selectPage(reqVO, new LambdaQueryWrapperX<ShowModelDO>()
//                .eqIfPresent(ShowModelDO::getTyp, reqVO.getTyp())
//                .likeIfPresent(ShowModelDO::getName, reqVO.getName())
//                .eqIfPresent(ShowModelDO::getModelCode, reqVO.getModelCode())
//                .eqIfPresent(ShowModelDO::getStatus, reqVO.getStatus())
//                .betweenIfPresent(ShowModelDO::getCreateTime, reqVO.getCreateTime())
//                .orderByDesc(ShowModelDO::getId));
//    }

}
