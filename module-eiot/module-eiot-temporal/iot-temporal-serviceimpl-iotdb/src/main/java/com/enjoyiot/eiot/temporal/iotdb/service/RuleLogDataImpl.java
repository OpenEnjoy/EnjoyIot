package com.enjoyiot.eiot.temporal.iotdb.service;


import com.enjoyiot.eiot.IRuleLogData;
import com.enjoyiot.eiot.temporal.iotdb.config.Constants;
import com.enjoyiot.eiot.temporal.iotdb.config.IotdbDatasourceConfig;
import com.enjoyiot.eiot.temporal.iotdb.dao.IotdbBaseService;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.device.dto.DevicePropertyCache;
import com.enjoyiot.module.eiot.api.rule.dto.RuleLog;
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
public class RuleLogDataImpl extends IotdbBaseService<RuleLog> implements IRuleLogData {

    @Resource
    private IotdbDatasourceConfig config;


    @Override
    public void deleteByRuleId(Long ruleId) {
        String dbName = config.getBaseDb();
        String timeserieName = getTimeserieName(ruleId);
        try {
            deleteTimeseries(timeserieName);
        } catch (StatementExecutionException e) {
            e.printStackTrace();
        } catch (IoTDBConnectionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PageResult<RuleLog> findByRuleId(Long ruleId, int page, int size) {
        int offset = Integer.min(0, (page - 1) * size);
        String timeserieName = getTimeserieName(ruleId);

        String countSql = String.format("select count(%s) from %s", "state1", timeserieName);
        String sql = String.format("select state1 as state1,content as content,success as success from %s limit %d offset  %d ", timeserieName, size, offset);
        RuleLog args = new RuleLog();
        args.setRuleId(ruleId);
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
    public void add(RuleLog log) {
        String timeserieName = getTimeserieName(log.getRuleId());

        long time = System.currentTimeMillis();

        Map<String, DevicePropertyCache> properties = new HashMap<>();
        properties.put("state1", new DevicePropertyCache(log.getState(), time));
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

    private String getTimeserieName(Long ruleId){
        return config.getBaseDb()+ "." + Constants.RULE_PREFIX + ".r_"+ruleId;
    }

    @Override
    public List<RuleLog> mapToEntity(SessionDataSet dataSet, RuleLog defaultEntity) throws StatementExecutionException, IoTDBConnectionException {
        List<RuleLog> result = new ArrayList<>();
        Map<String, Integer> columnIndexMap = new HashMap<>();
        for (int i = 0; i < dataSet.getColumnNames().size(); i++) {
            String columnName = dataSet.getColumnNames().get(i);
            columnIndexMap.put(columnName, i - 1);//去除Time字段
        }
        Long ruleId = defaultEntity.getRuleId();
        while (dataSet.hasNext()) {
            RowRecord rowRecord = dataSet.next();
            long timestamp = rowRecord.getTimestamp();
            String state1 = tryGetStringValue(rowRecord, "state1", columnIndexMap);
            String content = tryGetStringValue(rowRecord, "content", columnIndexMap);
            Boolean success = tryGetBoolV(rowRecord, "success", columnIndexMap);
            RuleLog one = new RuleLog(timestamp, ruleId, state1, content, success, timestamp);
            result.add(one);
        }
        return result;
    }

}
