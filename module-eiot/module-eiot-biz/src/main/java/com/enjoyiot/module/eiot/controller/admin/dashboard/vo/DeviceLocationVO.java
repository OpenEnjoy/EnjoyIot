package com.enjoyiot.module.eiot.controller.admin.dashboard.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 设备位置 VO")
@Data
public class DeviceLocationVO {

    @Schema(description = "设备ID")
    private Long id;

    @Schema(description = "设备名称")
    private String name;

    @Schema(description = "设备序列号")
    private String serialNo;

    @Schema(description = "纬度")
    private Double lat;

    @Schema(description = "经度")
    private Double lng;

    @Schema(description = "设备状态: online=在线, offline=离线, error=告警")
    private String status;

    @Schema(description = "产品ID")
    private String productKey;

    @Schema(description = "产品名称")
    private String productName;

    @Schema(description = "设备地址")
    private String address;

}
