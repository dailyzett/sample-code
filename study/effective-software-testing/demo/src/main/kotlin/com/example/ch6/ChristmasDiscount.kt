package com.example.ch6

import java.time.LocalDate
import java.time.Month

class ChristmasDiscount(
    private val clock: Clock = Clock()
) {
    fun applyDiscount(amount: Double): Double {
        val today = clock.getLocalDateNow()

        var discountPercentage = 0.0
        val isChristmas = today.month == Month.DECEMBER && today.dayOfMonth == 25

        if (isChristmas) {
            discountPercentage = 0.15
        }

        return amount - (amount * discountPercentage)
    }
}

class Clock {
    fun getLocalDateNow(): LocalDate {
        return LocalDate.now()
    }
}