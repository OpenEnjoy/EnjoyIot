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
package com.enjoyiot.eiot.temporal.timescaledb.service;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.enjoyiot.eiot.IThingModelMessageData;
import com.enjoyiot.eiot.TimeData;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.temporal.timescaledb.dao.PgTemplate;
import com.enjoyiot.eiot.temporal.timescaledb.dao.PgThingModelMessageMapper;
import com.enjoyiot.eiot.temporal.timescaledb.model.PgThingModelMessage;
import com.enjoyiot.framework.common.pojo.PageParam;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.postgresql.util.PGTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ThingModelMessageDataImpl implements IThingModelMessageData {

    @Autowired
    private PgTemplate pgTemplate;

    @Autowired
    private PgThingModelMessageMapper thingModelMessageMapper;

    @Override
    public PageResult<ThingModelMessage> findByTypeAndIdentifier(Long deviceId, String type,
                                                                 String identifier,
                                                                 int page, int size) {
        PageParam pageParam = new PageParam();
        pageParam.setPageNo(page);
        pageParam.setPageSize(size);

        PageResult<PgThingModelMessage> result = thingModelMessageMapper.selectPage(pageParam,
                Wrappers.lambdaQuery(PgThingModelMessage.class)
                        .eq(PgThingModelMessage::getDeviceId, deviceId)
                        .eq(StringUtils.isNotBlank(type), PgThingModelMessage::getType, type)
                        .eq(StringUtils.isNotBlank(identifier), PgThingModelMessage::getIdentifier, identifier)
                        .orderByDesc(PgThingModelMessage::getTime)
        );

        return new PageResult<>(result.getList().stream().map(r ->
                        new ThingModelMessage(r.getTime().toString(), r.getMid(),
                                deviceId, r.getProductKey(), r.getDeviceName(),
                                r.getUid(), r.getType(), r.getIdentifier(), r.getCode(),
                                JsonUtils.parseObject(r.getData(), Map.class),
                                r.getTime().getTime(), r.getReportTime(), null))
                .collect(Collectors.toList()), result.getTotal());
    }

    @Override
    public PageResult<ThingModelMessage> findByTypeAndDeviceIds(List<Long> deviceIds, String type,
                                                                String identifier,
                                                                int page, int size) {

        PageParam pageParam = new PageParam();
        pageParam.setPageNo(page);
        pageParam.setPageSize(size);
        PageResult<PgThingModelMessage> result = thingModelMessageMapper.selectPage(pageParam,
                Wrappers.lambdaQuery(PgThingModelMessage.class)
                        .eq(PgThingModelMessage::getType, type)
                        .in(!deviceIds.isEmpty(), PgThingModelMessage::getDeviceId, deviceIds)
                        .eq(StringUtils.isNotBlank(identifier), PgThingModelMessage::getIdentifier, identifier)
                        .orderByDesc(PgThingModelMessage::getTime)
        );

        return new PageResult<>(result.getList().stream().map(r ->
                        new ThingModelMessage(r.getTime().toString(), r.getMid(),
                                r.getDeviceId(), r.getProductKey(), r.getDeviceName(),
                                r.getUid(), r.getType(), r.getIdentifier(), r.getCode(),
                                JsonUtils.parseObject(r.getData(), Map.class),
                                r.getTime().getTime(), r.getReportTime(), null))
                .collect(Collectors.toList()), result.getTotal());
    }

    @Override
    public List<TimeData> getDeviceMessageStatsWithUid(String uid, long start, long end) {
        String sql = "SELECT time,COUNT(*) AS data FROM(" +
                "SELECT TIMETRUNCATE(time,'1h') AS time FROM thing_model_message " +
                "WHERE time>=? AND time<=? " + (uid != null ? "AND uid=?" : "") +
                ") a GROUP BY time ORDER BY time ASC";

        List<Object> args = new ArrayList<>();
        args.add(start);
        args.add(end);
        if (uid != null) {
            args.add(uid);
        }

        return pgTemplate.query(sql, new BeanPropertyRowMapper<>(TimeData.class), args.toArray());
    }

    @Override
    public List<TimeData> getDeviceUpMessageStatsWithUid(String uid, Long start, Long end) {
        String sql = "SELECT time,COUNT(*) AS data FROM(" +
                "SELECT TIMETRUNCATE(time,'1h') AS time FROM thing_model_message " +
                "WHERE (type='property' AND identifier='report') OR type='event' ";
        StringBuilder sqlBuffer = new StringBuilder();
        sqlBuffer.append(sql);

        List<Object> args = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(uid)) {
            sqlBuffer.append(" AND uid=?");
            args.add(uid);
        }

        if (ObjectUtil.isNotEmpty(start) && ObjectUtil.isNotEmpty(end)) {
            sqlBuffer.append(" AND time>=? AND time<=?");
            args.add(start);
            args.add(end);
        }

        sqlBuffer.append(") a GROUP BY time ORDER BY time ASC");

        return pgTemplate.query(sqlBuffer.toString(), new BeanPropertyRowMapper<>(TimeData.class), args.toArray());
    }

    @Override
    public List<TimeData> getDeviceDownMessageStatsWithUid(String uid, Long start, Long end) {
        String sql = "SELECT time,COUNT(*) AS data FROM(" +
                "SELECT TIMETRUNCATE(time,1h) AS time FROM thing_model_message " +
                "WHERE (type='property' AND identifier='report') OR type='service' OR type= 'config' ";
        StringBuilder sqlBuffer = new StringBuilder();
        sqlBuffer.append(sql);

        List<Object> args = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(uid)) {
            sqlBuffer.append(" AND uid=?");
            args.add(uid);
        }

        if (ObjectUtil.isNotEmpty(start) && ObjectUtil.isNotEmpty(end)) {
            sqlBuffer.append(" AND time>=? AND time<=?");
            args.add(start);
            args.add(end);
        }

        sqlBuffer.append(") a GROUP BY time ORDER BY time ASC");

        return pgTemplate.query(sqlBuffer.toString(), new BeanPropertyRowMapper<>(TimeData.class), args.toArray());
    }

    @Override
    public void add(ThingModelMessage msg) {
        PgThingModelMessage message = BeanUtil.copyProperties(msg, PgThingModelMessage.class, "time", "data", "reportTime", "deviceName");
        message.setData(msg.getData() == null ? "{}" : JsonUtils.toJsonString(msg.getData()));
        message.setDeviceName(msg.getDn());
        message.setTime(new PGTimestamp(msg.getOccurred()));
        message.setReportTime(msg.getTime());
        thingModelMessageMapper.insert(message);
    }

    @Override
    public long count() {
        return thingModelMessageMapper.selectCount();
    }
}
