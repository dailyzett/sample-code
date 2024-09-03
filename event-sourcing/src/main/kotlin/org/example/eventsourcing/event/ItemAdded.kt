package org.example.eventsourcing.event

data class ItemAdded(
    val cartId: String,
    val productNo: String,
    val productName: String,
    val quantity: Int,
) : Event()