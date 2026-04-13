package com.enjoyiot.module.eiot.service.dashboard;

import com.enjoyiot.module.eiot.controller.admin.dashboard.vo.*;

import java.util.List;

/**
 * Dashboard大屏 Service接口
 *
 * @author EnjoyIot
 */
public interface DashboardService {

    /**
     * 获取Dashboard统计数据
     *
     * @return Dashboard统计数据
     */
    DashboardStatsVO getDashboardStats();

    /**
     * 获取设备统计
     *
     * @return 设备统计数据
     */
    DeviceStatsVO getDeviceStats();

    /**
     * 获取告警统计
     *
     * @return 告警统计数据
     */
    AlertStatsVO getAlertStats();

    /**
     * 获取产品统计
     *
     * @return 产品统计数据
     */
    ProductStatsVO getProductStats();

    /**
     * 获取设备位置列表
     *
     * @return 设备位置列表
     */
    List<DeviceLocationVO> getDeviceLocations();

    /**
     * 分页获取设备位置列表
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 设备位置列表
     */
    List<DeviceLocationVO> getDeviceLocationsByPage(Integer pageNum, Integer pageSize);
}
