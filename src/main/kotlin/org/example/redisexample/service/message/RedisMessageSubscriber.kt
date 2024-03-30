package org.example.redisexample.service.message

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.redisexample.domain.req.MessageReq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class RedisMessageSubscriber : MessageListener {

    @Autowired
    lateinit var mapper: ObjectMapper

    private val log = KotlinLogging.logger { }

    @Async("taskExecutor")
    override fun onMessage(message: Message, pattern: ByteArray?) {
        val jsonString = String(message.body)
        val channel = String(message.channel)

        try {
            val obj = mapper.readValue<MessageReq>(jsonString)
            log.info { "$channel 채널에서 수신 메시지: $obj" }
        } catch (e: Exception) {
            log.info { "$channel 채널에서 수신 메시지: ${String(message.body)} " }
        }
    }
}