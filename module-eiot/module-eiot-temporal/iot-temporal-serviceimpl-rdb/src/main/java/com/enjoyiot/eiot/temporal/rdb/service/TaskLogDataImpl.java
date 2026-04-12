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


import com.enjoyiot.eiot.ITaskLogData;
import com.enjoyiot.eiot.temporal.rdb.dao.RdbTemplate;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.task.dto.TaskLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskLogDataImpl implements ITaskLogData {

    @Autowired
    private RdbTemplate rdbTemplate;

    @Override
    public void deleteByTaskId(Long taskId) {
        rdbTemplate.update("delete from task_log where task_id=?", taskId);
    }

    @Override
    public PageResult<TaskLog> findByTaskId(Long taskId, int page, int size) {
        String sql = "select time,content,success,task_id from task_log where task_id=? order by time desc limit ? offset ?";
        List<Map<String, Object>> taskLogs = rdbTemplate.queryForList(sql, taskId, size, (page - 1) * size);

        List<TaskLog> logs = taskLogs.stream().map(r -> {
            Long time = ((Number) r.get("time")).longValue();
            return new TaskLog(
                    String.valueOf(time),
                    taskId,
                    (String) r.get("content"),
                    ((Number) r.get("success")).intValue() == 1,
                    time
            );
        }).collect(Collectors.toList());

        sql = "select count(*) from task_log where task_id=?";
        Long count = rdbTemplate.queryForObject(sql, Long.class, taskId);

        return new PageResult<>(logs, count != null ? count : 0L);
    }

    @Override
    public void add(TaskLog log) {
        String sql = "INSERT INTO task_log (time, content, success, task_id) VALUES (?, ?, ?, ?)";
        rdbTemplate.update(sql, System.currentTimeMillis(), log.getContent(), log.getSuccess() ? 1 : 0, log.getTaskId());
    }
}
