package com.enjoyiot.eiot.temporal.iotdb.service;

import com.enjoyiot.eiot.ITaskLogData;
import com.enjoyiot.eiot.temporal.iotdb.config.Constants;
import com.enjoyiot.eiot.temporal.iotdb.config.IotdbDatasourceConfig;
import com.enjoyiot.eiot.temporal.iotdb.dao.IotdbBaseService;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.device.dto.DevicePropertyCache;
import com.enjoyiot.module.eiot.api.task.dto.TaskLog;
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
public class TaskLogDataImpl extends IotdbBaseService<TaskLog> implements ITaskLogData {

    @Resource
    private IotdbDatasourceConfig config;


    @Override
    public void deleteByTaskId(Long taskId) {
        String timeserieName = getTimeserieName(taskId);
        try {
            deleteTimeseries(timeserieName);
        } catch (StatementExecutionException e) {
            e.printStackTrace();
        } catch (IoTDBConnectionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PageResult<TaskLog> findByTaskId(Long taskId, int page, int size) {
        int offset = Integer.min(0, (page - 1) * size);
        String timeserieName = getTimeserieName(taskId);

        String countSql = String.format("select count(%s) from %s", "success", timeserieName);
        String sql = String.format("select content as content,success as success from %s limit %d offset  %d ", timeserieName, size, offset);
        TaskLog args = new TaskLog();
        args.setTaskId(taskId);
        try {
            return queryPage(countSql, sql, args);
        } catch (StatementExecutionException e) {
            e.printStackTrace();
        } catch (IoTDBConnectionException e) {
            e.printStackTrace();
        }
        return new PageResult<>(new ArrayList<>(), 0L);
    }

    @Override
    public void add(TaskLog log) {

        String timeserieName = getTimeserieName(log.getTaskId());

        long time = System.currentTimeMillis();

        Map<String, DevicePropertyCache> properties = new HashMap<>();
        properties.put("content", new DevicePropertyCache(log.getContent(), time));
        properties.put("success", new DevicePropertyCache(log.getSuccess(), time));
        try {
            insertRecord(timeserieName, properties);
        } catch (StatementExecutionException e) {
            e.printStackTrace();
        } catch (IoTDBConnectionException e) {
            e.printStackTrace();
        }
    }

    private String getTimeserieName(Long taskId){
        return config.getBaseDb()+ "." + Constants.TASK_PREFIX + ".t_"+taskId;
    }

    @Override
    public List<TaskLog> mapToEntity(SessionDataSet dataSet, TaskLog defaultEntity) throws StatementExecutionException, IoTDBConnectionException {
        List<TaskLog> result = new ArrayList<>();
        Map<String, Integer> columnIndexMap = new HashMap<>();
        for (int i = 0; i < dataSet.getColumnNames().size(); i++) {
            String columnName = dataSet.getColumnNames().get(i);
            columnIndexMap.put(columnName, i - 1);//去除Time字段
        }
        Long taskId = defaultEntity.getTaskId();
        while (dataSet.hasNext()) {
            RowRecord rowRecord = dataSet.next();
            long timestamp = rowRecord.getTimestamp();
            String content = tryGetStringValue(rowRecord,"content",columnIndexMap);
            Boolean success =tryGetBoolV(rowRecord,"success",columnIndexMap);
            TaskLog one = new TaskLog(String.valueOf(timestamp), taskId, content, success, timestamp);
            result.add(one);
        }
        return result;
    }
}
