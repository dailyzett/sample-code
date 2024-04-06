package org.example.redisexample.config

import jakarta.annotation.PostConstruct
import org.example.redisexample.controller.message.StreamListener
import org.example.redisexample.service.message.RedisMessageSubscriber
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.connection.stream.StreamOffset
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import org.springframework.data.redis.stream.StreamMessageListenerContainer
import java.time.Duration

@Configuration
class RedisMessageListenerConfig {

    @Autowired
    lateinit var connectionFactory: LettuceConnectionFactory
    
    @PostConstruct
    fun init() {
        val streamListener = StreamListener()
        val options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
            .pollTimeout(Duration.ofMillis(100)).build()

        val messageContainer = StreamMessageListenerContainer.create(connectionFactory, options)
        messageContainer.receive(StreamOffset.fromStart("Email"), streamListener)
        messageContainer.start()
    }

    @Bean
    fun messageListener(): MessageListenerAdapter {
        return MessageListenerAdapter(RedisMessageSubscriber())
    }

    @Bean
    fun redisContainer(): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(connectionFactory)
        container.addMessageListener(messageListener(), topic())
        return container
    }

    @Bean
    fun topic(): ChannelTopic {
        return ChannelTopic("hello")
    }
}