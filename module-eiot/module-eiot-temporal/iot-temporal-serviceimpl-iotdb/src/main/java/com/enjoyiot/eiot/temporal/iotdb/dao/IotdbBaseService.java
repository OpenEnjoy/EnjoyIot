package com.enjoyiot.eiot.temporal.iotdb.dao;

import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.device.dto.DevicePropertyCache;
import org.apache.iotdb.isession.SessionDataSet;
import org.apache.iotdb.isession.pool.SessionDataSetWrapper;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionPool;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.apache.iotdb.tsfile.read.common.RowRecord;
import org.apache.iotdb.tsfile.utils.Binary;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class IotdbBaseService<T> {

    @Resource
    protected SessionPool sessionPool;

    protected abstract List<T> mapToEntity(SessionDataSet dataSet, T defaultEntity) throws StatementExecutionException, IoTDBConnectionException;

    public void insertRecord(String timeseriesName, Map<String, DevicePropertyCache> properties) throws StatementExecutionException, IoTDBConnectionException {
        insertRecord(timeseriesName, properties, System.currentTimeMillis());
    }

    public void insertRecord(String timeseriesName, Map<String, DevicePropertyCache> properties, long time) throws StatementExecutionException, IoTDBConnectionException {
        List<String> valueNames = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        List<TSDataType> types = new ArrayList<>();

        properties.forEach((k, v) -> {
            valueNames.add(k);
            types.add(getType(v.getValue()));
            values.add(v.getValue());
        });
        sessionPool.insertRecord(timeseriesName, time, valueNames, types, values);
    }

    public void deleteTimeseries(String timeseriesName) throws StatementExecutionException, IoTDBConnectionException {
        sessionPool.executeNonQueryStatement("delete timeseries   "+ timeseriesName+".*");
    }

    public List<T> queryList(String sql, T defaultEntity) throws StatementExecutionException, IoTDBConnectionException {
        try (SessionDataSetWrapper wrapper = sessionPool.executeQueryStatement(sql);
             SessionDataSet dataSet = wrapper.getSessionDataSet()) {
            List<T> list = mapToEntity(dataSet, defaultEntity);
            return list;
        }
    }

    public PageResult<T> queryPage(String countSql, String sql, T defaultEntity) throws StatementExecutionException, IoTDBConnectionException {
        try (SessionDataSetWrapper countWrapper = sessionPool.executeQueryStatement(countSql);
             SessionDataSet countDataSet = countWrapper.getSessionDataSet()) {
            long count = 0L;
            if (countDataSet.hasNext()) {
                RowRecord rowRecord = countDataSet.next();
                count = rowRecord.getFields().get(0).getLongV();
            }
            if (count < 1) {
                return new PageResult<>(new ArrayList<>(), count);
            }

            try (SessionDataSetWrapper wrapper = sessionPool.executeQueryStatement(sql);
                 SessionDataSet dataSet = wrapper.getSessionDataSet()) {
                List<T> list = mapToEntity(dataSet, defaultEntity);
                return new PageResult<T>(list, count);
            }
        }
    }

    protected TSDataType getType(Object value) {
        if (value instanceof Double) {
            return TSDataType.DOUBLE;
        } else if (value instanceof Float) {
            return TSDataType.FLOAT;
        } else if (value instanceof Long) {
            return TSDataType.INT64;
        } else if (value instanceof Integer) {
            return TSDataType.INT32;
        } else if (value instanceof Boolean) {
            return TSDataType.BOOLEAN;
        } else if (value instanceof Binary) {
            return TSDataType.TEXT;
        } else {
            return TSDataType.TEXT;
        }
    }

    public String tryGetStringValue(RowRecord rowRecord,String key , Map<String, Integer> columnIndexMap){
        if (columnIndexMap.containsKey(key)){
            return rowRecord.getFields().get(columnIndexMap.get(key)).getStringValue();
        }
        return null;
    }

    public Boolean tryGetBoolV(RowRecord rowRecord,String key , Map<String, Integer> columnIndexMap){
        if (columnIndexMap.containsKey(key)){
            return rowRecord.getFields().get(columnIndexMap.get(key)).getBoolV();
        }
        return null;
    }

    public Double tryGetDoubleV(RowRecord rowRecord,String key , Map<String, Integer> columnIndexMap){
        if (columnIndexMap.containsKey(key)){
            return rowRecord.getFields().get(columnIndexMap.get(key)).getDoubleV();
        }
        return null;
    }

    public Float tryGetFloatV(RowRecord rowRecord,String key , Map<String, Integer> columnIndexMap){
        if (columnIndexMap.containsKey(key)){
            return rowRecord.getFields().get(columnIndexMap.get(key)).getFloatV();
        }
        return null;
    }

    public Integer tryGetIntV(RowRecord rowRecord,String key , Map<String, Integer> columnIndexMap){
        if (columnIndexMap.containsKey(key)){
            return rowRecord.getFields().get(columnIndexMap.get(key)).getIntV();
        }
        return null;
    }

    public Long tryGetLongV(RowRecord rowRecord,String key , Map<String, Integer> columnIndexMap){
        if (columnIndexMap.containsKey(key)){
            return rowRecord.getFields().get(columnIndexMap.get(key)).getLongV();
        }
        return null;
    }

}
