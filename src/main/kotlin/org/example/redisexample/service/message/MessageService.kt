package org.example.redisexample.service.message

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.redisexample.domain.req.MessageReq
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val stringRedisTemplate: StringRedisTemplate,
    private val mapper: ObjectMapper,
) {
    fun publish(channel: String, text: String): Long {
        val subscribeChannels = stringRedisTemplate.convertAndSend(channel, text)
        return subscribeChannels
    }

    fun publishObject(channel: String, messageReq: MessageReq): Long {
        val messageAsString = mapper.writeValueAsString(messageReq)
        val subscribeChannels = stringRedisTemplate.convertAndSend(channel, messageAsString)
        return subscribeChannels
    }
}