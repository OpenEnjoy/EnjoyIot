package com.enjoyiot.module.eiot.controller.admin.modbus.vo;

import com.enjoyiot.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Data
public class ModbusInfoVo extends PageParam implements Serializable {

	private static final long serialVersionUID = -1L;


	@Schema(description = "id")
	private Long id;

	@NotBlank(message="产品名称不能为空")
	@Schema(description = "产品名称", requiredMode = Schema.RequiredMode.REQUIRED)
	private String name;

	@NotBlank(message="产品Key不能为空")
	@Schema(description = "产品Key", requiredMode = Schema.RequiredMode.REQUIRED)
	private String productKey;

	@Schema(description = "说明")
	private String remark;


    }
