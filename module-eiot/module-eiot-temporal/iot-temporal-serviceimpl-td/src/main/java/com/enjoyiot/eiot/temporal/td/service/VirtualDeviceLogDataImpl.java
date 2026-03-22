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
package com.enjoyiot.eiot.temporal.td.service;


import com.enjoyiot.eiot.IVirtualDeviceLogData;
import com.enjoyiot.eiot.temporal.td.dao.TdTemplate;
import com.enjoyiot.eiot.temporal.td.model.TbVirtualDeviceLog;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.virtualdevice.dto.VirtualDeviceLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VirtualDeviceLogDataImpl implements IVirtualDeviceLogData {

    @Autowired
    private TdTemplate tdTemplate;

    @Override
    public PageResult<VirtualDeviceLog> findByVirtualDeviceId(Long virtualDeviceId, int page, int size) {
        String sql = "select time,virtual_device_id,virtual_device_name,device_total,result from virtual_device_log_%s order by time desc limit %d offset %d";
        sql = String.format(sql, formatTableName(virtualDeviceId), size, (page - 1) * size);
        List<TbVirtualDeviceLog> logs = tdTemplate.query(sql, new BeanPropertyRowMapper<>(TbVirtualDeviceLog.class));

        sql = "select count(*) from virtual_device_log_" + formatTableName(virtualDeviceId);
        List<Long> counts = tdTemplate.queryForList(sql, Long.class);

        return new PageResult<>(logs.stream().map(r ->
                        new VirtualDeviceLog(r.getTime(), Long.valueOf(virtualDeviceId),
                                r.getVirtualDeviceName(),
                                r.getDeviceTotal(), r.getResult(), r.getTime()))
                .collect(Collectors.toList()), !counts.isEmpty() ? counts.get(0) : 0L);
    }

    private String formatTableName(Long virtualDeviceId) {
        return String.valueOf(virtualDeviceId).toLowerCase();
    }

    @Override
    public void add(VirtualDeviceLog log) {
        //使用virtualDeviceId作表名
        String sql = String.format("INSERT INTO %s (%s) USING %s TAGS ('%s') VALUES (%s);",
                "virtual_device_log_" + log.getVirtualDeviceId(),
                "time,virtual_device_name,device_total,result",
                "virtual_device_log",
                log.getVirtualDeviceId(),
                "?,?,?,?"
        );
        tdTemplate.update(sql, System.currentTimeMillis(), log.getVirtualDeviceName(),
                log.getDeviceTotal(), log.getResult());
    }
}
