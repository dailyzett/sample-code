package com.jun.controller

import com.jun.model.Loans
import com.jun.repository.LoanRepository
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class LoansController(
    private val loanRepository: LoanRepository
) {

    @GetMapping("/myLoans")
    @PostAuthorize("hasRole('USER')")
    fun getLoanDetails(@RequestParam id: Int): List<Loans> {
        return loanRepository.findByCustomerIdOrderByStartDtDesc(id);
    }
}