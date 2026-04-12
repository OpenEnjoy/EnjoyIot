package com.enjoyiot.module.eiot.controller.admin.devicealert.vo;

import com.enjoyiot.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 设备告警记录分页 Request VO")
@Data
public class DeviceAlertRecordPageReqVO extends PageParam {

    @Schema(description = "设备ID")
    private Long deviceId;

    @Schema(description = "产品Key")
    private String productKey;

    @Schema(description = "告警状态: alert(触发) / recover(恢复)")
    private String alertState;

    @Schema(description = "开始时间")
    private Long startTime;

    @Schema(description = "结束时间")
    private Long endTime;
}
