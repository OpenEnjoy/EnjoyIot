package com.enjoyiot.module.eiot.controller.admin.dashboard;

import com.enjoyiot.framework.common.pojo.CommonResult;
import com.enjoyiot.module.eiot.controller.admin.dashboard.vo.*;
import com.enjoyiot.module.eiot.service.dashboard.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.enjoyiot.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - Dashboard大屏")
@RestController
@RequestMapping("/eiot/dashboard")
@Validated
public class DashboardController {

    @Resource
    private DashboardService dashboardService;

    @GetMapping("/stats")
    @Operation(summary = "获取Dashboard统计数据")
    @PreAuthorize("@ss.hasPermission('iot:dashboard:query')")
    public CommonResult<DashboardStatsVO> getDashboardStats() {
        return success(dashboardService.getDashboardStats());
    }

    @GetMapping("/device-stats")
    @Operation(summary = "获取设备统计")
    @PreAuthorize("@ss.hasPermission('iot:dashboard:query')")
    public CommonResult<DeviceStatsVO> getDeviceStats() {
        return success(dashboardService.getDeviceStats());
    }

    @GetMapping("/alert-stats")
    @Operation(summary = "获取告警统计")
    @PreAuthorize("@ss.hasPermission('iot:dashboard:query')")
    public CommonResult<AlertStatsVO> getAlertStats() {
        return success(dashboardService.getAlertStats());
    }

    @GetMapping("/product-stats")
    @Operation(summary = "获取产品统计")
    @PreAuthorize("@ss.hasPermission('iot:dashboard:query')")
    public CommonResult<ProductStatsVO> getProductStats() {
        return success(dashboardService.getProductStats());
    }

    @GetMapping("/device-locations")
    @Operation(summary = "获取设备位置列表")
    @PreAuthorize("@ss.hasPermission('iot:dashboard:query')")
    public CommonResult<List<DeviceLocationVO>> getDeviceLocations(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "100") Integer pageSize) {
        return success(dashboardService.getDeviceLocationsByPage(pageNum, pageSize));
    }
}
