package org.example.redisexample.util

object KeyGenerator {
    fun generateDailyScoreKey(): String {
        return "daily-score:${getCurrentDate()}"
    }

    fun generateSearchKeywordKey(): String {
        return "search-keyword:123"
    }

    fun generateUserHashKey(): String {
        return "user:hash"
    }
}