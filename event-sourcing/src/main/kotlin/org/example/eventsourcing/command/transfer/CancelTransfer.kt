package org.example.eventsourcing.command.transfer

data class CancelTransfer(
    val transferId: String,
)
