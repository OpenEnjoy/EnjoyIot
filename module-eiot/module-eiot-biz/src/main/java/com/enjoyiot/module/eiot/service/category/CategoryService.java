
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
package com.enjoyiot.module.eiot.service.category;

import com.enjoyiot.module.eiot.controller.admin.category.vo.Category;
import com.enjoyiot.module.eiot.controller.admin.category.vo.CategoryListReqVO;
import com.enjoyiot.module.eiot.controller.admin.category.vo.CategorySaveReqVO;

import javax.validation.Valid;
import java.util.List;

/**
 * IOT产品分类 Service 接口
 *
 * @author EnjoyIot
 */
public interface CategoryService {

    /**
     * 创建IOT产品分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCategory(@Valid CategorySaveReqVO createReqVO);

    /**
     * 更新IOT产品分类
     *
     * @param updateReqVO 更新信息
     */
    void updateCategory(@Valid CategorySaveReqVO updateReqVO);

    /**
     * 删除IOT产品分类
     *
     * @param id 编号
     */
    void deleteCategory(Long id);

    /**
     * 获得IOT产品分类
     *
     * @param id 编号
     * @return IOT产品分类
     */
    Category getCategory(Long id);

    /**
     * 获得IOT产品分类列表
     *
     * @param listReqVO 查询条件
     * @return IOT产品分类列表
     */
    List<Category> getCategoryList(CategoryListReqVO listReqVO);

}
