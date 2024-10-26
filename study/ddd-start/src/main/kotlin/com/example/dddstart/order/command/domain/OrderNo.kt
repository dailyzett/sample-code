package com.example.dddstart.order.command.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class OrderNo(
    @Column(name = "order_number")
    private val number: String
) : Serializable {
    fun getNumber(): String {
        return number
    }
}

