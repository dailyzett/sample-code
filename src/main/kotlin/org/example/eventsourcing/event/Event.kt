package org.example.eventsourcing.event

import com.fasterxml.jackson.annotation.JsonIgnore
import org.example.eventsourcing.JsonUtil
import java.util.*

abstract class Event(
    val eventId: String,
    val time: Long,
    val cartId: String
) {
    constructor() : this(
        eventId = UUID.randomUUID().toString(),
        time = System.currentTimeMillis(),
        cartId = ""
    )

    @JsonIgnore
    fun getPayload(): String {
        return JsonUtil.toJson(this)
    }
}