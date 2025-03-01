/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com | Tel: 19918996474
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot] | Tel: 19918996474
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
package com.enjoyiot.eiot.temporal.es.service;


import com.enjoyiot.eiot.ITaskLogData;
import com.enjoyiot.eiot.temporal.es.dao.TaskLogRepository;
import com.enjoyiot.eiot.temporal.es.convert.EsTaskLogConvert;
import com.enjoyiot.eiot.temporal.es.document.DocTaskLog;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.task.dto.TaskLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TaskLogDataImpl implements ITaskLogData {

    @Autowired
    private TaskLogRepository taskLogRepository;

    @Override
    public void deleteByTaskId(Long taskId) {
        taskLogRepository.deleteByTaskId(taskId);
    }

    @Override
    public PageResult<TaskLog> findByTaskId(Long taskId, int page, int size) {
        Page<DocTaskLog> paged = taskLogRepository.findByTaskIdOrderByLogAtDesc(taskId, Pageable.ofSize(size).withPage(page - 1));
        return new PageResult<>(
                paged.getContent().stream().map(o -> EsTaskLogConvert.INSTANCE.convert(o))
                        .collect(Collectors.toList()), paged.getTotalElements());
    }

    @Override
    public void add(TaskLog log) {
        taskLogRepository.save(EsTaskLogConvert.INSTANCE.convertDoc(log));
    }
}
