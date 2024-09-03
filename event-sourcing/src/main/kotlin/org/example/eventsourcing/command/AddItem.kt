package org.example.eventsourcing.command

data class AddItem(
    val cartId: String,
    val productNo: String,
    val productName: String,
    val quantity: Int,
    val version: Long,
)
