package com.jun.controller

import com.jun.model.Loans
import com.jun.repository.CustomerRepository
import com.jun.repository.LoanRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class LoansController(
    private val loanRepository: LoanRepository,
    private val customerRepository: CustomerRepository
) {

    @GetMapping("/myLoans")
    fun getLoanDetails(@RequestParam email: String): List<Loans> {
        val customers = customerRepository.findByEmail(email)
        if (customers.isNotEmpty()) {
            val loans = loanRepository.findByCustomerIdOrderByStartDtDesc(customers[0].id)
            if (customers.isNotEmpty()) {
                return loans
            }
        }
        return listOf()
    }
}