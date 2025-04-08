package com.enjoyiot.eiot.message.actor.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import com.enjoyiot.eiot.message.actor.DeliverMessage;
import com.enjoyiot.eiot.message.actor.PublishMessage;
import com.enjoyiot.eiot.message.actor.SubscribeMessage;
import com.enjoyiot.eiot.message.actor.UnsubscribeMessage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageBusActor extends AbstractActor {

    private final Map<String, List<ActorRef>> subscribers = new HashMap<>();

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(
                10, // 最大重启次数
                Duration.ofMinutes(1), // 时间窗口
                DeciderBuilder
                        .match(Exception.class, e -> SupervisorStrategy.restart())
                        .matchAny(o -> SupervisorStrategy.escalate())
                        .build()
        );
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PublishMessage.class, this::handlePublish)
                .match(SubscribeMessage.class, this::handleSubscribe)
                .match(UnsubscribeMessage.class, this::handleUnsubscribe)
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

    private void handleUnsubscribe(UnsubscribeMessage msg) {
        String topic = msg.getTopic();
        ActorRef subscriber = msg.getSubscriber();
        List<ActorRef> subscriberList = subscribers.get(topic);
        if (subscriberList != null) {
            subscriberList.remove(subscriber);
            if (subscriberList.isEmpty()) {
                subscribers.remove(topic);
            }
        }
    }
}
