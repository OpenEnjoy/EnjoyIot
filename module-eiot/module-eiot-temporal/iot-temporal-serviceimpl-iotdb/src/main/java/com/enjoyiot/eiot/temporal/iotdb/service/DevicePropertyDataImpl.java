package com.enjoyiot.eiot.temporal.iotdb.service;


import com.enjoyiot.eiot.IDevicePropertyData;
import com.enjoyiot.eiot.temporal.iotdb.config.IotdbDatasourceConfig;
import com.enjoyiot.eiot.temporal.iotdb.dao.IotdbBaseService;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.device.dto.DeviceProperty;
import com.enjoyiot.module.eiot.api.device.dto.DevicePropertyCache;
import org.apache.iotdb.isession.SessionDataSet;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.tsfile.read.common.Field;
import org.apache.iotdb.tsfile.read.common.RowRecord;
import org.apache.iotdb.tsfile.utils.Binary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            e.printStackTrace();
        } catch (IoTDBConnectionException e) {
            e.printStackTrace();
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
        //获取设备旧属性
        Map<String, DevicePropertyCache> oldProperties = deviceApi.getPropertiesFromCache(deviceId);
        //用新属性覆盖
        oldProperties.putAll(properties);

        String timeserieName = getTimeserieName(device.getProductKey(), device.getDn());
        try {
            insertRecord(timeserieName, oldProperties, time);
        } catch (StatementExecutionException | IoTDBConnectionException e) {
            e.printStackTrace();
        }
    }

    private String getTimeserieName(String productKey, String dn) {
        return config.getBaseDb() + ".p_" + productKey + ".d_" + dn;
    }

    @Override
    public List<DeviceProperty> mapToEntity(SessionDataSet dataSet, DeviceProperty defaultEntity) throws StatementExecutionException, IoTDBConnectionException {
        List<DeviceProperty> result = new ArrayList<>();
        int index = dataSet.getColumnNames().indexOf("value");
        if (index > 0) {
            index--;
            String deviceId = defaultEntity.getDeviceId();
            String valueName = defaultEntity.getName();
            while (dataSet.hasNext()) {
                RowRecord rowRecord = dataSet.next();
                long timestamp = rowRecord.getTimestamp();
                Field valueField = rowRecord.getFields().get(index);
                Object value = valueField.getObjectValue(valueField.getDataType());
                if (value instanceof Binary) {
                    value = value.toString();
                }
                DeviceProperty one = new DeviceProperty(String.valueOf(timestamp), deviceId, valueName, value, timestamp);
                result.add(one);
            }
        }
        return result;
    }
}
