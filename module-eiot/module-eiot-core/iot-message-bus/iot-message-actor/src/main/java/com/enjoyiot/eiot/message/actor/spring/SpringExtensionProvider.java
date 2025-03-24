package com.enjoyiot.eiot.message.actor.spring;

import akka.actor.AbstractExtensionId;
import akka.actor.ExtendedActorSystem;

public class SpringExtensionProvider extends AbstractExtensionId<SpringExtension> {

    private static final SpringExtensionProvider provider = new SpringExtensionProvider();

    public static SpringExtensionProvider getInstance() {
        return provider;
    }

    @Override
    public SpringExtension createExtension(ExtendedActorSystem extendedActorSystem) {
        return new SpringExtension();
    }
}
