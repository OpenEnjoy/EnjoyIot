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
package com.enjoyiot.eiot.task;


import cn.hutool.core.collection.CollectionUtil;
import com.enjoyiot.eiot.ITaskLogData;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.task.RuleTaskApi;
import com.enjoyiot.module.eiot.api.task.TaskService;
import com.enjoyiot.module.eiot.api.task.dto.TaskInfo;
import com.enjoyiot.module.eiot.api.task.dto.TaskInfoPageReq;
import com.enjoyiot.module.eiot.api.task.dto.TaskLog;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TaskServiceImpl implements TaskService, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Resource
    private ITaskLogData taskLogData;

    @Resource
    private RuleTaskApi ruleTaskApi;

    public TaskServiceImpl() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.schedule(this::initTask, 1, TimeUnit.SECONDS);
    }

    public void initTask() {
        int idx = 1;
        while (true) {
            TaskInfoPageReq pageRequest = new TaskInfoPageReq();
            pageRequest.setPageNo(idx);
            pageRequest.setPageSize(100);
            PageResult<TaskInfo> all = ruleTaskApi.selectTaskPage(pageRequest);
            List<TaskInfo> tasks = all.getList();

            // 如果记录为空，直接跳出循环
            if (CollectionUtil.isEmpty(tasks)) {
                break;
            }
            tasks.forEach(task -> {
                try {
                    if (!TaskInfo.STATE_RUNNING.equals(task.getState())) {
                        return;
                    }
                    log.info("got task {} to init", task.getId());
                    addTask(task);
                } catch (Throwable e) {
                    log.error("add task error", e);
                }
            });
            idx++;
        }
    }

    @SneakyThrows
    private void addTask(TaskInfo task) {
        // 指明job的名称，所在组的名称，以及绑定job类
        JobDataMap jobData = new JobDataMap();
        jobData.put("taskInfo", task);
        JobDetail jobDetail = JobBuilder.newJob(CommonJob.class)
                .withIdentity(task.getId().toString(), task.getUid())
                .usingJobData(jobData)
                .build();

        ScheduleBuilder<?> scheduleBuilder = getScheduleBuilder(task);
        if (scheduleBuilder == null) {
            log.error("get task builder failed");
            return;
        }

        // 定义调度触发规则
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(task.getId().toString(), task.getUid())// 触发器key
                .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                .withSchedule(scheduleBuilder)
                .startNow().build();

        // 把作业和触发器注册到任务调度中
        Scheduler scheduler = getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);

        // 启动
        if (!scheduler.isShutdown()) {
            scheduler.start();
        }
    }

    private ScheduleBuilder<?> getScheduleBuilder(TaskInfo task) {
        if (TaskInfo.TYPE_TIMER.equals(task.getType())) {
            return CronScheduleBuilder.cronSchedule(task.getExpression());
        }
        if (TaskInfo.TYPE_DELAY.equals(task.getType())) {
            Long delayTime = task.delayTime();
            if (delayTime == null) {
                return null;
            }
            delayTime = System.currentTimeMillis() + delayTime * 1000;

            return DailyTimeIntervalScheduleBuilder
                    .dailyTimeIntervalSchedule()
                    .startingDailyAt(TimeOfDay.hourAndMinuteAndSecondFromDate(new Date(delayTime)))
                    .endingDailyAt(TimeOfDay.hourAndMinuteAndSecondFromDate(new Date(delayTime + 1000)))
                    .withRepeatCount(1)
                    ;
        }
        return null;
    }

    @SneakyThrows
    public void saveTask(TaskInfo task) {
        TriggerKey triggerKey = new TriggerKey(task.getId().toString(), task.getUid());
        Trigger oldTrigger = getScheduler().getTrigger(triggerKey);
        if (oldTrigger == null) {
            log.warn("saveTask:trigger does not exist");
            addTask(task);
            return;
        }
        Scheduler scheduler = getScheduler();
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
        scheduler.deleteJob(JobKey.jobKey(task.getId().toString(), task.getUid()));

        addTask(task);
    }

    public void renewTask(TaskInfo task) {
        saveTask(task);
    }

    public TaskInfo updateTaskState(Long taskId, String state, String reason) {
        TaskInfo taskInfo = ruleTaskApi.getTask(taskId);
        if (taskInfo == null) {
            return null;
        }
        taskInfo.setState(state);
        taskInfo.setReason(reason);
        ruleTaskApi.updateTask(taskInfo);
        return taskInfo;
    }

    public void finishTask(Long taskId) {
        TaskInfo taskInfo = ruleTaskApi.getTask(taskId);
        if (taskInfo == null) {
            return;
        }
        taskInfo.setState(TaskInfo.STATE_FINISHED);
        ruleTaskApi.updateTask(taskInfo);
    }

    @SneakyThrows
    public void pauseTask(Long taskId, String reason) {
        TaskInfo taskInfo = updateTaskState(taskId, TaskInfo.STATE_STOP, reason);
        if (taskInfo == null) {
            return;
        }
        Scheduler scheduler = getScheduler();
        TriggerKey triggerKey = new TriggerKey(taskInfo.getId().toString(), taskInfo.getUid());
        if (!scheduler.checkExists(triggerKey)) {
            return;
        }

        scheduler.pauseTrigger(triggerKey);
        scheduler.pauseJob(JobKey.jobKey(taskInfo.getId().toString(), taskInfo.getUid()));
    }

    @SneakyThrows
    public void deleteTask(Long taskId, String reason) {
        TaskInfo taskInfo = updateTaskState(taskId, TaskInfo.STATE_STOP, reason);
        if (taskInfo == null) {
            return;
        }
        Scheduler scheduler = getScheduler();
        TriggerKey triggerKey = new TriggerKey(taskInfo.getId().toString(), taskInfo.getUid());
        if (!scheduler.checkExists(triggerKey)) {
            return;
        }
        scheduler.deleteJob(JobKey.jobKey(taskInfo.getId().toString(), taskInfo.getUid()));
    }

    @SneakyThrows
    public void resumeTask(Long taskId, String reason) {
        TaskInfo taskInfo = updateTaskState(taskId, TaskInfo.STATE_RUNNING, reason);
        if (taskInfo == null) {
            return;
        }
        Scheduler scheduler = getScheduler();
        TriggerKey triggerKey = new TriggerKey(taskInfo.getId().toString(), taskInfo.getUid());
        if (!scheduler.checkExists(triggerKey)) {
            addTask(taskInfo);
        }

        scheduler.resumeTrigger(triggerKey);
        scheduler.resumeJob(JobKey.jobKey(taskInfo.getId().toString(), taskInfo.getUid()));
    }

    public void addLog(Long taskId, boolean success, String content) {
        taskLogData.add(TaskLog.builder()
                .id(UUID.randomUUID().toString())
                .taskId(taskId)
                .success(success)
                .content(content)
                .logAt(System.currentTimeMillis())
                .build());
    }

    public Scheduler getScheduler() {
        return this.applicationContext.getBean("scheduler", Scheduler.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
