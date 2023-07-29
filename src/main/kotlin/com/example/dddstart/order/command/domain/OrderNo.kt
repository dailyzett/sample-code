package com.example.dddstart.order.command.domain

data class OrderNo(
    private val number: String
) {
    fun getNumber(): String {
        return number
    }
}
