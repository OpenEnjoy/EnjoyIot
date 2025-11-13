package com.enjoyiot.eiot.message.vertx;

import com.enjoyiot.eiot.message.core.ConsumerHandler;
import com.enjoyiot.eiot.message.core.MqConsumer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;

public class VertxConsumer<T> implements MqConsumer<T> {

    private final MqConsumerVerticle<T> consumerVerticle;

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    @SneakyThrows
    public VertxConsumer(Class<T> cls) {
        consumerVerticle = new MqConsumerVerticle<>(cls);
        VertxManager.getVertx().deployVerticle(consumerVerticle, stringAsyncResult -> countDownLatch.countDown());
        //等待初始化穿完成
        countDownLatch.await();
    }

    @Override
    public void consume(String route, ConsumerHandler<T> handler) {
        consumerVerticle.consume(route, handler);
    }

    public static class MqConsumerVerticle<T> extends AbstractVerticle {

        private final Class<T> cls;
        private EventBus eventBus;

        public MqConsumerVerticle(Class<T> cls) {
            this.cls = cls;
        }

        @Override
        public void start() {
            eventBus = vertx.eventBus();
            eventBus.registerCodec(new BeanCodec<>(cls));
        }

        public void consume(String topic, ConsumerHandler<T> handler) {
            eventBus.consumer(topic, (Handler<Message<T>>) msg -> handler.handler(msg.body()));
        }
    }
}
