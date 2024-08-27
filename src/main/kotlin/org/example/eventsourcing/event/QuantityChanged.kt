package org.example.eventsourcing.event

data class QuantityChanged(
    val productNo: String,
    val quantity: Int,
) : Event()