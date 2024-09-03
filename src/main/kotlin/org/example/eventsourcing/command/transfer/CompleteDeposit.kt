package org.example.eventsourcing.command.transfer

data class CompleteDeposit(
    val transferId: String,
)
