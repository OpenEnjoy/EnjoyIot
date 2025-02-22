
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
package com.enjoyiot.module.eiot.dal.mysql.product;

import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.mybatis.core.mapper.BaseMapperX;
import com.enjoyiot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.enjoyiot.module.eiot.controller.admin.product.vo.ProductPageReqVO;
import com.enjoyiot.module.eiot.dal.dataobject.product.ProductDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 物联网产品 Mapper
 *
 * @author EnjoyIot
 */
@Mapper
public interface ProductMapper extends BaseMapperX<ProductDO> {

    default PageResult<ProductDO> selectPage(ProductPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProductDO>()
                .likeIfPresent(ProductDO::getName, reqVO.getName())
                .eqIfPresent(ProductDO::getCategoryId, reqVO.getCategoryId())
                .eqIfPresent(ProductDO::getProductKey, reqVO.getProductKey())
                .eqIfPresent(ProductDO::getMcuCode, reqVO.getMcuCode())
                .eqIfPresent(ProductDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ProductDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ProductDO::getNodeType, reqVO.getNodeType())
                .eqIfPresent(ProductDO::getProtocolCode, reqVO.getProtocolCode())
                .eqIfPresent(ProductDO::getTransparent, reqVO.getTransparent())
                .orderByDesc(ProductDO::getId));
    }

    default ProductDO getByProductKey(String productKey){
        return selectOne(ProductDO::getProductKey, productKey);
    }

    default Long selectCountByCategoryId(Long categoryId){
        return selectCount(ProductDO::getCategoryId, categoryId);
    };
}
