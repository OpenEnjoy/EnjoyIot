package com.enjoyiot.module.eiot.controller.admin.dashboard.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 设备统计 VO")
@Data
public class DeviceStatsVO {

    @Schema(description = "设备总数")
    private Long totalDevices;

    @Schema(description = "在线设备数")
    private Long onlineDevices;

    @Schema(description = "离线设备数")
    private Long offlineDevices;

    @Schema(description = "告警设备数")
    private Long alertDevices;
}
