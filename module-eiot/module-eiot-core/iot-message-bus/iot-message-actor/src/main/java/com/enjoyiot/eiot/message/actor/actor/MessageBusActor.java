package com.enjoyiot.eiot.message.actor.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.enjoyiot.eiot.message.actor.DeliverMessage;
import com.enjoyiot.eiot.message.actor.PublishMessage;
import com.enjoyiot.eiot.message.actor.SubscribeMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageBusActor extends AbstractActor {
    private final Map<String, List<ActorRef>> subscribers = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PublishMessage.class, this::handlePublish)
                .match(SubscribeMessage.class, this::handleSubscribe)
                .build();
    }

    private void handlePublish(PublishMessage<?> msg) {
        String topic = msg.getTopic();
        List<ActorRef> subscriberList = subscribers.getOrDefault(topic, new ArrayList<>());
        for (ActorRef subscriber : subscriberList) {
            subscriber.tell(new DeliverMessage<>(topic, msg.getMessage()), self());
        }
    }

    private void handleSubscribe(SubscribeMessage msg) {
        String topic = msg.getTopic();
        ActorRef subscriber = msg.getSubscriber();
        subscribers.computeIfAbsent(topic, k -> new ArrayList<>()).add(subscriber);
    }
}
