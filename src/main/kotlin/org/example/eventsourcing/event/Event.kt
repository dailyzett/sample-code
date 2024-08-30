package org.example.eventsourcing.event

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.example.eventsourcing.JsonUtil
import java.util.*

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = QuantityChanged::class, name = "QuantityChanged"),
    JsonSubTypes.Type(value = ItemAdded::class, name = "ItemAdded"),
    JsonSubTypes.Type(value = ItemRemoved::class, name = "ItemRemoved"),
    JsonSubTypes.Type(value = CartCreated::class, name = "CartCreated"),
    JsonSubTypes.Type(value = CartDeleted::class, name = "CartDeleted"),
)
abstract class Event(
    val eventId: String,
    val time: Long,
    protected val rehydration: Boolean,
) {
    constructor() : this(
        eventId = UUID.randomUUID().toString(),
        time = System.currentTimeMillis(),
        rehydration = true
    )

    @JsonIgnore
    fun getPayload(): String {
        return JsonUtil.toJson(this)
    }

    fun typeName(): String {
        return this.javaClass.typeName
    }
}