package com.enjoyiot.eiot.message.actor.actor;

import akka.actor.AbstractActor;
import com.enjoyiot.eiot.message.actor.DeliverMessage;
import com.enjoyiot.eiot.message.core.ConsumerHandler;

public class ConsumerActor<T> extends AbstractActor {
    private final ConsumerHandler<T> handler;

    public ConsumerActor(ConsumerHandler<T> handler) {
        this.handler = handler;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(DeliverMessage.class, msg -> {
                    @SuppressWarnings("unchecked")
                    DeliverMessage<T> deliverMessage = (DeliverMessage<T>) msg;
                    handler.handler(deliverMessage.getMessage());
                })
                .build();
    }
}
