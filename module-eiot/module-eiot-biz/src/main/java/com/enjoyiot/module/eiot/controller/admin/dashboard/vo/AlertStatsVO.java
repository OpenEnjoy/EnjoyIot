package com.enjoyiot.module.eiot.controller.admin.dashboard.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 告警统计 VO")
@Data
public class AlertStatsVO {

    @Schema(description = "告警总数")
    private Long totalAlerts;

    @Schema(description = "今日告警数")
    private Long todayAlerts;

    @Schema(description = "严重告警数")
    private Long criticalAlerts;

    @Schema(description = "警告告警数")
    private Long warningAlerts;
}
