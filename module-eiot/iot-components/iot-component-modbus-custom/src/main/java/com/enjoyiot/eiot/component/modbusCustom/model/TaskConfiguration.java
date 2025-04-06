package com.enjoyiot.eiot.component.modbusCustom.model;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@AutoConfiguration
@EnableAspectJAutoProxy
@EnableAsync(proxyTargetClass = true)
public class TaskConfiguration {

    @Bean(name = "ModbusTaskScheduler")
    public ThreadPoolTaskScheduler setThreadPoolTaskScheduler(){
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        taskScheduler.setThreadNamePrefix("modbus-task-pool-");
        // 执行shutdown时，等待前一个任务执行完
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        // 执行shutdown时，等待的超时时间
        taskScheduler.setAwaitTerminationSeconds(30);
        // 自定义错误处理器，当某个线程执行时出现错误会跳过线程内部的Try/catch，进入ErrorHandler
        // 需要实现 ErrorHandler 接口
        // taskScheduler.setErrorHandler(new MyTaskErrorHandler());
        return taskScheduler;
    }
}
