package org.example.redisexample.config

import org.example.redisexample.domain.entity.Member
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
    @Bean
    fun connectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory()
    }

    @Bean
    fun redisTemplate(connectionFactory: LettuceConnectionFactory): RedisTemplate<String, String> {
        val template = RedisTemplate<String, String>()
        template.connectionFactory = connectionFactory
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = GenericToStringSerializer(Any::class.java)
        return template
    }

    @Bean
    fun redisTemplateMembers(connectionFactory: LettuceConnectionFactory): RedisTemplate<String, Member> {
        val template = RedisTemplate<String, Member>()
        template.connectionFactory = connectionFactory
        template.keySerializer = StringRedisSerializer()
        return template
    }
}