
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
package com.enjoyiot.module.eiot.service.product;

import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.product.dto.Product;
import com.enjoyiot.module.eiot.api.product.dto.ProductConfig;
import com.enjoyiot.module.eiot.controller.admin.product.vo.ProductPageReqVO;
import com.enjoyiot.module.eiot.controller.admin.product.vo.ProductSaveReqVO;
import com.enjoyiot.module.eiot.controller.admin.product.vo.ProductUpdateReqVO;

import javax.validation.Valid;

/**
 * 物联网产品 Service 接口
 *
 * @author EnjoyIot
 */
public interface ProductService {

    /**
     * 创建物联网产品
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProduct(@Valid ProductSaveReqVO createReqVO);

    /**
     * 更新物联网产品
     *
     * @param updateReqVO 更新信息
     */
    void updateProduct(@Valid ProductUpdateReqVO updateReqVO);

    /**
     * 删除物联网产品
     *
     * @param id 编号
     */
    Boolean deleteProduct(Long id);

    /**
     * 获得物联网产品
     *
     * @param id 编号
     * @return 物联网产品
     */
    Product getProduct(Long id);

    Product getByPk(String pk);

    /**
     * 获得物联网产品分页
     *
     * @param pageReqVO 分页查询
     * @return 物联网产品分页
     */
    PageResult<Product> getProductPage(ProductPageReqVO pageReqVO);

    Product getProductByPkFromCache(String pk);

    ProductConfig getConfigByPk(String pk);

    boolean saveConfig(ProductConfig request);
}
