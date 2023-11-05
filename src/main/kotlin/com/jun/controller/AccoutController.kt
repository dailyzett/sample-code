package com.jun.controller

import com.jun.model.Accounts
import com.jun.repository.AccountsRepository
import com.jun.repository.CustomerRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AccoutController(
    private val accountsRepository: AccountsRepository,
    private val customerRepository: CustomerRepository
) {

    @GetMapping("/myAccount")
    fun getAccountDetails(@RequestParam email: String): Accounts? {
        val customers = customerRepository.findByEmail(email)
        if (customers.isNotEmpty()) {
            val accounts = accountsRepository.findByCustomerId(customers[0].id)
            if (accounts != null)
                return accounts
        }
        return null
    }
}