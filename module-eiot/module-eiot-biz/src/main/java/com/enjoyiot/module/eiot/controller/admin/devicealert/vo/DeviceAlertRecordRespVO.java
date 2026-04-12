package com.enjoyiot.module.eiot.controller.admin.devicealert.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 设备告警记录 Response VO")
@Data
public class DeviceAlertRecordRespVO {

    @Schema(description = "告警记录id")
    private Long id;

    @Schema(description = "设备ID")
    private Long deviceId;

    @Schema(description = "告警配置ID")
    private Long alertConfigId;

    @Schema(description = "告警时间")
    private Long alertTime;

    @Schema(description = "告警状态: alert(触发) / recover(恢复)")
    private String alertState;

    @Schema(description = "恢复时间")
    private Long recoverTime;

    @Schema(description = "告警等级")
    private String level;

    @Schema(description = "告警名称")
    private String name;

    @Schema(description = "告警详情")
    private String details;

    @Schema(description = "是否已读")
    private Boolean readFlg;

    @Schema(description = "创建时间")
    private Long createTime;
}
