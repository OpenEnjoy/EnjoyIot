

package com.enjoyiot.eiot.message.rocketmq.config;


import com.enjoyiot.eiot.common.thing.ComponentMessage;
import com.enjoyiot.eiot.message.rocketmq.RocketMqConsumer;
import com.enjoyiot.eiot.message.rocketmq.RocketMqProducer;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.message.core.MqConsumer;
import com.enjoyiot.eiot.message.core.MqProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rocketmq自动配置类
 * @author yitian
 */
@Configuration
@ConditionalOnProperty(prefix = "eiot.message", name = "producer-type", havingValue = "rocketmq")
public class RocketMqAutoConfiguration {

    @Value("${rocketmq.name-server}")
    private String nameServer;

    @Value("${rocketmq.producer.group}")
    private String group;

    @Bean
    public MqProducer<ThingModelMessage> getThingModelMessageProducer() {
        return new RocketMqProducer<>(nameServer, group);
    }

    @Bean
    public MqConsumer<ThingModelMessage> getThingModelMessageConsumer() {
        return new RocketMqConsumer<>(nameServer, ThingModelMessage.class);
    }

    @Bean
    public MqProducer<ComponentMessage> componentMessageProducer() {
        return new RocketMqProducer<>(nameServer, group);
    }

    @Bean
    public MqConsumer<ComponentMessage> componentMessageConsumer() {
        return new RocketMqConsumer<>(nameServer, ComponentMessage.class);
    }


}
