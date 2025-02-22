
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

import com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil;
import com.enjoyiot.framework.common.util.object.BeanUtils;
import com.enjoyiot.module.eiot.api.enums.ErrorCodeConstants;
import com.enjoyiot.module.eiot.controller.admin.category.vo.Category;
import com.enjoyiot.module.eiot.controller.admin.category.vo.CategoryListReqVO;
import com.enjoyiot.module.eiot.controller.admin.category.vo.CategorySaveReqVO;
import com.enjoyiot.module.eiot.convert.ProductConvert;
import com.enjoyiot.module.eiot.dal.dataobject.category.CategoryDO;
import com.enjoyiot.module.eiot.dal.mysql.category.CategoryMapper;
import com.enjoyiot.module.eiot.dal.mysql.product.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;


/**
 * IOT产品分类 Service 实现类
 *
 * @author EnjoyIot
 */
@Service
@Validated
public class CategoryServiceImpl implements CategoryService {


    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private ProductMapper productMapper;

    @Override
    public Long createCategory(CategorySaveReqVO createReqVO) {
        // 校验父分类id的有效性
        validateParentCategory(null, createReqVO.getParentId());
        // 校验分类名称的唯一性
        validateCategoryNameUnique(null, createReqVO.getParentId(), createReqVO.getName());

        // 插入
        CategoryDO category = BeanUtils.toBean(createReqVO, CategoryDO.class);
        categoryMapper.insert(category);
        // 返回
        return category.getId();
    }

    @Override
    public void updateCategory(CategorySaveReqVO updateReqVO) {
        // 校验存在
        validateCategoryExists(updateReqVO.getId());
        // 校验父分类id的有效性
        validateParentCategory(updateReqVO.getId(), updateReqVO.getParentId());
        // 校验分类名称的唯一性
        validateCategoryNameUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getName());

        // 更新
        CategoryDO updateObj = BeanUtils.toBean(updateReqVO, CategoryDO.class);
        categoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteCategory(Long id) {
        // 校验存在
        validateCategoryExists(id);
        // 校验是否有子IOT产品分类
        if (categoryMapper.selectCountByParentId(id) > 0) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.CATEGORY_EXITS_CHILDREN);
        }
        // 判断是否有产品关联
        if (productMapper.selectCountByCategoryId(id) > 0) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.CATEGORY_EXITS_PRODUCT);
        }

        // 删除
        categoryMapper.deleteById(id);
    }

    private void validateCategoryExists(Long id) {
        if (categoryMapper.selectById(id) == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.CATEGORY_NOT_EXISTS);
        }
    }

    private void validateParentCategory(Long id, Long parentId) {
        if (parentId == null || CategoryDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. 不能设置自己为父IOT产品分类
        if (Objects.equals(id, parentId)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.CATEGORY_PARENT_ERROR);
        }
        // 2. 父IOT产品分类不存在
        CategoryDO parentCategory = categoryMapper.selectById(parentId);
        if (parentCategory == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.CATEGORY_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父IOT产品分类，如果父IOT产品分类是自己的子IOT产品分类，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentId = parentCategory.getParentId();
            if (Objects.equals(id, parentId)) {
                throw ServiceExceptionUtil.exception(ErrorCodeConstants.CATEGORY_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父IOT产品分类
            if (parentId == null || CategoryDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentCategory = categoryMapper.selectById(parentId);
            if (parentCategory == null) {
                break;
            }
        }
    }

    private void validateCategoryNameUnique(Long id, Long parentId, String name) {
        CategoryDO category = categoryMapper.selectByParentIdAndName(parentId, name);
        if (category == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的IOT产品分类
        if (id == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.CATEGORY_NAME_DUPLICATE);
        }
        if (!Objects.equals(category.getId(), id)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.CATEGORY_NAME_DUPLICATE);
        }
    }

    @Override
    public Category getCategory(Long id) {
        return ProductConvert.INSTANCE.convertCategory(categoryMapper.selectById(id));
    }

    @Override
    public List<Category> getCategoryList(CategoryListReqVO listReqVO) {
        return ProductConvert.INSTANCE.convertCategoryList(categoryMapper.selectList(listReqVO));
    }

}
