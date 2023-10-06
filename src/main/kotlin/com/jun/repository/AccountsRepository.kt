package com.jun.repository

import com.jun.model.Accounts
import org.springframework.data.jpa.repository.JpaRepository

interface AccountsRepository : JpaRepository<Accounts, Long> {
    fun findByCustomerId(customerId: Int): Accounts?
}