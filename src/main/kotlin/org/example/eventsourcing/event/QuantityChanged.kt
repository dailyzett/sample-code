package org.example.eventsourcing.event

class QuantityChanged(
    val productNo: String,
    val quantity: Int,
) : Event()