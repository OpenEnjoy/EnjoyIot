package com.enjoyiot.module.eiot.controller.admin.devicealert.vo;

import com.enjoyiot.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 设备告警配置分页 Request VO")
@Data
public class DeviceAlertConfigPageReqVO extends PageParam {

    @Schema(description = "产品Key")
    private String productKey;

    @Schema(description = "设备ID")
    private Long deviceId;

    @Schema(description = "告警名称")
    private String name;

    @Schema(description = "状态(0启动 1禁用)")
    private Integer status;

    @Schema(description = "告警等级")
    private String level;
}
