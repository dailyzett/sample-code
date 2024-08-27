package org.example.eventsourcing.store.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload", columnDefinition = "json")
    val payload: String,

    val time: Long,
) {
    constructor(cart: Cart, event: Event) : this(
        eventId = event.eventId,
        cartId = cart.cartId,
        payload = event.getPayload(),
        time = event.time
    )
}