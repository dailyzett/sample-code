package com.jun.controller

import com.jun.model.AccountTransactions
import com.jun.repository.AccountTransactionsRepository
import com.jun.repository.CustomerRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class BalanceController(
    private val accountTransactionsRepository: AccountTransactionsRepository,
    private val customerRepository: CustomerRepository
) {

    @GetMapping("/myBalance")
    fun getBalanceDetails(@RequestParam email: String): List<AccountTransactions> {
        val customers = customerRepository.findByEmail(email)
        if (customers.isNotEmpty()) {
            val transactions =
                accountTransactionsRepository.findByCustomerIdOrderByTransactionDtDesc(customers[0].id)

            if (transactions.isNotEmpty()) return transactions
        }
        return listOf()
    }
}