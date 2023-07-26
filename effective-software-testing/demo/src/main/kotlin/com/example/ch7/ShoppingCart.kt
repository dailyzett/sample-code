package com.example.ch7

import java.time.LocalDate

class ShoppingCart(
    var value: Double = 0.0
) {
    private var readyForDelivery: Boolean = false

    fun markAsReadyForDelivery(estimatedDayOfDelivery: LocalDate) {
        readyForDelivery = true
        // ...
    }

    fun isReadyForDelivery(): Boolean {
        return readyForDelivery
    }
}
