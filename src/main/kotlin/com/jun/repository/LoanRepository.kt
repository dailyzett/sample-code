package com.jun.repository

import com.jun.model.Loans
import org.springframework.data.jpa.repository.JpaRepository

interface LoanRepository : JpaRepository<Loans, Long> {
    //    @PreAuthorize("hasRole('USER')")
    fun findByCustomerIdOrderByStartDtDesc(customerId: Int): List<Loans>
}