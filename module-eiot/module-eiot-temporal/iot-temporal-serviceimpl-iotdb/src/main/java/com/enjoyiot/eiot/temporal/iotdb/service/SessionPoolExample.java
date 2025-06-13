//package com.enjoyiot.eiot.temporal.iotdb.service;
//
//import com.enjoyiot.eiot.temporal.iotdb.config.IotdbTemplate;
//import com.enjoyiot.framework.common.pojo.PageResult;
//import com.enjoyiot.module.eiot.api.device.dto.DeviceProperty;
//import com.enjoyiot.module.eiot.api.device.dto.DevicePropertyCache;
//import com.enjoyiot.module.eiot.api.rule.dto.RuleLog;
//import com.zaxxer.hikari.HikariDataSource;
//import org.apache.iotdb.isession.SessionDataSet;
//import org.apache.iotdb.isession.pool.SessionDataSetWrapper;
//import org.apache.iotdb.rpc.IoTDBConnectionException;
//import org.apache.iotdb.rpc.StatementExecutionException;
//import org.apache.iotdb.session.pool.SessionPool;
//import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
//import org.apache.iotdb.tsfile.read.common.Field;
//import org.apache.iotdb.tsfile.read.common.RowRecord;
//import org.apache.iotdb.tsfile.utils.Binary;
//
//import java.util.*;
//import java.util.function.BiFunction;
//
//
//public class SessionPoolExample {
//    private static SessionPool sessionPool;
//    private static IotdbTemplate iotdbTemplate;
//
//    public static void main(String[] args) throws IoTDBConnectionException, StatementExecutionException {
//        // 1. init SessionPool
//        constructSessionPool();
//        // 2. executes a non-query SQL statement, such as a DDL or DML command.
//        isPathExistInTemplate();
////        executeQueryExample();
////        insertRuleLog();
////        queryRuleLog();
////        oneValueBetweenTime();
////        insertRecordsExample();
//        // 3. executes a query SQL statement and returns the result set.
////        executeNonQueryExample();
//        // 4. close SessionPool
////        closeSessionPool();
//
//    }
//
//    public static void isPathExistInTemplate() throws IoTDBConnectionException, StatementExecutionException {
//        List<String> strings = sessionPool.showPathsTemplateUsingOn("t1");
//        System.out.println(strings);
//    }
//
//    private static void constructSessionPool() {
//        sessionPool =
//                new SessionPool.Builder()
//                        .nodeUrls(Collections.singletonList("47.104.95.120:16667"))
//                        .user("root")
//                        .password("hongli@87097988")
//                        .maxSize(3)
//                        .build();
//        System.out.println(sessionPool.getVersion());
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl("jdbc:iotdb://47.104.95.120:16667/");
//        dataSource.setUsername("root");
//        dataSource.setPassword("hongli@87097988");
//        dataSource.setDriverClassName("org.apache.iotdb.jdbc.IoTDBDriver");
//        iotdbTemplate = new IotdbTemplate(dataSource);
//    }
//
//    private static final String RULE_PREFIX = "rule_log_";
//
//    public static void insertRuleLog() throws IoTDBConnectionException, StatementExecutionException {
//        RuleLog log = new RuleLog(12345L, 10001L, "01", "content", true, 12345L);
//        String dn = RULE_PREFIX + log.getRuleId();
//        String dbName = "root.hl";
//        List<String> valueNames = new ArrayList<>();
//        List<Object> values = new ArrayList<>();
//        List<TSDataType> types = new ArrayList<>();
//
//        long time = System.currentTimeMillis();
//
//        Map<String, DevicePropertyCache> properties = new HashMap<>();
//        properties.put("state1", new DevicePropertyCache(log.getState(), time));
//        properties.put("content", new DevicePropertyCache(log.getContent(), time));
//        properties.put("success", new DevicePropertyCache(log.getSuccess(), time));
//        properties.forEach((k, v) -> {
//            valueNames.add(k);
//            types.add(getType(v.getValue()));
//            values.add(v.getValue());
//        });
//        try {
//            sessionPool.insertRecord(dbName + "." + dn, time, valueNames, types, values);
//        } catch (IoTDBConnectionException e) {
//            e.printStackTrace();
//        } catch (StatementExecutionException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void queryRuleLog() throws IoTDBConnectionException, StatementExecutionException {
//
//        int page=1;
//        int size =10;
//
//        Long ruleId = 10001L;
//        int offset = Integer.min(0, (page - 1) * size);
//        String dn = RULE_PREFIX + ruleId;
//        String dbName = "root.hl";
//
//        String countSql = String.format("select count(%s) from %s", "state1", dbName + "." + dn);
//        String sql = String.format("select state1,content,success from %s limit %d offset  %d ", dbName + "." + dn, size, offset);
//        try (SessionDataSetWrapper wrapper = sessionPool.executeQueryStatement(sql);
//             SessionDataSet dataSet = wrapper.getSessionDataSet()) {
//            Map<String, Object> args = new HashMap<>();
//            args.put("ruleId", ruleId);
//            List<RuleLog> ruleLogs = mapToRuleLog(dataSet, args);
//            for (RuleLog ruleLog : ruleLogs) {
//                System.out.println(ruleLog);
//            }
//        } catch (StatementExecutionException e) {
//            e.printStackTrace();
//        } catch (IoTDBConnectionException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void insertRecordsExample() throws IoTDBConnectionException, StatementExecutionException {
//        long time = 123455L;
//        Map<String, DevicePropertyCache> properties = new HashMap<>();
//        properties.put("string1", new DevicePropertyCache("stringValue", time));
//        properties.put("int1", new DevicePropertyCache(1, time));
//        properties.put("long1", new DevicePropertyCache(100L, time));
//        properties.put("boolean", new DevicePropertyCache(true, time));
//        properties.put("double", new DevicePropertyCache(1.21d, time));
//        String dn = "d1";
//        String dbName = "root.hl";
//        List<String> valueNames = new ArrayList<>();
//        List<Object> values = new ArrayList<>();
//        List<TSDataType> types = new ArrayList<>();
//        properties.forEach((k, v) -> {
//            valueNames.add(k);
//            types.add(getType(v.getValue()));
//            values.add(v.getValue());
//        });
//        try {
//            sessionPool.insertRecord(dbName + "." + dn, time, valueNames, types, values);
//        } catch (IoTDBConnectionException e) {
//            e.printStackTrace();
//        } catch (StatementExecutionException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void executeQueryExample() throws IoTDBConnectionException, StatementExecutionException {
//
//        String name = "string1";
//        String dn = "d1";
//        String point = dn + "." + name;
//        String dbName = "root.hl";
//        long start = 1L;
//        long end = 1000000L;
//        int size = 10;
//
//        List<String> query = new ArrayList<>();
//        query.add(dbName + "." + point);
//        try (SessionDataSetWrapper wrapper = sessionPool.executeRawDataQuery(query, start, end, 1000L);
//             SessionDataSet dataSet = wrapper.getSessionDataSet()) {
//
////            List<DeviceProperty> deviceProperties = DevicePropertyMapper.mapToList(dataSet,dn,name);
////            for (DeviceProperty deviceProperty : deviceProperties) {
////                System.out.println(deviceProperty);
////            }
//            for (String columnName : dataSet.getColumnNames()) {
//                System.out.print(columnName + "\t");
//            }
//            System.out.println();
//            while (dataSet.hasNext()) {
//                System.out.println(dataSet.next());
//            }
//        }
////        try(SessionDataSetWrapper wrapper = sessionPool.executeQueryStatement("show databases root.hl");
////            SessionDataSet dataSet = wrapper.getSessionDataSet()) {
////            if (!dataSet.hasNext()) {
////                sessionPool.createDatabase("root.hl");
////            }
////        }
//
//        // 1. execute normal query
////        try(SessionDataSetWrapper wrapper = sessionPool.executeQueryStatement("select * from root.**")) {
////            SessionDataSet dataSet =wrapper.getSessionDataSet();
////            while (wrapper.hasNext()) {
////                System.out.println(wrapper.next());
////            }
////        }
//        // 2. execute aggregate query
////        try(SessionDataSetWrapper wrapper = sessionPool.executeQueryStatement("select count(s1) from root.sg1.d1 group by ([0, 40), 5ms) ")) {
////            while (wrapper.hasNext()) {
////                System.out.println(wrapper.next());
////            }
////        }
//    }
//
//    private static void oneValueBetweenTime() throws IoTDBConnectionException, StatementExecutionException {
//
//        String name = "string1";
//        String dn = "d1";
//        String point = dn + "." + name;
//        String dbName = "root.hl";
//        long start = 1L;
//        long end = 1000000L;
//        int size = 10;
//
//        String sql = String.format("select %s from %s where time > %d and time< %d limit %d", point, dbName, start, end, size);
//        try (SessionDataSetWrapper wrapper = sessionPool.executeQueryStatement(sql);
//             SessionDataSet dataSet = wrapper.getSessionDataSet()) {
//            Map<String, Object> args = new HashMap<>();
//            args.put("deviceId", dn);
//            args.put("valueName", name);
//            List<DeviceProperty> deviceProperties = mapToDeviceProperty(dataSet, args);
//
//            for (DeviceProperty deviceProperty : deviceProperties) {
//                System.out.println(deviceProperty);
//            }
//        } catch (StatementExecutionException e) {
//            e.printStackTrace();
//        } catch (IoTDBConnectionException e) {
//            e.printStackTrace();
//        }
//        return;
//
//    }
//
//    private static void executeNonQueryExample() throws IoTDBConnectionException, StatementExecutionException {
//        // 1. create a nonAligned time series
//        sessionPool.executeNonQueryStatement("create timeseries root.test.d1.s1 with dataType = int32");
//        // 2. set ttl
//        sessionPool.executeNonQueryStatement("set TTL to root.test.** 10000");
//        // 3. delete time series
//        sessionPool.executeNonQueryStatement("delete timeseries root.test.d1.s1");
//
//    }
//
//    public static void closeSessionPool() {
//        sessionPool.close();
//    }
//
//    private static  <T> List<T> queryList(String sql, BiFunction<SessionDataSet, Map<String, Object>, List<T>> mapper, Map<String, Object> args) {
//        try (SessionDataSetWrapper wrapper = sessionPool.executeQueryStatement(sql);
//             SessionDataSet dataSet = wrapper.getSessionDataSet()) {
//            List<T> list = mapper.apply(dataSet, args);
//            return list;
//        } catch (StatementExecutionException e) {
//            e.printStackTrace();
//        } catch (IoTDBConnectionException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private static  <T> PageResult<T> queryPage(String countSql, String sql, BiFunction<SessionDataSet, Map<String, Object>, List<T>> mapper, Map<String, Object> args) {
//
//        try (SessionDataSetWrapper countWrapper = sessionPool.executeQueryStatement(countSql);
//             SessionDataSet countDataSet = countWrapper.getSessionDataSet()) {
//            long count = 0L;
//            if (countDataSet.hasNext()) {
//                RowRecord rowRecord = countDataSet.next();
//                count = rowRecord.getFields().get(0).getLongV();
//            }
//            if (count < 1) {
//                return new PageResult<>(new ArrayList<>(), count);
//            }
//
//            try (SessionDataSetWrapper wrapper = sessionPool.executeQueryStatement(sql);
//                 SessionDataSet dataSet = wrapper.getSessionDataSet()) {
//                List<T> list = mapper.apply(dataSet, args);
//                return new PageResult<T>(list, count);
//            }
//        } catch (StatementExecutionException e) {
//            e.printStackTrace();
//        } catch (IoTDBConnectionException e) {
//            e.printStackTrace();
//        }
//        return new PageResult<>(new ArrayList<>(), 0L);
//    }
//
//    public static TSDataType getType(Object value) {
//        if (value instanceof Double) {
//            return TSDataType.DOUBLE;
//        } else if (value instanceof Float) {
//            return TSDataType.FLOAT;
//        } else if (value instanceof Long) {
//            return TSDataType.INT64;
//        } else if (value instanceof Integer) {
//            return TSDataType.INT32;
//        } else if (value instanceof Boolean) {
//            return TSDataType.BOOLEAN;
//        } else if (value instanceof Binary) {
//            return TSDataType.TEXT;
//        } else {
//            return TSDataType.TEXT;
//        }
//    }
//
//    public static List<DeviceProperty> mapToDeviceProperty(SessionDataSet dataSet, Map args) throws StatementExecutionException, IoTDBConnectionException {
//        List<DeviceProperty> result = new ArrayList<>();
//        String deviceId = (String) args.get("deviceId");
//        String valueName = (String) args.get("valueName");
//        while (dataSet.hasNext()) {
//            RowRecord rowRecord = dataSet.next();
//            long timestamp = rowRecord.getTimestamp();
//            Field valueField = rowRecord.getFields().get(0);
//            Object value = valueField.getObjectValue(valueField.getDataType());
//            DeviceProperty one = new DeviceProperty(String.valueOf(timestamp), deviceId, valueName, value, timestamp);
//            result.add(one);
//        }
//        return result;
//    }
//
//    public static List<RuleLog> mapToRuleLog(SessionDataSet dataSet, Map args) throws StatementExecutionException, IoTDBConnectionException {
//        List<RuleLog> result = new ArrayList<>();
//        Long ruleId = (Long) args.get("ruleId");
//        while (dataSet.hasNext()) {
//            RowRecord rowRecord = dataSet.next();
//            long timestamp = rowRecord.getTimestamp();
//            String state1 = rowRecord.getFields().get(0).getStringValue();
//            String content = rowRecord.getFields().get(1).getStringValue();
//            Boolean success = rowRecord.getFields().get(2).getBoolV();
//            RuleLog one = new RuleLog(timestamp, ruleId, state1, content, success, timestamp);
//            result.add(one);
//        }
//        return result;
//    }
//}