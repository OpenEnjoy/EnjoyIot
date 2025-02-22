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


import com.enjoyiot.eiot.message.core.MqProducer;
import com.enjoyiot.eiot.message.kafka.partitioner.ProductKeyPartitioner;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static com.enjoyiot.eiot.message.core.ErrorCodeConstants.INIT_PRODUCER_ERROR;
import static com.enjoyiot.eiot.message.core.ErrorCodeConstants.SEND_MSG_ERROR;
import static com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil.exception;

public class KafkaProducer<T> implements MqProducer<T> {

    private KafkaTemplate kafkaTemplate;

    public KafkaProducer(String bootstrapServers) {

        try {
            try {
                kafkaTemplate = createProducer(bootstrapServers, "iot-producer-" + System.currentTimeMillis());
            } catch (Throwable e) {
                throw exception(INIT_PRODUCER_ERROR);
            }
        } catch (Throwable e) {
            throw exception(INIT_PRODUCER_ERROR);
        }
    }

    @Override
    public void publish(String topic, T msg) {
        try {
            kafkaTemplate.send(new ProducerRecord<String, T>(topic, msg));
        } catch (Throwable e) {
            throw exception(SEND_MSG_ERROR);
        }
    }

    public static KafkaTemplate createProducer(String bootstrapServers, String clientId) {

        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerProps.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        producerProps.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, ProductKeyPartitioner.class);

        DefaultKafkaProducerFactory<?, ?> kafkaProducerFactory = new DefaultKafkaProducerFactory<>(producerProps);
        KafkaTemplate<?, ?> kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);

//        kafkaTemplate.setProducerListener(kafkaProducerListener);
        return kafkaTemplate;
    }

}
