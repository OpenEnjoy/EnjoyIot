
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
package com.enjoyiot.module.infra.service.job;

import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.test.core.ut.BaseDbUnitTest;
import com.enjoyiot.module.infra.controller.admin.job.vo.log.JobLogPageReqVO;
import com.enjoyiot.module.infra.dal.dataobject.job.JobLogDO;
import com.enjoyiot.module.infra.dal.mysql.job.JobLogMapper;
import com.enjoyiot.module.infra.enums.job.JobLogStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static com.enjoyiot.framework.common.util.date.LocalDateTimeUtils.addTime;
import static com.enjoyiot.framework.common.util.date.LocalDateTimeUtils.buildTime;
import static com.enjoyiot.framework.common.util.object.ObjectUtils.cloneIgnoreId;
import static com.enjoyiot.framework.test.core.util.AssertUtils.assertPojoEquals;
import static com.enjoyiot.framework.test.core.util.RandomUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Import(JobLogServiceImpl.class)
public class JobLogServiceImplTest extends BaseDbUnitTest {

    @Resource
    private JobLogServiceImpl jobLogService;
    @Resource
    private JobLogMapper jobLogMapper;

    @Test
    public void testCreateJobLog() {
        // 准备参数
        JobLogDO reqVO = randomPojo(JobLogDO.class, o -> o.setExecuteIndex(1));

        // 调用
        Long id = jobLogService.createJobLog(reqVO.getJobId(), reqVO.getBeginTime(),
                reqVO.getHandlerName(), reqVO.getHandlerParam(), reqVO.getExecuteIndex());
        // 断言
        assertNotNull(id);
        // 校验记录的属性是否正确
        JobLogDO job = jobLogMapper.selectById(id);
        assertEquals(JobLogStatusEnum.RUNNING.getStatus(), job.getStatus());
    }

    @Test
    public void testUpdateJobLogResultAsync_success() {
        // mock 数据
        JobLogDO log = randomPojo(JobLogDO.class, o -> {
            o.setExecuteIndex(1);
            o.setStatus(JobLogStatusEnum.RUNNING.getStatus());
        });
        jobLogMapper.insert(log);
        // 准备参数
        Long logId = log.getId();
        LocalDateTime endTime = randomLocalDateTime();
        Integer duration = randomInteger();
        boolean success = true;
        String result = randomString();

        // 调用
        jobLogService.updateJobLogResultAsync(logId, endTime, duration, success, result);
        // 校验记录的属性是否正确
        JobLogDO dbLog = jobLogMapper.selectById(log.getId());
        assertEquals(endTime, dbLog.getEndTime());
        assertEquals(duration, dbLog.getDuration());
        assertEquals(JobLogStatusEnum.SUCCESS.getStatus(), dbLog.getStatus());
        assertEquals(result, dbLog.getResult());
    }

    @Test
    public void testUpdateJobLogResultAsync_failure() {
        // mock 数据
        JobLogDO log = randomPojo(JobLogDO.class, o -> {
            o.setExecuteIndex(1);
            o.setStatus(JobLogStatusEnum.RUNNING.getStatus());
        });
        jobLogMapper.insert(log);
        // 准备参数
        Long logId = log.getId();
        LocalDateTime endTime = randomLocalDateTime();
        Integer duration = randomInteger();
        boolean success = false;
        String result = randomString();

        // 调用
        jobLogService.updateJobLogResultAsync(logId, endTime, duration, success, result);
        // 校验记录的属性是否正确
        JobLogDO dbLog = jobLogMapper.selectById(log.getId());
        assertEquals(endTime, dbLog.getEndTime());
        assertEquals(duration, dbLog.getDuration());
        assertEquals(JobLogStatusEnum.FAILURE.getStatus(), dbLog.getStatus());
        assertEquals(result, dbLog.getResult());
    }

    @Test
    public void testCleanJobLog() {
        // mock 数据
        JobLogDO log01 = randomPojo(JobLogDO.class, o -> o.setCreateTime(addTime(Duration.ofDays(-3))))
                .setExecuteIndex(1);
        jobLogMapper.insert(log01);
        JobLogDO log02 = randomPojo(JobLogDO.class, o -> o.setCreateTime(addTime(Duration.ofDays(-1))))
                .setExecuteIndex(1);
        jobLogMapper.insert(log02);
        // 准备参数
        Integer exceedDay = 2;
        Integer deleteLimit = 1;

        // 调用
        Integer count = jobLogService.cleanJobLog(exceedDay, deleteLimit);
        // 断言
        assertEquals(1, count);
        List<JobLogDO> logs = jobLogMapper.selectList();
        assertEquals(1, logs.size());
        // TODO createTime updateTime 被屏蔽，仅 win11 会复现，建议后续修复。
        assertPojoEquals(log02, logs.get(0), "createTime", "updateTime");
    }

    @Test
    public void testGetJobLog() {
        // mock 数据
        JobLogDO dbJobLog = randomPojo(JobLogDO.class, o -> o.setExecuteIndex(1));
        jobLogMapper.insert(dbJobLog);
        // 准备参数
        Long id = dbJobLog.getId();

        // 调用
        JobLogDO jobLog = jobLogService.getJobLog(id);
        // 断言
        assertPojoEquals(dbJobLog, jobLog);
    }

    @Test
    public void testGetJobPage() {
        // mock 数据
        JobLogDO dbJobLog = randomPojo(JobLogDO.class, o -> {
            o.setExecuteIndex(1);
            o.setHandlerName("handlerName 单元测试");
            o.setStatus(JobLogStatusEnum.SUCCESS.getStatus());
            o.setBeginTime(buildTime(2021, 1, 8));
            o.setEndTime(buildTime(2021, 1, 8));
        });
        jobLogMapper.insert(dbJobLog);
        // 测试 jobId 不匹配
        jobLogMapper.insert(cloneIgnoreId(dbJobLog, o -> o.setJobId(randomLongId())));
        // 测试 handlerName 不匹配
        jobLogMapper.insert(cloneIgnoreId(dbJobLog, o -> o.setHandlerName(randomString())));
        // 测试 beginTime 不匹配
        jobLogMapper.insert(cloneIgnoreId(dbJobLog, o -> o.setBeginTime(buildTime(2021, 1, 7))));
        // 测试 endTime 不匹配
        jobLogMapper.insert(cloneIgnoreId(dbJobLog, o -> o.setEndTime(buildTime(2021, 1, 9))));
        // 测试 status 不匹配
        jobLogMapper.insert(cloneIgnoreId(dbJobLog, o -> o.setStatus(JobLogStatusEnum.FAILURE.getStatus())));
        // 准备参数
        JobLogPageReqVO reqVo = new JobLogPageReqVO();
        reqVo.setJobId(dbJobLog.getJobId());
        reqVo.setHandlerName("单元");
        reqVo.setBeginTime(dbJobLog.getBeginTime());
        reqVo.setEndTime(dbJobLog.getEndTime());
        reqVo.setStatus(JobLogStatusEnum.SUCCESS.getStatus());

        // 调用
        PageResult<JobLogDO> pageResult = jobLogService.getJobLogPage(reqVo);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(dbJobLog, pageResult.getList().get(0));
    }

}
