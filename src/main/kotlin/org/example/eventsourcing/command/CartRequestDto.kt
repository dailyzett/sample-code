package org.example.eventsourcing.command

data class CartRequestDto(
    val cartId: String,
    val productNo: String,
    val productName: String,
    val quantity: Int
)