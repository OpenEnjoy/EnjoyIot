/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */
package com.enjoyiot.eiot.temporal.rdb.service;


import com.enjoyiot.eiot.IThingModelMessageData;
import com.enjoyiot.eiot.TimeData;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.temporal.rdb.dao.RdbTemplate;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ThingModelMessageDataImpl implements IThingModelMessageData {

    @Autowired
    private RdbTemplate rdbTemplate;

    @Override
    public PageResult<ThingModelMessage> findByTypeAndIdentifier(Long deviceId, String type,
                                                                 String identifier,
                                                                 int page, int size) {
        StringBuilder sql = new StringBuilder("select time,mid,product_key,device_name,device_id,uid,type,identifier,code,data,report_time ");
        sql.append("from thing_model_message where device_id=? ");
        List<Object> args = new ArrayList<>();
        args.add(deviceId);

        if (StringUtils.isNotBlank(type)) {
            sql.append(" and type=? ");
            args.add(type);
        }
        if (StringUtils.isNotBlank(identifier)) {
            sql.append(" and identifier=? ");
            args.add(identifier);
        }
        sql.append(" order by time desc limit ? offset ?");
        args.add(size);
        args.add((page - 1) * size);

        List<Map<String, Object>> rows = rdbTemplate.queryForList(sql.toString(), args.toArray());
        List<ThingModelMessage> messages = rows.stream().map(r -> mapToMessage(r)).collect(java.util.stream.Collectors.toList());

        sql = new StringBuilder("select count(*) from thing_model_message where device_id=? ");
        args = new ArrayList<>();
        args.add(deviceId);
        if (StringUtils.isNotBlank(type)) {
            sql.append(" and type=? ");
            args.add(type);
        }
        if (StringUtils.isNotBlank(identifier)) {
            sql.append(" and identifier=? ");
            args.add(identifier);
        }
        Long count = rdbTemplate.queryForObject(sql.toString(), Long.class, args.toArray());

        return new PageResult<>(messages, count != null ? count : 0L);
    }

    @Override
    public PageResult<ThingModelMessage> findByTypeAndDeviceIds(List<Long> deviceIds, String type,
                                                                String identifier,
                                                                int page, int size) {
        if (deviceIds == null || deviceIds.isEmpty()) {
            return new PageResult<>(new ArrayList<>(), 0L);
        }

        StringBuilder sql = new StringBuilder("select time,mid,product_key,device_name,device_id,uid,type,identifier,code,data,report_time ");
        sql.append("from thing_model_message where device_id in (");
        List<Object> args = new ArrayList<>();
        for (int i = 0; i < deviceIds.size(); i++) {
            sql.append(i > 0 ? ",?" : "?");
            args.add(deviceIds.get(i));
        }
        sql.append(") ");

        if (StringUtils.isNotBlank(type)) {
            sql.append(" and type=? ");
            args.add(type);
        }
        if (StringUtils.isNotBlank(identifier)) {
            sql.append(" and identifier=? ");
            args.add(identifier);
        }
        sql.append(" order by time desc limit ? offset ?");
        args.add(size);
        args.add((page - 1) * size);

        List<Map<String, Object>> rows = rdbTemplate.queryForList(sql.toString(), args.toArray());
        List<ThingModelMessage> messages = rows.stream().map(r -> mapToMessage(r)).collect(java.util.stream.Collectors.toList());

        sql = new StringBuilder("select count(*) from thing_model_message where device_id in (");
        args = new ArrayList<>();
        for (int i = 0; i < deviceIds.size(); i++) {
            sql.append(i > 0 ? ",?" : "?");
            args.add(deviceIds.get(i));
        }
        sql.append(") ");
        if (StringUtils.isNotBlank(type)) {
            sql.append(" and type=? ");
            args.add(type);
        }
        if (StringUtils.isNotBlank(identifier)) {
            sql.append(" and identifier=? ");
            args.add(identifier);
        }
        Long count = rdbTemplate.queryForObject(sql.toString(), Long.class, args.toArray());

        return new PageResult<>(messages, count != null ? count : 0L);
    }

    private ThingModelMessage mapToMessage(Map<String, Object> r) {
        Long time = ((Number) r.get("time")).longValue();
        Long deviceId = ((Number) r.get("device_id")).longValue();
        String dataStr = (String) r.get("data");
        Map<String, Object> data = null;
        if (StringUtils.isNotBlank(dataStr)) {
            try {
                data = JsonUtils.parseObject(dataStr, Map.class);
            } catch (Exception e) {
                log.warn("解析消息数据失败: {}", dataStr);
            }
        }

        return new ThingModelMessage(
                String.valueOf(time),
                (String) r.get("mid"),
                deviceId,
                (String) r.get("product_key"),
                (String) r.get("device_name"),
                (String) r.get("uid"),
                (String) r.get("type"),
                (String) r.get("identifier"),
                r.get("code") != null ? ((Number) r.get("code")).intValue() : null,
                data,
                time,
                r.get("report_time") != null ? ((Number) r.get("report_time")).longValue() : null,
                null
        );
    }

    @Override
    public List<TimeData> getDeviceMessageStatsWithUid(String uid, long start, long end) {
        String sql = "select (time - time % 3600000) as time, count(*) as data " +
                "from thing_model_message where time>=? and time<=? ";
        List<Object> args = new ArrayList<>();
        args.add(start);
        args.add(end);

        if (uid != null && !uid.isEmpty()) {
            sql += " and uid=? ";
            args.add(uid);
        }
        sql += " group by time order by time asc";

        List<Map<String, Object>> rows = rdbTemplate.queryForList(sql, args.toArray());
        return rows.stream().map(r -> new TimeData(
                ((Number) r.get("time")).longValue(),
                ((Number) r.get("data")).longValue()
        )).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<TimeData> getDeviceUpMessageStatsWithUid(String uid, Long start, Long end) {
        String sql = "select (time - time % 3600000) as time, count(*) as data " +
                "from thing_model_message where ((type='property' and identifier='report') or type='event') ";
        List<Object> args = new ArrayList<>();

        if (uid != null && !uid.isEmpty()) {
            sql += " and uid=? ";
            args.add(uid);
        }
        if (start != null && end != null) {
            sql += " and time>=? and time<=? ";
            args.add(start);
            args.add(end);
        }
        sql += " group by time order by time asc";

        List<Map<String, Object>> rows = rdbTemplate.queryForList(sql, args.toArray());
        return rows.stream().map(r -> new TimeData(
                ((Number) r.get("time")).longValue(),
                ((Number) r.get("data")).longValue()
        )).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<TimeData> getDeviceDownMessageStatsWithUid(String uid, Long start, Long end) {
        String sql = "select (time - time % 3600000) as time, count(*) as data " +
                "from thing_model_message where ((type='property' and identifier!='report') or type='service' or type='config') ";
        List<Object> args = new ArrayList<>();

        if (uid != null && !uid.isEmpty()) {
            sql += " and uid=? ";
            args.add(uid);
        }
        if (start != null && end != null) {
            sql += " and time>=? and time<=? ";
            args.add(start);
            args.add(end);
        }
        sql += " group by time order by time asc";

        List<Map<String, Object>> rows = rdbTemplate.queryForList(sql, args.toArray());
        return rows.stream().map(r -> new TimeData(
                ((Number) r.get("time")).longValue(),
                ((Number) r.get("data")).longValue()
        )).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public void add(ThingModelMessage msg) {
        String sql = "INSERT INTO thing_model_message (time,mid,product_key,device_name,device_id,uid,type,identifier,code,data,report_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        rdbTemplate.update(sql, msg.getOccurred(), msg.getMid(),
                msg.getProductKey(), msg.getDn(), msg.getDeviceId(),
                msg.getUid(), msg.getType(),
                msg.getIdentifier(), msg.getCode(),
                msg.getData() == null ? "{}" : JsonUtils.toJsonString(msg.getData()),
                msg.getTime());
    }

    @Override
    public long count() {
        Long count = rdbTemplate.queryForObject("select count(*) from thing_model_message", Long.class);
        return count != null ? count : 0L;
    }
}
