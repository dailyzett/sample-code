package org.example.eventsourcing.event

data class ItemRemoved(
    val productNo: String,
) : Event()