package org.example.redisexample.service.message

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.redisexample.domain.req.MessageReq
import org.example.redisexample.util.KeyGenerator
import org.example.redisexample.util.toMap
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.data.redis.connection.stream.StreamRecords
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val stringRedisTemplate: StringRedisTemplate,
    private val mapper: ObjectMapper,
) {

    private val log = KotlinLogging.logger { }

    fun publish(channel: String, text: String): Long {
        val subscribeChannels = stringRedisTemplate.convertAndSend(channel, text)
        return subscribeChannels
    }

    fun publishObject(channel: String, messageReq: MessageReq): Long {
        val messageAsString = mapper.writeValueAsString(messageReq)
        val subscribeChannels = stringRedisTemplate.convertAndSend(channel, messageAsString)
        return subscribeChannels
    }

    fun publishEvent(paramKey: String, text: String): Long {
        val key = KeyGenerator.generateQueueKey(paramKey)
        val eventsLength = stringRedisTemplate.opsForList().rightPush(key, text)
        return eventsLength ?: 0
    }

    /*    @Async
        fun blockingPop(key: String) {
            while (true) {
                val value = stringRedisTemplate.opsForList().leftPop(key, 10, TimeUnit.NANOSECONDS)
                value?.let { log.info { "받은 문자열: $it" } }
            }
        }*/

    fun appendingMessage(key: String, req: MessageReq) {
        val streamRecord = StreamRecords.string(req.toMap()).withStreamKey(key)
        stringRedisTemplate.opsForStream<String, String>().add(streamRecord)
    }

    fun doSomethingByMessage(msg: MapRecord<String, String, String>?) {
        log.info { "MessageId: ${msg?.id}" }
        log.info { "Stream: ${msg?.stream}" }
        log.info { "Body: ${msg?.value}}" }
    }
}