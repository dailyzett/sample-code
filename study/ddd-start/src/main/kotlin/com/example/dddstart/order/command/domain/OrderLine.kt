package com.example.dddstart.order.command.domain

import com.example.dddstart.catalog.command.domain.ProductId
import com.example.dddstart.common.jpa.MoneyConverter
import jakarta.persistence.Convert
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded

@Embeddable
class OrderLine(
    @Embedded
    private val productId: ProductId,

    @Convert(converter = MoneyConverter::class)
    private val price: Money,
    private val quantity: Int,

    @Convert(converter = MoneyConverter::class)
    private val amounts: Money
) {
    private fun calculateAmounts(): Money {
        return price * quantity
    }

    fun getAmounts(): Money {
        return amounts
    }
}