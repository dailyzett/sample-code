package org.example.redisexample.service.message

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val stringRedisTemplate: StringRedisTemplate,
) {
    fun publish(channel: String, text: String): Long {
        val subscribeChannels = stringRedisTemplate.convertAndSend(channel, text)
        return subscribeChannels
    }
}