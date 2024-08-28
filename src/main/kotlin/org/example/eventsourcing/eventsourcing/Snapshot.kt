package org.example.eventsourcing.eventsourcing

data class Snapshot(
    val payload: String,
    val time: Long,
)