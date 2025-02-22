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

package com.enjoyiot.eiot.message.kafka;


import com.enjoyiot.eiot.message.core.ConsumerHandler;
import com.enjoyiot.eiot.message.core.MqConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class KafkaConsumer<T> implements MqConsumer<T> {

    private static final AtomicLong CONSUME_ID = new AtomicLong(0);

    // 注入Kafka监听器端点注册表，用于管理Kafka消费者容器
//    @Autowired
//    private KafkaListenerEndpointRegistry registry;

    private String bootstrapServers;

    private final Class<T> msgType;

    public KafkaConsumer(String bootstrapServers, Class<T> cls) {
        this.bootstrapServers = bootstrapServers;
        this.msgType = cls;
    }

    @Override
    public void consume(String topic, ConsumerHandler<T> handler) {
        try {
            String groupId = handler.getClass().getName().replace(".", "");
            Map<String, Object> consumerProps = new HashMap<>();
            consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
            consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

            ConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);
            // 订阅的主题
            ContainerProperties containerProps = new ContainerProperties(topic);
            containerProps.setMessageListener((MessageListener<String, T>) message -> {
                T msg = message.value();
                handler.handler(msg);
            });

            ConcurrentMessageListenerContainer<String, String> container = new ConcurrentMessageListenerContainer<>(consumerFactory, containerProps);
            container.start();
        } catch (Throwable e) {
            log.error("consume error", e);
        }
    }

}
