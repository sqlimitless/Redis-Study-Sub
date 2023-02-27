package com.sqlimitless.redisstudysub.config.redis;

import com.sqlimitless.redisstudysub.event.listener.EventStreamListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.time.Duration;

@Configuration
public class RedisStreamConfig {

    @Autowired
    private EventStreamListener eventStreamListener;

    @Bean
    public Subscription subscription(RedisConnectionFactory redisConnectionFactory) {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions options = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .build();

        StreamMessageListenerContainer listenerContainer = StreamMessageListenerContainer.create(redisConnectionFactory, options);

        Subscription subscription = listenerContainer.receiveAutoAck(Consumer.from("pub-sub-group", "instance1"),
                StreamOffset.create("pubsubtest", ReadOffset.lastConsumed()), eventStreamListener);

        listenerContainer.start();
        return subscription;
    }
}
