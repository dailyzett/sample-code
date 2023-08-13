package com.example.dddstart.common.event

open class Event(
    private val timestamp: Long = System.currentTimeMillis()
) {
    fun getTimestamp(): Long = timestamp
}