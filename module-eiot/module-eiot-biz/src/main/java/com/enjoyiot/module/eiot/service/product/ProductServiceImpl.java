
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

import cn.hutool.core.lang.UUID;
import com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.util.object.BeanUtils;
import com.enjoyiot.framework.mybatis.core.query.MPJLambdaWrapperX;
import com.enjoyiot.module.eiot.api.enums.ErrorCodeConstants;
import com.enjoyiot.module.eiot.api.product.dto.Product;
import com.enjoyiot.module.eiot.api.product.dto.ProductConfig;
import com.enjoyiot.module.eiot.controller.admin.product.vo.ProductPageReqVO;
import com.enjoyiot.module.eiot.controller.admin.product.vo.ProductSaveReqVO;
import com.enjoyiot.module.eiot.controller.admin.product.vo.ProductUpdateReqVO;
import com.enjoyiot.module.eiot.convert.ProductConvert;
import com.enjoyiot.module.eiot.dal.dataobject.category.CategoryDO;
import com.enjoyiot.module.eiot.dal.dataobject.product.ProductDO;
import com.enjoyiot.module.eiot.dal.mysql.deviceinfo.EiotDeviceInfoMapper;
import com.enjoyiot.module.eiot.dal.mysql.product.ProductMapper;
import com.enjoyiot.module.eiot.dal.redis.RedisKeyConstants;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;


/**
 * 物联网产品 Service 实现类
 *
 * @author EnjoyIot
 */
@Service
@Validated
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private EiotDeviceInfoMapper deviceInfoMapper;

    @Resource
    private CacheManager cacheManager;

    @Override
    public Long createProduct(ProductSaveReqVO createReqVO) {
        // 插入
        ProductDO product = BeanUtils.toBean(createReqVO, ProductDO.class);
        String secret = UUID.randomUUID().toString(true);
        product.setProductSecret(secret);
        String productKey = createReqVO.getProductKey();

        if (productMapper.selectOne(ProductDO::getProductKey, productKey) != null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.PRODUCT_KEY_REPEAT);
        }

        productMapper.insert(product);
        // 返回
        return product.getId();
    }

    @Override
    @CacheEvict(value = RedisKeyConstants.PRODUCT, key = "#updateReqVO.productKey")
    public void updateProduct(ProductUpdateReqVO updateReqVO) {
        // 校验存在
        validateProductExists(updateReqVO.getId());
        // 更新
        ProductDO updateObj = BeanUtils.toBean(updateReqVO, ProductDO.class);
        productMapper.updateById(updateObj);
    }

    private void validateProductKeyExists(String productKey) {
        if (productMapper.selectOne(ProductDO::getProductKey, productKey) == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.PRODUCT_NOT_EXISTS);
        }
    }

    @Override
    public Boolean deleteProduct(Long id) {
        ProductDO product = validateProductExists(id);
        // 检测是否有设备
        if(deviceInfoMapper.selectCountByProductKey(product.getProductKey())>0){
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.PRODUCT_DEVICE_EXISTS);
        }
        Boolean ret= productMapper.deleteById(id)>0;
        if(ret){
            String productKey = product.getProductKey();
            // 清除缓存
            clearCache(RedisKeyConstants.PRODUCT,productKey);

        }
        return ret;
    }

    // 清理缓存
    private void clearCache(String cacheName, String key){
        Cache cache = cacheManager.getCache(cacheName);
        if(cache!=null){
            cache.evict(key);
        }
    }

    private ProductDO validateProductExists(Long id) {
        ProductDO productDO = productMapper.selectById(id);
        if (productDO == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.PRODUCT_NOT_EXISTS);
        }
        return productDO;
    }

    @Override
    public Product getProduct(Long id) {
        return ProductConvert.INSTANCE.convert(productMapper.selectById(id));
    }

    @Override
    public Product getByPk(String pk) {
        return ProductConvert.INSTANCE.convert(productMapper.selectOne(ProductDO::getProductKey, pk));
    }

    @Override
    public PageResult<Product> getProductPage(ProductPageReqVO pageReqVO) {
        MPJLambdaWrapperX<ProductDO> wrapperX = new MPJLambdaWrapperX<ProductDO>()
                .selectAll(ProductDO.class)
                .selectAs(CategoryDO::getName, Product::getCategoryName)
                .eqIfPresent(ProductDO::getCategoryId, pageReqVO.getCategoryId())
                .likeIfPresent(ProductDO::getName, pageReqVO.getName())
                .eqIfPresent(ProductDO::getProductKey, pageReqVO.getProductKey())
                .eqIfPresent(ProductDO::getNodeType, pageReqVO.getNodeType())
                .eqIfPresent(ProductDO::getProtocolCode, pageReqVO.getProtocolCode());

        wrapperX
                .leftJoin(CategoryDO.class, CategoryDO::getId, ProductDO::getCategoryId);

        return productMapper.selectJoinPage(pageReqVO, Product.class, wrapperX);
    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.PRODUCT, key = "#pk",
            unless = "#result == null")
    public Product getProductByPkFromCache(String pk) {
        return getByPk(pk);
    }

    @Override
    public ProductConfig getConfigByPk(String pk) {
        return null;
    }

    @Override
    public boolean saveConfig(ProductConfig request) {

        return true;
    }

}
