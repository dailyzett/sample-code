package com.jun.repository

import com.jun.model.AccountTransactions
import org.springframework.data.jpa.repository.JpaRepository

interface AccountTransactionsRepository : JpaRepository<AccountTransactions, Long> {
    fun findByCustomerIdOrderByTransactionDtDesc(customerId: Int): List<AccountTransactions>
}