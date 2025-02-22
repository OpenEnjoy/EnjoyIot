
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
/**
 * 多租户，支持如下层面：
 * 1. DB：基于 MyBatis Plus 多租户的功能实现。
 * 2. Redis：通过在 Redis Key 上拼接租户编号的方式，进行隔离。
 * 3. Web：请求 HTTP API 时，解析 Header 的 tenant-id 租户编号，添加到租户上下文。
 * 4. Security：校验当前登陆的用户，是否越权访问其它租户的数据。
 * 5. Job：在 JobHandler 执行任务时，会按照每个租户，都独立并行执行一次。
 * 6. MQ：在 Producer 发送消息时，Header 带上 tenant-id 租户编号；在 Consumer 消费消息时，将 Header 的 tenant-id 租户编号，添加到租户上下文。
 * 7. Async：异步需要保证 ThreadLocal 的传递性，通过使用阿里开源的 TransmittableThreadLocal 实现。相关的改造点，可见：
 *      1）Spring Async：
 *          {@link com.enjoyiot.framework.quartz.config.EnjoyAsyncAutoConfiguration#threadPoolTaskExecutorBeanPostProcessor()}
 *      2）Spring Security：
 *          TransmittableThreadLocalSecurityContextHolderStrategy
 *          和 EnjoySecurityAutoConfiguration#securityContextHolderMethodInvokingFactoryBean() 方法
 *
 */
package com.enjoyiot.framework.tenant;
