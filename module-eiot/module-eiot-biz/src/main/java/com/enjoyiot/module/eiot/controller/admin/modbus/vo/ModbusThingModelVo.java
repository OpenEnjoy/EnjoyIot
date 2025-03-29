package com.enjoyiot.module.eiot.controller.admin.modbus.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Data
public class ModbusThingModelVo implements Serializable {
    private static final long serialVersionUID = -1L;

    @Schema(description = "模型内容")
    @Size(max = 65535, message = "模型内容长度不正确")
    private String model;

    @NotBlank(message="产品Key不能为空")
    @Schema(description = "产品key")
    @Size(min = 16, max = 16, message = "产品key长度不正确")
    private String productKey;

}
