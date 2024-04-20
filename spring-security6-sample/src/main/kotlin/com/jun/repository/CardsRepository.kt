package com.jun.repository

import com.jun.model.Cards
import org.springframework.data.jpa.repository.JpaRepository

interface CardsRepository : JpaRepository<Cards, Long> {
    fun findByCustomerId(customerId: Int): List<Cards>
}