package org.example.eventsourcing.command.transfer

data class TransferMoney(
    val transferId: String,
    val fromAccount: String,
    val toAccount: String,
    val amount: Int,
)
