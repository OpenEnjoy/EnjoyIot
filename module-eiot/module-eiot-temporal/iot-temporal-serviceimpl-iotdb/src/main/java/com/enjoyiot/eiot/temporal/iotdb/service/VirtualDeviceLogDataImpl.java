package com.enjoyiot.eiot.temporal.iotdb.service;


import com.enjoyiot.eiot.IVirtualDeviceLogData;
import com.enjoyiot.eiot.temporal.iotdb.config.Constants;
import com.enjoyiot.eiot.temporal.iotdb.config.IotdbDatasourceConfig;
import com.enjoyiot.eiot.temporal.iotdb.dao.IotdbBaseService;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.device.dto.DevicePropertyCache;
import com.enjoyiot.module.eiot.api.virtualdevice.dto.VirtualDeviceLog;
import org.apache.iotdb.isession.SessionDataSet;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.tsfile.read.common.RowRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VirtualDeviceLogDataImpl extends IotdbBaseService<VirtualDeviceLog> implements IVirtualDeviceLogData {

    @Resource
    private IotdbDatasourceConfig config;


    /**
     * 根据虚拟设备id分页查询日志
     *
     * @param virtualDeviceId
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<VirtualDeviceLog> findByVirtualDeviceId(Long virtualDeviceId, int page, int size) {
        int offset = Integer.min(0, (page - 1) * size);
        String timeserieName = getTimeserieName(virtualDeviceId);

        String countSql = String.format("select count(%s) from %s", "virtual_device_name", timeserieName);
        String sql = String.format("select virtual_device_name as virtual_device_name,device_total as device_total,result as result from %s limit %d offset  %d ", timeserieName, size, offset);
        VirtualDeviceLog args = new VirtualDeviceLog();
        args.setVirtualDeviceId(virtualDeviceId);
        try {
            return queryPage(countSql, sql, args);
        } catch (StatementExecutionException e) {
            e.printStackTrace();
        } catch (IoTDBConnectionException e) {
            e.printStackTrace();
        }
        return new PageResult<>(new ArrayList<>(), 0L);
    }

    /**
     * 新增虚拟设备日志
     *
     * @param log
     */
    @Override
    public void add(VirtualDeviceLog log) {
        String timeserieName = getTimeserieName(log.getVirtualDeviceId());

        long time = System.currentTimeMillis();
        Map<String, DevicePropertyCache> properties = new HashMap<>();
        properties.put("virtual_device_name", new DevicePropertyCache(log.getVirtualDeviceName(), time));
        properties.put("device_total", new DevicePropertyCache(log.getDeviceTotal(), time));
        properties.put("result", new DevicePropertyCache(log.getResult(), time));
        try {
            insertRecord(timeserieName, properties);
        } catch (StatementExecutionException e) {
            e.printStackTrace();
        } catch (IoTDBConnectionException e) {
            e.printStackTrace();
        }
    }

    private String getTimeserieName(Long virtualDeviceId){
        return config.getBaseDb()+ "." + Constants.VIRTUAL_DEVICE_PREFIX + ".v_"+virtualDeviceId;
    }
    @Override
    protected List<VirtualDeviceLog> mapToEntity(SessionDataSet dataSet, VirtualDeviceLog defaultEntity) throws StatementExecutionException, IoTDBConnectionException {
        List<VirtualDeviceLog> list = new ArrayList<>();
        Map<String, Integer> columnIndexMap = new HashMap<>();
        for (int i = 0; i < dataSet.getColumnNames().size(); i++) {
            String columnName = dataSet.getColumnNames().get(i);
            columnIndexMap.put(columnName, i - 1);//去除Time字段
        }
        Long virtualDeviceId = defaultEntity.getVirtualDeviceId();
        while (dataSet.hasNext()) {
            RowRecord rowRecord = dataSet.next();
            long timestamp = rowRecord.getTimestamp();
            String virtualDeviceName = tryGetStringValue(rowRecord,"virtual_device_name",columnIndexMap);
            Integer deviceTotal = tryGetIntV(rowRecord,"device_total",columnIndexMap);
            String result = tryGetStringValue(rowRecord,"result",columnIndexMap);
            VirtualDeviceLog one = new VirtualDeviceLog(timestamp, virtualDeviceId, virtualDeviceName, deviceTotal, result, timestamp);
            list.add(one);
        }
        return list;
    }
}
