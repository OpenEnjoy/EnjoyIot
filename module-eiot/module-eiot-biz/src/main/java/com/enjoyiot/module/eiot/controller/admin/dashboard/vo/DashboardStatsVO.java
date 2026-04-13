package com.enjoyiot.module.eiot.controller.admin.dashboard.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - Dashboard统计信息 VO")
@Data
public class DashboardStatsVO {

    @Schema(description = "设备统计")
    private DeviceStatsVO deviceStats;

    @Schema(description = "告警统计")
    private AlertStatsVO alertStats;

    @Schema(description = "产品统计")
    private ProductStatsVO productStats;

    @Schema(description = "设备位置列表")
    private java.util.List<DeviceLocationVO> deviceLocations;
}
