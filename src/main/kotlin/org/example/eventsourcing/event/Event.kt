package org.example.eventsourcing.event

import com.fasterxml.jackson.annotation.JsonIgnore
import org.example.eventsourcing.JsonUtil
import java.util.*

abstract class Event(
    val eventId: String,
    val time: Long,
) {
    constructor() : this(
        eventId = UUID.randomUUID().toString(),
        time = System.currentTimeMillis(),
    )

    @JsonIgnore
    fun getPayload(): String {
        return JsonUtil.toJson(this)
    }

    fun typeName(): String {
        return this.javaClass.typeName
    }
}