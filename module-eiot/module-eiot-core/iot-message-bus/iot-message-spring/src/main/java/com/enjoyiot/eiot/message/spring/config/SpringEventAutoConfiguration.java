
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
package com.enjoyiot.eiot.message.spring.config;

import com.enjoyiot.eiot.common.thing.ComponentMessage;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.message.core.MqConsumer;
import com.enjoyiot.eiot.message.core.MqProducer;
import com.enjoyiot.eiot.message.spring.SpringEventConsumer;
import com.enjoyiot.eiot.message.spring.SpringEventProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
@ConditionalOnProperty(name = "eiot.message.producer-type", havingValue = "spring-event", matchIfMissing = true)
public class SpringEventAutoConfiguration {

//    @ConditionalOnMissingBean
    @Bean
    public MqProducer<ThingModelMessage> producer(ApplicationEventPublisher eventPublisher) {
        return new SpringEventProducer<>(eventPublisher);
    }

//    @ConditionalOnMissingBean
    @Bean
    public MqConsumer<ThingModelMessage> consumer() {
        return new SpringEventConsumer<>();
    }


//    @ConditionalOnMissingBean
    @Bean
    public MqProducer<ComponentMessage> componentMessageProducer(ApplicationEventPublisher eventPublisher) {
        return new SpringEventProducer<>(eventPublisher);
    }

//    @ConditionalOnMissingBean(value = )
    @Bean
    public MqConsumer<ComponentMessage> componentMessageConsumer() {
        return new SpringEventConsumer<>();
    }
}