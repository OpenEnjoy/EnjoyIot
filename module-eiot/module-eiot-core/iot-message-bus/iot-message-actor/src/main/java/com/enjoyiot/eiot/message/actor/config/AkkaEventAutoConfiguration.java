package com.enjoyiot.eiot.message.actor.config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.enjoyiot.eiot.common.thing.ComponentMessage;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.message.actor.AkkaConsumer;
import com.enjoyiot.eiot.message.actor.AkkaProducer;
import com.enjoyiot.eiot.message.actor.actor.MessageBusActor;
import com.enjoyiot.eiot.message.actor.spring.SpringExtensionProvider;
import com.enjoyiot.eiot.message.core.MqConsumer;
import com.enjoyiot.eiot.message.core.MqProducer;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AkkaEventAutoConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public ActorSystem actorSystem() {
        Config dispatcherConfig = ConfigFactory.parseResources("my-dispatcher.conf");
        Config combinedConfig = dispatcherConfig.withFallback(ConfigFactory.load());
        ActorSystem system = ActorSystem.create("IoTMessageBusSystem", combinedConfig);
        SpringExtensionProvider.getInstance().get(system).initialize(applicationContext);
        return system;
    }

    @Bean
    public ActorRef messageBus(ActorSystem actorSystem) {
        return actorSystem.actorOf(Props.create(MessageBusActor.class), "messageBus");
    }

    @Bean
    public MqProducer<ThingModelMessage> producer(ActorRef messageBus) {
        return new AkkaProducer<>(messageBus);
    }

    @Bean
    public MqConsumer<ThingModelMessage> consumer(ActorRef messageBus, ActorSystem actorSystem) {
        return new AkkaConsumer<>(messageBus, actorSystem);
    }

    @Bean
    public MqProducer<ComponentMessage> componentMessageProducer(ActorRef messageBus) {
        return new AkkaProducer<>(messageBus);
    }

    @Bean
    public MqConsumer<ComponentMessage> componentMessageConsumer(ActorRef messageBus, ActorSystem actorSystem) {
        return new AkkaConsumer<>(messageBus, actorSystem);
    }
}
