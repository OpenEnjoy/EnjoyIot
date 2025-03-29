package com.enjoyiot.module.eiot.controller.admin.modbus.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - Modbus产品 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ModbusInfoRespVo {
    @Schema(description = "id")
    @ExcelProperty("编号")
    private Long id;


    @Schema(description = "产品名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("产品名称")
    private String name;


    @Schema(description = "产品Key", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("产品Key")
    private String productKey;

    @Schema(description = "说明")
    @ExcelProperty("说明")
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description ="创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;
    /**
     * 最后更新时间
     */
    @Schema(description = "更新时间")
    @ExcelProperty("更新时间")
    private LocalDateTime updateTime;
}
