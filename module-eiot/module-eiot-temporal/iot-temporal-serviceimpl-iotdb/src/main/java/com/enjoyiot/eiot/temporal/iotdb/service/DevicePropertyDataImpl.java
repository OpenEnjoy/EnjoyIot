package com.enjoyiot.eiot.temporal.iotdb.service;


import com.enjoyiot.eiot.IDevicePropertyData;
import com.enjoyiot.eiot.temporal.iotdb.config.IotdbDatasourceConfig;
import com.enjoyiot.eiot.temporal.iotdb.dao.IotdbBaseService;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.device.dto.DeviceProperty;
import com.enjoyiot.module.eiot.api.device.dto.DevicePropertyCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.iotdb.isession.SessionDataSet;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.tsfile.read.common.Field;
import org.apache.iotdb.tsfile.read.common.RowRecord;
import org.apache.iotdb.tsfile.utils.Binary;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DevicePropertyDataImpl extends IotdbBaseService<DeviceProperty> implements IDevicePropertyData {

    @Resource
    private DeviceApi deviceApi;
    @Resource
    private IotdbDatasourceConfig config;

    /**
     * 按时间范围取设备指定属性的历史数据
     *
     * @param deviceId 设备id
     * @param name     属性名称
     * @param start    开始时间戳
     * @param end      结束时间戳
     * @param size     取时间范围内的数量
     */
    @Override
    public List<DeviceProperty> findDevicePropertyHistory(Long deviceId, String name, long start, long end, int size) {
        DeviceInfo device = deviceApi.getDeviceInfoFromCache(deviceId);
        if (device == null) {
            return new ArrayList<>();
        }

        String timeserieName = getTimeserieName(device.getProductKey(), device.getDn());
        String sql = String.format("select %s as `value` from %s where time > %d and time< %d limit %d", name, timeserieName, start, end, size);
        DeviceProperty args = new DeviceProperty();
        args.setDeviceId(device.getDn());
        args.setName(name);
        try {
            return queryList(sql, args);
        } catch (StatementExecutionException e) {
            log.error("findDevicePropertyHistory query failed: deviceId={}, name={}", deviceId, name, e);
        } catch (IoTDBConnectionException e) {
            log.error("findDevicePropertyHistory connection failed: deviceId={}, name={}", deviceId, name, e);
        }
        return new ArrayList<>();
    }

    /**
     * 添加多个属性
     *
     * @param deviceId   设备ID
     * @param properties 属性
     * @param time       属性上报时间
     */
    @Override
    public void addProperties(Long deviceId, Map<String, DevicePropertyCache> properties, long time) {
        DeviceInfo device = deviceApi.getDeviceInfoFromCache(deviceId);
        if (device == null) {
            return;
        }

        String timeserieName = getTimeserieName(device.getProductKey(), device.getDn());
        try {
            insertRecord(timeserieName, properties, time);
        } catch (StatementExecutionException | IoTDBConnectionException e) {
            log.error("addProperties failed: deviceId={}", deviceId, e);
        }
    }

    private String getTimeserieName(String productKey, String dn) {
        return config.getBaseDb() + ".p_" + productKey + ".d_" + dn;
    }

    @Override
    public List<DeviceProperty> mapToEntity(SessionDataSet dataSet, DeviceProperty defaultEntity) throws StatementExecutionException, IoTDBConnectionException {
        List<DeviceProperty> result = new ArrayList<>();
        Map<String, Integer> columnIndexMap = new HashMap<>();
        for (int i = 0; i < dataSet.getColumnNames().size(); i++) {
            String columnName = dataSet.getColumnNames().get(i);
            columnIndexMap.put(columnName, i - 1);
        }
        String deviceId = defaultEntity.getDeviceId();
        String valueName = defaultEntity.getName();
        while (dataSet.hasNext()) {
            RowRecord rowRecord = dataSet.next();
            long timestamp = rowRecord.getTimestamp();
            Object value = tryGetObjectValue(rowRecord, "value", columnIndexMap);
            DeviceProperty one = new DeviceProperty(String.valueOf(timestamp), deviceId, valueName, value, timestamp);
            result.add(one);
        }
        return result;
    }
}
