package org.example.redisexample.util

object DailyScoreKeyGenerator {
    fun generateKey(): String {
        return "daily-score:${getCurrentDate()}"
    }
}