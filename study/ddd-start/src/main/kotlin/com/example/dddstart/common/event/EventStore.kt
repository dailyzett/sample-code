package com.example.dddstart.common.event

interface EventStore {
    fun save(event: Any)

    fun get(offset: Long, limit: Long): List<EventEntry>
}