package com.jun.controller

import com.jun.model.AccountTransactions
import com.jun.repository.AccountTransactionsRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class BalanceController(
    private val accountTransactionsRepository: AccountTransactionsRepository,
) {

    @GetMapping("/myBalance")
    fun getBalanceDetails(@RequestParam id: Int): List<AccountTransactions> {
        return accountTransactionsRepository.findByCustomerIdOrderByTransactionDtDesc(id)
    }
}