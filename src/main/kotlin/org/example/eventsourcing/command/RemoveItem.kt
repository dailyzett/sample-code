package org.example.eventsourcing.command

data class RemoveItem(
    val cartId: String,
    val productNo: String
)
