package com.enjoyiot.module.eiot.controller.admin.devicealert.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Schema(description = "管理后台 - 设备告警配置 Response VO")
@Data
public class DeviceAlertConfigRespVO {

    @Schema(description = "告警配置id")
    private Long id;

    @Schema(description = "告警名称")
    private String name;

    @Schema(description = "产品Key")
    private String productKey;

    @Schema(description = "设备ID")
    private Long deviceId;

    @Schema(description = "告警等级")
    private String level;

    @Schema(description = "告警条件")
    private List<DeviceAlertConfigSaveReqVO.ConditionVO> conditions;

    @Schema(description = "触发选项")
    private DeviceAlertConfigSaveReqVO.TriggerOptionsVO triggerOptions;

    @Schema(description = "状态(0启动 1禁用)")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private Long createTime;
}
