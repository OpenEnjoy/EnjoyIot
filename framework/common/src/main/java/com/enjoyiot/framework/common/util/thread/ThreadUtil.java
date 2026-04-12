/*
 *
 *  * | Licensed 未经许可不能去掉「EnjoyIot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2024] [EnjoyIot]
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
package com.enjoyiot.framework.common.util.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

public class ThreadUtil {

    /**
     * 创建基于虚拟线程的执行器(适合短时任务、IO密集型操作)
     *
     * @param threadName 线程名称前缀
     * @return ExecutorService
     */
    public static java.util.concurrent.ExecutorService newVirtual(String threadName) {
        return Executors.newThreadPerTaskExecutor(
                Thread.ofVirtual()
                        .name(threadName + "-", 0)
                        .factory()
        );
    }

    /**
     * 创建基于虚拟线程的定时任务执行器(适合短时定时任务)
     *
     * @param threadName 线程名称前缀
     * @return ScheduledExecutorService
     * @deprecated 虚拟线程不适合定时调度任务,请使用 {@link #newScheduledPlatform(String)} 创建平台线程的定时任务
     */
    @Deprecated(since = "2026-04-12", forRemoval = true)
    public static ScheduledExecutorService newScheduled(String threadName) {
        ThreadFactory threadFactory = Thread.ofVirtual()
                .name(threadName + "-", 0)
                .factory();
        return Executors.newScheduledThreadPool(1, threadFactory);
    }

    /**
     * 创建平台线程的定时任务执行器(推荐用于定时调度任务)
     *
     * @param poolSize 核心线程池大小
     * @param threadName 线程名称前缀
     * @return ScheduledExecutorService
     */
    public static ScheduledExecutorService newScheduledPlatform(int poolSize, String threadName) {
        return Executors.newScheduledThreadPool(poolSize, r -> {
            Thread t = new Thread(r, threadName + "-" + System.currentTimeMillis());
            t.setDaemon(false);
            return t;
        });
    }

    /**
     * 创建平台线程的定时任务执行器(推荐用于定时调度任务)
     *
     * @param threadName 线程名称前缀
     * @return ScheduledExecutorService
     */
    public static ScheduledExecutorService newScheduledPlatform(String threadName) {
        return newScheduledPlatform(1, threadName);
    }

    /**
     * 创建基于虚拟线程的定时任务执行器(兼容旧版本)
     *
     * @param poolSize 线程池大小(虚拟线程模式下此参数将被忽略)
     * @param threadName 线程名称前缀
     * @return ScheduledExecutorService
     * @deprecated 请使用 {@link #newScheduledPlatform(int, String)} 或 {@link #newVirtual(String)}
     */
    @Deprecated
    public static ScheduledExecutorService newScheduled(int poolSize, String threadName) {
        return newScheduled(threadName);
    }

}
