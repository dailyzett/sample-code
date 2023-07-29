package com.example.dddstart.order.command.domain

import com.example.dddstart.catalog.command.domain.Product

class OrderLine(
    private val product: Product,
    private val price: Int,
    private val quantity: Int,
    private val amounts: Int
) {
    private fun calculateAmounts(): Int {
        return price * quantity
    }

    fun getAmounts(): Int {
        return amounts
    }
}