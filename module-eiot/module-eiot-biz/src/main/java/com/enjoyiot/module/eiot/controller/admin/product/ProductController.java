
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
package com.enjoyiot.module.eiot.controller.admin.product;

import com.enjoyiot.module.eiot.api.product.dto.Product;
import com.enjoyiot.module.eiot.api.product.dto.ProductConfig;
import com.enjoyiot.module.eiot.controller.convert.ProductBizConvert;
import com.enjoyiot.module.eiot.service.product.ProductService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

import com.enjoyiot.framework.common.pojo.PageParam;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.pojo.CommonResult;
import com.enjoyiot.framework.common.util.object.BeanUtils;
import static com.enjoyiot.framework.common.pojo.CommonResult.success;

import com.enjoyiot.framework.excel.core.util.ExcelUtils;

import com.enjoyiot.framework.apilog.core.annotation.ApiAccessLog;
import static com.enjoyiot.framework.apilog.core.enums.OperateTypeEnum.*;

import com.enjoyiot.module.eiot.controller.admin.product.vo.*;

@Tag(name = "管理后台 - 物联网产品")
@RestController
@RequestMapping("/eiot/product")
@Validated
public class ProductController {

    @Resource
    private ProductService productService;

    @PostMapping("/create")
    @Operation(summary = "创建物联网产品")
    @PreAuthorize("@ss.hasPermission('iot:product:create')")
    public CommonResult<Long> createProduct(@Valid @RequestBody ProductSaveReqVO createReqVO) {
        return success(productService.createProduct(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新物联网产品")
    @PreAuthorize("@ss.hasPermission('iot:product:update')")
    public CommonResult<Boolean> updateProduct(@Valid @RequestBody ProductUpdateReqVO updateReqVO) {
        productService.updateProduct(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除物联网产品")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('iot:product:delete')")
    public CommonResult<Boolean> deleteProduct(@RequestParam("id") Long id) {
        productService.deleteProduct(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得物联网产品")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('iot:product:query')")
    public CommonResult<ProductRespVO> getProduct(@RequestParam("id") Long id) {
        Product product = productService.getProduct(id);
        return success(BeanUtils.toBean(product, ProductRespVO.class));
    }

    @GetMapping("/getByPk")
    @Operation(summary = "获得物联网产品")
    @Parameter(name = "pk", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('iot:product:query')")
    public CommonResult<ProductRespVO> getProduct(@RequestParam("pk") String pk) {
        Product product = productService.getByPk(pk);
        return success(BeanUtils.toBean(product, ProductRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得物联网产品分页")
    @PreAuthorize("@ss.hasPermission('iot:product:query')")
    public CommonResult<PageResult<ProductRespVO>> getProductPage(@Valid ProductPageReqVO pageReqVO) {
        PageResult<Product> pageResult = productService.getProductPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ProductRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出物联网产品 Excel")
    @PreAuthorize("@ss.hasPermission('iot:product:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportProductExcel(@Valid ProductPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<Product> list = productService.getProductPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "物联网产品.xls", "数据", ProductRespVO.class,
                        BeanUtils.toBean(list, ProductRespVO.class));
    }

    /**
     * 获取产品配置详细信息
     *
     */
    @PreAuthorize("@ss.hasPermission('iot:product:query')")
    @GetMapping("/getConfig")
    @Parameter(name = "pk", description = "productKey", required = true, example = "1024")
    public ProductConfigVo getDetail(@RequestParam("pk") String pk) {
        return ProductBizConvert.INSTANCE.convertVO( productService.getConfigByPk(pk));
    }


    /**
     * 修改产品配置
     */
    @PreAuthorize("@ss.hasPermission('iot:product:edit')")
    @PostMapping("/saveConfig")
    public boolean saveConfig( ProductConfigBo request) {
        ProductConfig config = ProductBizConvert.INSTANCE.convert(request);
        return productService.saveConfig(config);
    }
}
