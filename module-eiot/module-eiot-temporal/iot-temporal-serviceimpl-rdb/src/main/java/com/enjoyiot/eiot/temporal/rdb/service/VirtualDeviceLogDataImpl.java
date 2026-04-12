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


import com.enjoyiot.eiot.IVirtualDeviceLogData;
import com.enjoyiot.eiot.temporal.rdb.dao.RdbTemplate;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.virtualdevice.dto.VirtualDeviceLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VirtualDeviceLogDataImpl implements IVirtualDeviceLogData {

    @Autowired
    private RdbTemplate rdbTemplate;

    @Override
    public PageResult<VirtualDeviceLog> findByVirtualDeviceId(Long virtualDeviceId, int page, int size) {
        String sql = "select time,virtual_device_id,virtual_device_name,device_total,result from virtual_device_log " +
                "where virtual_device_id=? order by time desc limit ? offset ?";
        List<Map<String, Object>> logs = rdbTemplate.queryForList(sql, virtualDeviceId, size, (page - 1) * size);

        List<VirtualDeviceLog> result = logs.stream().map(r -> {
            Long time = ((Number) r.get("time")).longValue();
            return new VirtualDeviceLog(
                    time,
                    virtualDeviceId,
                    (String) r.get("virtual_device_name"),
                    ((Number) r.get("device_total")).intValue(),
                    (String) r.get("result"),
                    time
            );
        }).collect(Collectors.toList());

        sql = "select count(*) from virtual_device_log where virtual_device_id=?";
        Long count = rdbTemplate.queryForObject(sql, Long.class, virtualDeviceId);

        return new PageResult<>(result, count != null ? count : 0L);
    }

    @Override
    public void add(VirtualDeviceLog log) {
        String sql = "INSERT INTO virtual_device_log (time, virtual_device_name, device_total, result, virtual_device_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        rdbTemplate.update(sql, System.currentTimeMillis(), log.getVirtualDeviceName(),
                log.getDeviceTotal(), log.getResult(), log.getVirtualDeviceId());
    }
}
