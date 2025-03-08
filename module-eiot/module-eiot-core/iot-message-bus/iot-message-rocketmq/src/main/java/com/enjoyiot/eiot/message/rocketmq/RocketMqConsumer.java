

package com.enjoyiot.eiot.message.rocketmq;


import cn.hutool.json.JSONUtil;
import com.enjoyiot.eiot.message.core.ConsumerHandler;
import com.enjoyiot.eiot.message.core.MqConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import java.nio.charset.StandardCharsets;

@Slf4j
public class RocketMqConsumer<T> implements MqConsumer<T> {

    private String nameServer;

    private final Class<T> msgType;


    public RocketMqConsumer(String nameServer, Class<T> cls) {
        this.nameServer = nameServer;
        this.msgType = cls;
    }


    @Override
    public void consume(String topic, ConsumerHandler<T> handler) {
        try {
            String groupId = handler.getClass()
                    .getName().replace(".", "")
                    .replace("$", "").replace("/", "");
            log.warn("consume groupId:{}",groupId);
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupId);
            consumer.setNamesrvAddr(nameServer);
            consumer.subscribe(topic, "*");
            consumer.registerMessageListener((MessageListenerConcurrently) (messages, context) -> {
                for (MessageExt message : messages) {
                    T msg = JSONUtil.toBean(new String(message.getBody(), StandardCharsets.UTF_8), msgType);
                    handler.handler(msg);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
        } catch (Throwable e) {
            log.error("consume error", e);
        }
    }

}
