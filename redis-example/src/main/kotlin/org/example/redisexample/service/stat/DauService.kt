package org.example.redisexample.service.stat

import org.example.redisexample.util.KeyGenerator
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class DauService(
    private val redisTemplate: RedisTemplate<String, String>
) {

    private val valueOps = redisTemplate.opsForValue()
    private val key = KeyGenerator.generateDauKey()

    fun incrementDauCount(userId: Int) {
        valueOps.setBit(key, userId.toLong(), true)
    }

    fun getDauCount(): Long {
        return redisTemplate.execute { conn ->
            conn.stringCommands().bitCount(key.toByteArray(), 0, -1)
        } ?: 0L
    }
}