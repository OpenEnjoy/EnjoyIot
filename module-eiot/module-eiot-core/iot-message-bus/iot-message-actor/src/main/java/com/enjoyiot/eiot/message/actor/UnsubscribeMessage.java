package com.enjoyiot.eiot.message.actor;

import akka.actor.ActorRef;

/**
 * @Author: hao.ding@insentek.com
 * @Date: 2025/4/8 10:55
 */
public class UnsubscribeMessage {
    private final String topic;
    private final ActorRef subscriber;

    public UnsubscribeMessage(String topic, ActorRef subscriber) {
        this.topic = topic;
        this.subscriber = subscriber;
    }

    public String getTopic() {
        return topic;
    }

    public ActorRef getSubscriber() {
        return subscriber;
    }
}

