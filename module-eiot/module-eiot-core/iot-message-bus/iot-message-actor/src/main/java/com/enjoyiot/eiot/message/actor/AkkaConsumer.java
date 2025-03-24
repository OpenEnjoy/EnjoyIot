package com.enjoyiot.eiot.message.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.enjoyiot.eiot.message.actor.actor.ConsumerActor;
import com.enjoyiot.eiot.message.core.ConsumerHandler;
import com.enjoyiot.eiot.message.core.MqConsumer;

public class AkkaConsumer<T> implements MqConsumer<T> {
    private final ActorRef messageBus;
    private final ActorSystem actorSystem;

    public AkkaConsumer(ActorRef messageBus, ActorSystem actorSystem) {
        this.messageBus = messageBus;
        this.actorSystem = actorSystem;
    }

    @Override
    public void consume(String topic, ConsumerHandler<T> handler) {
        ActorRef consumerActor = actorSystem.actorOf(Props.create(ConsumerActor.class, handler));
        messageBus.tell(new SubscribeMessage(topic, consumerActor), ActorRef.noSender());
    }
}