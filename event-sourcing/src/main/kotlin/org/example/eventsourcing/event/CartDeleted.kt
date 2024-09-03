package org.example.eventsourcing.event

data class CartDeleted(
    val cartId: String,
) : Event()
