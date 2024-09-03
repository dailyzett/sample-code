package org.example.eventsourcing.store.jpa

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.eventsourcing.JsonUtil
import org.example.eventsourcing.aggregate.Cart
import org.example.eventsourcing.event.Event
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "cart_events")
class CartEventJpo(
    @Id
    @Column(name = "event_id")
    val eventId: String,

    @Column(name = "cart_id")
    val cartId: String,

    val type: String,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload", columnDefinition = "json")
    val payload: String,

    val time: Long,
) {
    constructor(cart: Cart, event: Event) : this(
        eventId = event.eventId,
        cartId = cart.cartId,
        payload = event.getPayload(),
        type = event.typeName(),
        time = event.time
    )

    @JsonIgnore
    fun toEvent(): Event {
        val clazz = Class.forName(type).asSubclass(Event::class.java)
        val event = JsonUtil.fromJson(payload, clazz) ?: throw IllegalStateException("")
        return event
    }
}