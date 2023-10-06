package com.jun.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.sql.Date

@Entity
@Table(name = "account_transactions")
class AccountTransactions(
    @Id
    @Column(name = "transaction_id")
    var transactionId: String? = null,

    @Column(name = "account_number")
    var accountNumber: Long = 0,

    @Column(name = "customer_id")
    var customerId: Int = 0,

    @Column(name = "transaction_dt")
    var transactionDt: Date? = null,

    @Column(name = "transaction_summary")
    var transactionSummary: String? = null,

    @Column(name = "transaction_type")
    var transactionType: String? = null,

    @Column(name = "transaction_amt")
    var transactionAmt: Int = 0,

    @Column(name = "closing_balance")
    var closingBalance: Int = 0,

    @Column(name = "create_dt")
    var createDt: String? = null,
)