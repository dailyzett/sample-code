package org.example.redisexample.service.message

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class RedisMessageSubscriber : MessageListener {

    private val log = KotlinLogging.logger { }

    @Async("taskExecutor")
    override fun onMessage(message: Message, pattern: ByteArray?) {
        log.info { "${String(message.channel)} 채널에서 수신 메시지: ${String(message.body)} " }
    }
}