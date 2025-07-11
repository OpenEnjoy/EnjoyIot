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


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.enjoyiot.eiot.ITaskLogData;
import com.enjoyiot.eiot.temporal.timescaledb.dao.PgTaskLogMapper;
import com.enjoyiot.eiot.temporal.timescaledb.model.PgTaskLog;
import com.enjoyiot.framework.common.pojo.PageParam;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.util.object.BeanUtils;
import com.enjoyiot.module.eiot.api.task.dto.TaskLog;
import org.postgresql.util.PGTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TaskLogDataImpl implements ITaskLogData {

    @Autowired
    private PgTaskLogMapper taskLogMapper;

    @Override
    public void deleteByTaskId(Long taskId) {
        taskLogMapper.delete(PgTaskLog::getTaskId, taskId);
    }

    @Override
    public PageResult<TaskLog> findByTaskId(Long taskId, int page, int size) {
        PageParam pageParam = new PageParam();
        pageParam.setPageNo(page);
        pageParam.setPageSize(size);
        PageResult<PgTaskLog> result = taskLogMapper.selectPage(pageParam,
                Wrappers.lambdaQuery(PgTaskLog.class).eq(PgTaskLog::getTaskId, taskId).orderByDesc(PgTaskLog::getTime)
        );
        return new PageResult<>(result.getList().stream().map(r ->
                        new TaskLog(r.getTime().toString(), taskId,
                                r.getContent(), r.getSuccess(), r.getTime().getTime()))
                .collect(Collectors.toList()), result.getTotal());
    }

    @Override
    public void add(TaskLog log) {
        PgTaskLog taskLog = BeanUtils.toBean(log, PgTaskLog.class);
        taskLog.setTime(new PGTimestamp(System.currentTimeMillis()));
        taskLogMapper.insert(taskLog);
    }

}
