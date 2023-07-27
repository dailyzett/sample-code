package com.example.dddstart.domain.value

data class OrderNo(
    private val number: String
) {
    fun getNumber(): String {
        return number
    }
}
