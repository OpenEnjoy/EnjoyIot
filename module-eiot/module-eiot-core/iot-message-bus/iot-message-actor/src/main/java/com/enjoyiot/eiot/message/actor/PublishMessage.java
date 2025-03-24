package com.enjoyiot.eiot.message.actor;

public class PublishMessage<T> {
    private final String topic;
    private final T message;

    public PublishMessage(String topic, T message) {
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