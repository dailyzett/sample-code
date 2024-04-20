package org.example.redisexample.util

import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun getCurrentDate(): String {
    val today = LocalDate.now()
    val pattern = DateTimeFormatter.ofPattern("yyyyMMdd")
    return today.format(pattern)
}

fun getCurrentUnixTimestamp(): Long {
    return Instant.now().epochSecond
}

fun getDaysBefore(daysBack: Int, clock: Clock): String {
    val daysBefore = LocalDate.now(clock).minus(daysBack.toLong(), ChronoUnit.DAYS)
    val pattern: DateTimeFormatter? = DateTimeFormatter.ofPattern("yyyyMMdd")
    return daysBefore.format(pattern)
}
