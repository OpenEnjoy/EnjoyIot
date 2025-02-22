
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

import com.enjoyiot.eiot.message.core.MqProducer;
import org.springframework.context.ApplicationEventPublisher;

public class SpringEventProducer<T> implements MqProducer<T> {

    private final ApplicationEventPublisher eventPublisher;

    public SpringEventProducer(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publish(String topic, T msg) {
        // 使用Spring Event发布消息
        // 这里我们将topic和消息包装成一个事件对象
        SpringEventMessage<T> event = new SpringEventMessage<>(topic, msg);
        eventPublisher.publishEvent(event);
    }
}