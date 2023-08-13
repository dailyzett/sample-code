package com.example.dddstart.common.event

class OrderCanceledEvent(
    private val orderNumber: String
) : Event() {
    fun getOrderNumber(): String = orderNumber
}