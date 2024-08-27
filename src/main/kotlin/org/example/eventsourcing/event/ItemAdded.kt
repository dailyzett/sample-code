package org.example.eventsourcing.event

class ItemAdded(
    val productNo: String,
    val productName: String,
    val quantity: Int,
) : Event()