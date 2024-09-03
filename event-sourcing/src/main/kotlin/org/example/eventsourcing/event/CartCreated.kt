package org.example.eventsourcing.event

data class CartCreated(
    val cartId: String,
) : Event()