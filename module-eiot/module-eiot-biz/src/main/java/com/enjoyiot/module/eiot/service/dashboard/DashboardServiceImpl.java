package com.enjoyiot.module.eiot.service.dashboard;

import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.alert.dto.AlertRecord;
import com.enjoyiot.module.eiot.api.device.dto.DeviceShortInfo;
import com.enjoyiot.module.eiot.api.product.dto.Product;
import com.enjoyiot.module.eiot.controller.admin.alertconfig.vo.AlertRecordPageReq;
import com.enjoyiot.module.eiot.controller.admin.dashboard.vo.*;
import com.enjoyiot.module.eiot.controller.admin.device.vo.DeviceInfoPageReqVO;
import com.enjoyiot.module.eiot.controller.admin.product.vo.ProductPageReqVO;
import com.enjoyiot.module.eiot.service.alert.AlertService;
import com.enjoyiot.module.eiot.service.device.DeviceInfoService;
import com.enjoyiot.module.eiot.service.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DashboardServiceImpl implements DashboardService {

    @Resource
    private DeviceInfoService deviceInfoService;

    @Resource
    private ProductService productService;

    @Resource
    private AlertService alertService;

    @Override
    public DashboardStatsVO getDashboardStats() {
        DashboardStatsVO stats = new DashboardStatsVO();
        stats.setDeviceStats(getDeviceStats());
        stats.setAlertStats(getAlertStats());
        stats.setProductStats(getProductStats());
        return stats;
    }

    @Override
    public DeviceStatsVO getDeviceStats() {
        DeviceStatsVO stats = new DeviceStatsVO();
        try {
            // 获取设备总数
            DeviceInfoPageReqVO pageReq = new DeviceInfoPageReqVO();
            pageReq.setPageNo(1);
            pageReq.setPageSize(1);
            PageResult<DeviceShortInfo> totalResult = deviceInfoService.getDeviceInfoPage(pageReq);
            stats.setTotalDevices(totalResult.getTotal());

            // 获取在线设备数
            DeviceInfoPageReqVO onlineReq = new DeviceInfoPageReqVO();
            onlineReq.setPageNo(1);
            onlineReq.setPageSize(1);
            onlineReq.setState(1); // 在线状态
            PageResult<DeviceShortInfo> onlineResult = deviceInfoService.getDeviceInfoPage(onlineReq);
            stats.setOnlineDevices(onlineResult.getTotal());

            // 获取离线设备数
            DeviceInfoPageReqVO offlineReq = new DeviceInfoPageReqVO();
            offlineReq.setPageNo(1);
            offlineReq.setPageSize(1);
            offlineReq.setState(0); // 离线状态
            PageResult<DeviceShortInfo> offlineResult = deviceInfoService.getDeviceInfoPage(offlineReq);
            stats.setOfflineDevices(offlineResult.getTotal());

            // 告警设备数（暂时设为0，后续可以根据需求实现）
            stats.setAlertDevices(0L);

        } catch (Exception e) {
            log.error("获取设备统计失败", e);
            stats.setTotalDevices(0L);
            stats.setOnlineDevices(0L);
            stats.setOfflineDevices(0L);
            stats.setAlertDevices(0L);
        }
        return stats;
    }

    @Override
    public AlertStatsVO getAlertStats() {
        AlertStatsVO stats = new AlertStatsVO();
        try {
            // 获取告警总数
            AlertRecordPageReq pageReq = new AlertRecordPageReq();
            pageReq.setPageNo(1);
            pageReq.setPageSize(1);
            PageResult<AlertRecord> result = alertService.selectAlertRecordPage(pageReq);
            stats.setTotalAlerts(result.getTotal());

            // 获取今日告警数
            AlertRecordPageReq todayReq = new AlertRecordPageReq();
            todayReq.setPageNo(1);
            todayReq.setPageSize(1);
            todayReq.setAlertTime(System.currentTimeMillis());
            PageResult<AlertRecord> todayResult = alertService.selectAlertRecordPage(todayReq);
            stats.setTodayAlerts(todayResult.getTotal());

            // 严重告警数和警告告警数（暂时设为0，后续可以根据需求实现）
            stats.setCriticalAlerts(0L);
            stats.setWarningAlerts(0L);

        } catch (Exception e) {
            log.error("获取告警统计失败", e);
            stats.setTotalAlerts(0L);
            stats.setTodayAlerts(0L);
            stats.setCriticalAlerts(0L);
            stats.setWarningAlerts(0L);
        }
        return stats;
    }

    @Override
    public ProductStatsVO getProductStats() {
        ProductStatsVO stats = new ProductStatsVO();
        try {
            // 获取产品总数
            ProductPageReqVO pageReq = new ProductPageReqVO();
            pageReq.setPageNo(1);
            pageReq.setPageSize(1);
            PageResult<Product> result = productService.getProductPage(pageReq);
            stats.setTotalProducts(result.getTotal());

            // 获取启用产品数
            ProductPageReqVO activeReq = new ProductPageReqVO();
            activeReq.setPageNo(1);
            activeReq.setPageSize(1);
            activeReq.setStatus(0); // 启用状态
            PageResult<Product> activeResult = productService.getProductPage(activeReq);
            stats.setActiveProducts(activeResult.getTotal());

        } catch (Exception e) {
            log.error("获取产品统计失败", e);
            stats.setTotalProducts(0L);
            stats.setActiveProducts(0L);
        }
        return stats;
    }

    @Override
    public List<DeviceLocationVO> getDeviceLocations() {
        return getDeviceLocationsByPage(1, 100);
    }

    @Override
    public List<DeviceLocationVO> getDeviceLocationsByPage(Integer pageNum, Integer pageSize) {
        List<DeviceLocationVO> locations = new ArrayList<>();
        try {
            DeviceInfoPageReqVO pageReq = new DeviceInfoPageReqVO();
            pageReq.setPageNo(pageNum);
            pageReq.setPageSize(pageSize);
            PageResult<DeviceShortInfo> result = deviceInfoService.getDeviceInfoPage(pageReq);

            if (result.getList() != null && !result.getList().isEmpty()) {
                locations = result.getList().stream()
                        .map(this::convertToLocationVO)
                        .filter(vo -> vo.getLat() != null && vo.getLng() != null)
                        .collect(Collectors.toList());
            }

        } catch (Exception e) {
            log.error("分页获取设备位置列表失败", e);
        }
        return locations;
    }

    private DeviceLocationVO convertToLocationVO(DeviceShortInfo device) {
        DeviceLocationVO vo = new DeviceLocationVO();
        vo.setId(device.getId());
        vo.setName(device.getName());
        vo.setSerialNo(device.getDn());
        vo.setProductKey(device.getProductKey());
        vo.setProductName(device.getProductName());
        vo.setLat(device.getLat());
        vo.setLng(device.getLon());

        // 设置设备状态
        if (device.getState() != null) {
            if (device.getState() == 1) {
                vo.setStatus("online");
            } else if (device.getState() == 0) {
                vo.setStatus("offline");
            } else {
                vo.setStatus("error");
            }
        } else {
            vo.setStatus("offline");
        }

        return vo;
    }
}
