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
package com.enjoyiot.eiot.ruleengine.handler;


import com.enjoyiot.eiot.common.constant.Constants;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.message.core.ConsumerHandler;
import com.enjoyiot.eiot.message.core.MqConsumer;
import com.enjoyiot.framework.common.util.thread.ThreadUtil;
import com.enjoyiot.framework.tenant.core.util.TenantUtils;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.stream.Collectors;

@Slf4j
public class RuleDeviceConsumer implements ConsumerHandler<ThingModelMessage>, ApplicationContextAware {

    private final List<DeviceMessageHandler> handlers = new ArrayList<>();
    private ScheduledThreadPoolExecutor messageHandlerPool;

    @SneakyThrows
    public RuleDeviceConsumer(MqConsumer<ThingModelMessage> consumer) {
        consumer.consume(Constants.THING_MODEL_MESSAGE_TOPIC, this);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, DeviceMessageHandler> handlerMap = applicationContext.getBeansOfType(DeviceMessageHandler.class);
        messageHandlerPool = ThreadUtil.newScheduled(handlerMap.size() * 2, "messageHandler");
        //this.handlers.addAll(handlerMap.values());
        List<DeviceMessageHandler> handlerList = Lists.newArrayList(handlerMap.values());
        handlerList = handlerList.stream().sorted(Comparator.comparingInt(a -> {
            Order o = a.getClass().getAnnotation(Order.class);
            if (o == null) {
                return Integer.MAX_VALUE;
            }
            return o.value();
        })).collect(Collectors.toList());
        handlers.addAll(handlerList);
    }

    @SneakyThrows
    @Override
//    @TenantIgnore
    public void handler(ThingModelMessage msg) {
        log.info("received thing model message:{}", msg);
        try {
            CompletableFuture<?> future = CompletableFuture.completedFuture("messageHandler");
            for (DeviceMessageHandler handler : this.handlers) {
                future = future.thenRunAsync(() -> {
                    try {
                        extracted(msg, handler);
                    } catch (Throwable e) {
                        log.error("handler message error", e);
                    }
                }, messageHandlerPool);
            }
            future.join(); // 等待所有任务完成
        } catch (Throwable e) {
            log.error("rule device message process error", e);
        }
    }

    private static void extracted(ThingModelMessage msg, DeviceMessageHandler handler) {
        if (!(msg.getData() instanceof Map)) {
            msg.setData(new HashMap<>());
        }
        TenantUtils.executeIgnore(() -> {
                    handler.handle(msg);
                }
        );
    }

}

