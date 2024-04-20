package org.example.redisexample.service.search

import org.example.redisexample.util.KeyGenerator
import org.example.redisexample.util.getCurrentUnixTimestamp
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class SearchService(
    redisTemplate: RedisTemplate<String, String>,
) {

    private val zSetOps = redisTemplate.opsForZSet()

    fun search(query: String): String {
        val key = KeyGenerator.generateSearchKeywordKey()
        zSetOps.add(key, query, getCurrentUnixTimestamp().toDouble())
        zSetOps.removeRange(key, -6, -6)
        return query
    }

    fun findKeywordRecently(): List<String> {
        val key = KeyGenerator.generateSearchKeywordKey()
        val recentSearches = zSetOps.reverseRange(key, 0, -1)
        return recentSearches?.toList() ?: emptyList()
    }
}