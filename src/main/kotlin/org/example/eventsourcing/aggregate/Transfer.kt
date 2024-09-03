package org.example.eventsourcing.aggregate

import org.example.eventsourcing.command.transfer.CancelTransfer
import org.example.eventsourcing.command.transfer.CompleteDeposit
import org.example.eventsourcing.command.transfer.TransferMoney

class Transfer {
    var transferId = ""
    var fromAccount = ""
    val withdrawed: Boolean = false

    var toAccount = ""
    var deposited: Boolean = false

    var amount: Int = 0
    var completed = false

    constructor(command: TransferMoney) {
        this.transferId = command.transferId
        this.fromAccount = command.fromAccount
        this.toAccount = command.toAccount
        this.amount = command.amount
    }

    constructor(command: CompleteDeposit) {
        this.deposited = true
        this.complete()
    }

    fun complete() {
        if (this.withdrawed && this.deposited) {
            this.completed = true
        }
    }

    fun cancel(command: CancelTransfer) {
        this.completed = false
    }
}