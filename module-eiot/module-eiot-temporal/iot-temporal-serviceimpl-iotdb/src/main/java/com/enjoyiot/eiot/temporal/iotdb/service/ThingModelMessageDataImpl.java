package com.enjoyiot.eiot.temporal.iotdb.service;


import com.alibaba.fastjson.JSON;
import com.enjoyiot.eiot.IThingModelMessageData;
import com.enjoyiot.eiot.TimeData;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.temporal.iotdb.config.Constants;
import com.enjoyiot.eiot.temporal.iotdb.config.IotdbDatasourceConfig;
import com.enjoyiot.eiot.temporal.iotdb.dao.IotdbBaseService;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.device.dto.DevicePropertyCache;
import org.apache.commons.lang3.StringUtils;
import org.apache.iotdb.isession.SessionDataSet;
import org.apache.iotdb.isession.pool.SessionDataSetWrapper;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.tsfile.read.common.RowRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ThingModelMessageDataImpl extends IotdbBaseService<ThingModelMessage> implements IThingModelMessageData {

    @Resource
    private DeviceApi deviceApi;
    @Resource
    private IotdbDatasourceConfig config;

    private Set<String> pathExistInTemplateSet = new HashSet<>();

    /**
     * 按消息类型和标识符取设备消息
     *
     * @param deviceId   设备id
     * @param type       消息类型
     * @param identifier 标识符
     * @param page       页码
     * @param size       页大小
     */
    @Override
    public PageResult<ThingModelMessage> findByTypeAndIdentifier(Long deviceId, String type, String identifier, int page, int size) {
        DeviceInfo device = deviceApi.getDeviceInfoFromCache(deviceId);
        if (device == null) {
            return new PageResult<>(new ArrayList<>(), 0L);
        }
        int offset = Integer.min(0, (page - 1) * size);
        String timeserieNames = config.getBaseDb()+"."+Constants.THING_MODEL_MESSAGE_PREFIX + ".d_" + device.getDn();;
        String whereSql = "";
        List<String> conditionList = new ArrayList<>();
        if (StringUtils.isNotBlank(type)) {
            conditionList.add(" type = '" + type + "' ");
        }
        if (StringUtils.isNotBlank(identifier)) {
            conditionList.add(" identifier = '" + identifier + "' ");
        }
        if (!conditionList.isEmpty()) {
            whereSql = "where " + String.join(" and ", conditionList);
        }
        String countSql = String.format("select count(%s) from %s %s", "report_time", timeserieNames, whereSql);
        String sql = String.format("select `mid` as mid,`product_key` as `product_key`,`device_name` as `device_name`,`uid` as `uid`,`type` as `type`,`identifier` as `identifier`,`code` as `code`,`data` as `data`,`report_time` as `report_time` from %s %s limit %d offset  %d ", timeserieNames, whereSql, size, offset);
        ThingModelMessage args = new ThingModelMessage();
        args.setDeviceId(deviceId);
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
     * 按用户统计时间段内每小时上报次数
     *
     * @param uid   用户id
     * @param start 开始时间戳
     * @param end   结束时间戳
     */
    @Override
    public List<TimeData> getDeviceMessageStatsWithUid(String uid, long start, long end) {
        //todo 未使用 暂不实现
        return null;
    }

    /**
     * 按用户统计时间段内上行消息
     *
     * @param uid   用户id
     * @param start 开始时间戳
     * @param end   结束时间戳
     */
    @Override
    public List<TimeData> getDeviceUpMessageStatsWithUid(String uid, Long start, Long end) {
        //todo 未使用 暂不实现
        return null;
    }

    /**
     * 按用户统计时间段内下行
     *
     * @param uid   用户id
     * @param start 开始时间戳
     * @param end   结束时间戳
     */
    @Override
    public List<TimeData> getDeviceDownMessageStatsWithUid(String uid, Long start, Long end) {
        //todo 未使用 暂不实现
        return null;
    }

    /**
     * 查询指定设备集类型并按时间倒序
     *
     * @param deviceIds  设备ids
     * @param type       消息类型
     * @param identifier 标识符
     * @param page       页码
     * @param size       页大小
     */
    @Override
    public PageResult<ThingModelMessage> findByTypeAndDeviceIds(List<Long> deviceIds, String type, String identifier, int page, int size) {
        Map<String, Long> deviceDnIdMap = new HashMap<>();
        for (Long deviceId : deviceIds) {
            DeviceInfo device = deviceApi.getDeviceInfoFromCache(deviceId);
            if (device == null) {
                deviceDnIdMap.put(device.getDn(), deviceId);
            }
        }
        if (deviceDnIdMap.isEmpty()) {
            return new PageResult<>(new ArrayList<>(), 0L);
        }
        int offset = Integer.min(0, (page - 1) * size);
        String dbName = config.getBaseDb();
        String timeserieNames = deviceDnIdMap.keySet().stream()
                .map(dn -> dbName + "." + Constants.THING_MODEL_MESSAGE_PREFIX + ".d_" + dn)
                .collect(Collectors.joining(","));

        String whereSql = "";
        List<String> conditionList = new ArrayList<>();
        if (StringUtils.isNotBlank(type)) {
            conditionList.add(" type = '" + type + "' ");
        }
        if (StringUtils.isNotBlank(identifier)) {
            conditionList.add(" identifier = '" + identifier + "' ");
        }
        if (!conditionList.isEmpty()) {
            whereSql = "where " + String.join(" and ", conditionList);
        }
        String countSql = String.format("select count(%s) from %s %s", "report_time", timeserieNames, whereSql);
        String sql = String.format("select `mid` as mid,`product_key` as `product_key`,`device_name` as `device_name`,`uid` as `uid`,`type` as `type`,`identifier` as `identifier`,`code` as `code`,`data` as `data`,`report_time` as `report_time` from %s %s order by time desc limit %d offset  %d ", timeserieNames, whereSql, size, offset);
        ThingModelMessage args = new ThingModelMessage();
        args.setDeviceId(-1L);
        try {
            PageResult<ThingModelMessage> pageResult = queryPage(countSql, sql, args);
            for (ThingModelMessage thingModelMessage : pageResult.getList()) {
                thingModelMessage.setDeviceId(deviceDnIdMap.get(thingModelMessage.getDn()));
            }
            return pageResult;
        } catch (StatementExecutionException e) {
            e.printStackTrace();
        } catch (IoTDBConnectionException e) {
            e.printStackTrace();
        }
        return new PageResult<>(new ArrayList<>(), 0L);
    }

    @Override
    public void add(ThingModelMessage msg) {
        String dbName = config.getBaseDb();
        String timeserieName = dbName + "." + Constants.THING_MODEL_MESSAGE_PREFIX + ".d_" + msg.getDn();

        try {
            String templateName = Constants.THING_MODEL_MESSAGE_PREFIX;
            if (!isPathExistInTemplate(templateName, timeserieName)) {
                String createSql = "create timeseries using device template on " + timeserieName;
                sessionPool.executeNonQueryStatement(createSql);
                pathExistInTemplateSet.add(timeserieName);
            }

            long time = System.currentTimeMillis();
            Map<String, DevicePropertyCache> properties = new HashMap<>();
            properties.put("mid", new DevicePropertyCache(msg.getMid(), time));
            properties.put("product_key", new DevicePropertyCache(msg.getProductKey(), time));
            properties.put("device_name", new DevicePropertyCache(msg.getDn(), time));
            properties.put("uid", new DevicePropertyCache(msg.getUid(), time));
            properties.put("type", new DevicePropertyCache(msg.getType(), time));
            properties.put("identifier", new DevicePropertyCache(msg.getIdentifier(), time));
            properties.put("code", new DevicePropertyCache(msg.getCode(), time));
            properties.put("data", new DevicePropertyCache(msg.getData() == null ? "{}" : JsonUtils.toJsonString(msg.getData()), time));
            properties.put("report_time", new DevicePropertyCache(msg.getTime(), time));
            insertRecord(timeserieName, properties, msg.getOccurred());
        } catch (StatementExecutionException e) {
            e.printStackTrace();
        } catch (IoTDBConnectionException e) {
            e.printStackTrace();
        }
    }

    private boolean isPathExistInTemplate(String templateName, String path) throws StatementExecutionException, IoTDBConnectionException {
        if (!pathExistInTemplateSet.contains(path)) {
            List<String> nameList = sessionPool.showPathsTemplateUsingOn(templateName);
            pathExistInTemplateSet = new HashSet<>(nameList);
        }
        return pathExistInTemplateSet.contains(path);
    }

    @Override
    public long count() {
        String dbName = config.getBaseDb();
        String timeserieName = dbName + "." + Constants.THING_MODEL_MESSAGE_PREFIX + ".*";
        String sql = String.format("select count(status) as `count` from %s   align by device", timeserieName);
        try (SessionDataSetWrapper wrapper = sessionPool.executeQueryStatement(sql);
             SessionDataSet dataSet = wrapper.getSessionDataSet()) {
            Map<String, Integer> columnIndexMap = new HashMap<>();
            for (int i = 0; i < dataSet.getColumnNames().size(); i++) {
                String columnName = dataSet.getColumnNames().get(i);
                columnIndexMap.put(columnName, i - 1);
            }
            long count = 0L;
            if (dataSet.hasNext()) {
                RowRecord rowRecord = dataSet.next();
                Long one = tryGetLongV(rowRecord, "count", columnIndexMap);
                if (one != null) {
                    count = count + one;
                }
            }
            return count;
        } catch (StatementExecutionException e) {
            e.printStackTrace();
        } catch (IoTDBConnectionException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    @Override
    protected List<ThingModelMessage> mapToEntity(SessionDataSet dataSet, ThingModelMessage defaultEntity) throws StatementExecutionException, IoTDBConnectionException {
        List<ThingModelMessage> result = new ArrayList<>();
        Map<String, Integer> columnIndexMap = new HashMap<>();
        for (int i = 0; i < dataSet.getColumnNames().size(); i++) {
            String columnName = dataSet.getColumnNames().get(i);
            columnIndexMap.put(columnName, i - 1);
        }
        Long deviceId = defaultEntity.getDeviceId();
        while (dataSet.hasNext()) {
            RowRecord rowRecord = dataSet.next();
            long timestamp = rowRecord.getTimestamp();
            String mid = tryGetStringValue(rowRecord, "mid", columnIndexMap);
            String product_key = tryGetStringValue(rowRecord, "product_key", columnIndexMap);
            String device_name = tryGetStringValue(rowRecord, "device_name", columnIndexMap);
            String uid = tryGetStringValue(rowRecord, "uid", columnIndexMap);
            String type = tryGetStringValue(rowRecord, "type", columnIndexMap);
            String identifier = tryGetStringValue(rowRecord, "identifier", columnIndexMap);
            int code = tryGetIntV(rowRecord, "code", columnIndexMap);
            String data = tryGetStringValue(rowRecord, "data", columnIndexMap);
            Long report_time = tryGetLongV(rowRecord, "report_time", columnIndexMap);
            ThingModelMessage one = new ThingModelMessage();
            one.setId(String.valueOf(timestamp));
            one.setMid(mid);
            one.setDeviceId(deviceId);
            one.setProductKey(product_key);
            one.setDn(device_name);
            one.setUid(uid);
            one.setType(type);
            one.setIdentifier(identifier);
            one.setCode(code);
            one.setData(data == null?null:JSON.parseObject(data, Map.class));
            one.setOccurred(timestamp);
            one.setTime(report_time);
            one.setToClient(null);
            result.add(one);
        }
        return result;
    }
}
