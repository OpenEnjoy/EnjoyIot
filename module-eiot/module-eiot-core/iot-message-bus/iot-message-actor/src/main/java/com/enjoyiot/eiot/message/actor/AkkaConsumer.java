package com.enjoyiot.eiot.message.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.enjoyiot.eiot.message.actor.actor.ConsumerActor;
import com.enjoyiot.eiot.message.core.ConsumerHandler;
import com.enjoyiot.eiot.message.core.MqConsumer;

import java.util.*;

public class AkkaConsumer<T> implements MqConsumer<T> {
    private final ActorRef messageBus;
    private final ActorSystem actorSystem;
    private final Map<String, List<ActorRef>> consumerActors = new HashMap<>();
    private final Map<ConsumerHandler<T>, ActorRef> handlerToActorMap = new HashMap<>();

    public AkkaConsumer(ActorRef messageBus, ActorSystem actorSystem) {
        this.messageBus = messageBus;
        this.actorSystem = actorSystem;
    }

    @Override
    public synchronized void consume(String topic, ConsumerHandler<T> handler) {
        ActorRef consumerActor = handlerToActorMap.computeIfAbsent(handler, h ->
                actorSystem.actorOf(Props.create(ConsumerActor.class, h))
        );
        consumerActors.computeIfAbsent(topic, k -> new ArrayList<>()).add(consumerActor);
        messageBus.tell(new SubscribeMessage(topic, consumerActor), ActorRef.noSender());
    }

    public synchronized void unsubscribe(String topic, ConsumerHandler<T> handler) {
        ActorRef consumerActor = handlerToActorMap.get(handler);
        if (consumerActor != null) {
            messageBus.tell(new UnsubscribeMessage(topic, consumerActor), ActorRef.noSender());
            List<ActorRef> actors = consumerActors.get(topic);
            if (actors != null) {
                actors.remove(consumerActor);
                if (actors.isEmpty()) {
                    consumerActors.remove(topic);
                }
            }
            if (!isHandlerSubscribedToOtherTopics(handler)) {
                actorSystem.stop(consumerActor);
                handlerToActorMap.remove(handler);
            }
        }
    }

    private boolean isHandlerSubscribedToOtherTopics(ConsumerHandler<T> handler) {
        return consumerActors.values().stream()
                .flatMap(List::stream)
                .anyMatch(actor -> actor.equals(handlerToActorMap.get(handler)));
    }
}