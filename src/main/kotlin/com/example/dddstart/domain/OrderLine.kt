package com.example.dddstart.domain

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