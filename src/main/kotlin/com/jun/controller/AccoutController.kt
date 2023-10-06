package com.jun.controller

import com.jun.model.Accounts
import com.jun.repository.AccountsRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AccoutController(
    private val accountsRepository: AccountsRepository,
) {

    @GetMapping("/myAccount")
    fun getAccountDetails(@RequestParam id: Int): Accounts? {
        return accountsRepository.findByCustomerId(id)
    }
}