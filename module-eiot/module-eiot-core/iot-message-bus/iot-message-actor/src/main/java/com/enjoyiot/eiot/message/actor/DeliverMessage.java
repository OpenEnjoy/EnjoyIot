package com.enjoyiot.eiot.message.actor;

// 分发消息
public class DeliverMessage<T> {
    private final String topic;
    private final T message;

    public DeliverMessage(String topic, T message) {
        this.topic = topic;
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public T getMessage() {
        return message;
    }
}
