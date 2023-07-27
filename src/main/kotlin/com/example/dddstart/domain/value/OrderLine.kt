package com.example.dddstart.domain.value

data class OrderLine(
    private val product: Product,
    private val price: Int,
    private val quantity: Int,
    private val amounts: Money
)
