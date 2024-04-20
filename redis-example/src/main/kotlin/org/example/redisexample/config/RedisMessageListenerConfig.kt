package org.example.redisexample.config

import jakarta.annotation.PostConstruct
import org.example.redisexample.service.message.MessageService
import org.example.redisexample.service.message.RedisMessageSubscriber
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.connection.stream.Consumer
import org.springframework.data.redis.connection.stream.ReadOffset
import org.springframework.data.redis.connection.stream.StreamOffset
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import org.springframework.data.redis.stream.StreamMessageListenerContainer
import java.time.Duration

@Configuration
class RedisMessageListenerConfig {

    @Autowired
    lateinit var connectionFactory: LettuceConnectionFactory

    @Autowired
    lateinit var stringRedisTemplate: StringRedisTemplate

    @Autowired
    lateinit var messageService: MessageService

    /**
     * 소비자 그룹을 따로 생성하지 않았을 때, 주석된 두 줄을 해제하면 메시지를 처리할 수 있습니다.
     */
    @PostConstruct
    fun init() {
//        val streamListener = StreamListener()
        val options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
            .pollTimeout(Duration.ofMillis(10000)).build()

        val messageContainer = StreamMessageListenerContainer.create(connectionFactory, options)
//        messageContainer.receive(StreamOffset.fromStart("Email"), streamListener)
        messageContainer.receive(
            Consumer.from("my-group", "my-consumer"),
            StreamOffset.create("Email", ReadOffset.lastConsumed())
        ) { msg ->
            messageService.doSomethingByMessage(msg)
            stringRedisTemplate.opsForStream<String, String>().acknowledge("my-group", msg)
        }
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