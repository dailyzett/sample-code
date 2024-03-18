package org.example.redisexample.util

import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun getCurrentDate(): String {
    val today = LocalDate.now()
    val pattern = DateTimeFormatter.ofPattern("yyyyMMdd")
    return today.format(pattern)
}

fun getCurrentUnixTimestamp(): Long {
    return Instant.now().epochSecond
}
