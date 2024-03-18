package org.example.redisexample.util

object KeyGenerator {
    fun generateDailyScoreKey(): String {
        return "daily-score:${getCurrentDate()}"
    }
}