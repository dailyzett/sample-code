package com.example.dddstart.common.event

class EventEntry(
    private val id: Long,
    val type: String,
    val contentType: String,
    val payload: String,
    val timestamp: Long
) {
    constructor(
        type: String,
        contentType: String,
        payload: String
    ) : this(0, type, contentType, payload, System.currentTimeMillis())
}