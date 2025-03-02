
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
package com.enjoyiot.eiot.message.spring;

import com.enjoyiot.eiot.message.core.ConsumerHandler;
import com.enjoyiot.eiot.message.core.MqConsumer;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpringEventConsumer<T> implements MqConsumer<T>, ReceiveMessage<T> {

    private final Map<String, List<ConsumerHandler<T>>> handlers = new ConcurrentHashMap<>();

    @Override
    public void consume(String topic, ConsumerHandler<T> handler) {
        List<ConsumerHandler<T>> handlerList = handlers.getOrDefault(topic, new ArrayList<>());
        // 如果handlerList不存在handler, 则添加handler
        if (!handlerList.contains(handler)) {
            handlerList.add(handler);
            handlers.put(topic, handlerList);
        }
    }

    @EventListener
    @Async // 异步处理消息
    public void onMessage(SpringEventMessage<T> event) {
        String topic = event.getTopic();
        List<ConsumerHandler<T>> handlerList = handlers.get(topic);
        if (handlerList != null) {
            for (ConsumerHandler<T> handler : handlerList) {
                handler.handler(event.getMessage());
            }
        }
    }

}