package com.enjoyiot.module.eiot.controller.admin.devicealert.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Schema(description = "管理后台 - 设备告警配置新增/修改 Request VO")
@Data
public class DeviceAlertConfigSaveReqVO {

    @Schema(description = "告警配置id")
    private Long id;

    @Schema(description = "告警名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "告警名称不能为空")
    private String name;

    @Schema(description = "产品Key（产品级配置时必填）")
    private String productKey;

    @Schema(description = "设备ID（设备级配置时必填）")
    private Long deviceId;

    @Schema(description = "告警等级")
    private String level;

    @Schema(description = "告警条件")
    @NotNull(message = "告警条件不能为空")
    private List<ConditionVO> conditions;

    @Schema(description = "触发选项")
    private TriggerOptionsVO triggerOptions;

    @Schema(description = "状态(0启动 1禁用)")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @Data
    public static class ConditionVO {
        @Schema(description = "条件类型: property(属性) / status(状态)")
        private String type;

        @Schema(description = "属性标识或状态字段")
        private String key;

        @Schema(description = "比较运算符: >, <, ==, !=, >=, <=, contains, not_contains")
        private String operator;

        @Schema(description = "比较值")
        private String value;
    }

    @Data
    public static class TriggerOptionsVO {
        @Schema(description = "持续满足时间（秒），0表示立即触发")
        private Integer durationSec;

        @Schema(description = "静默时间（秒）")
        private Integer silentSec;

        @Schema(description = "是否开启告警恢复")
        private Boolean enableRecover;
    }
}
