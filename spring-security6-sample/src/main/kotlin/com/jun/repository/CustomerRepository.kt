package com.jun.repository

import com.jun.model.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer, Long> {
    fun findByEmail(email: String): List<Customer>
}