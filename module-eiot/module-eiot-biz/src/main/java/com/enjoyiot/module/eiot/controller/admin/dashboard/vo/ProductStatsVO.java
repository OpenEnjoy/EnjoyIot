package com.enjoyiot.module.eiot.controller.admin.dashboard.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 产品统计 VO")
@Data
public class ProductStatsVO {

    @Schema(description = "产品总数")
    private Long totalProducts;

    @Schema(description = "启用产品数")
    private Long activeProducts;
}
