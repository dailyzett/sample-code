package com.example.dddstart.domain

import com.example.dddstart.domain.enum.OrderState

class Order {
    private var orderLines: List<OrderLine>? = null
    private var totalAmounts: Money? = null
    private var state: OrderState? = null
    private var shippingInfo: ShippingInfo? = null

    private fun setOrderLines(orderLines: List<OrderLine>) {
        verifyAtLeastOneOrMoreOrderLines(orderLines)
        this.orderLines = orderLines
        calculateTotalAmounts()
    }

    private fun verifyAtLeastOneOrMoreOrderLines(orderLines: List<OrderLine>) {
        if (orderLines.isEmpty()) {
            throw IllegalArgumentException("no OrderLine")
        }
    }

    private fun calculateTotalAmounts() {
        val sum = orderLines!!.stream()
            .mapToInt { obj: OrderLine -> obj.getAmounts() }
            .sum()
        this.totalAmounts = Money(sum)
    }

    fun changeShippingInfo(newShippingInfo: ShippingInfo) {
        if (!state!!.isShippingChangeable()) {
            throw IllegalStateException("can't change shipping in $state")
        }
        this.shippingInfo = newShippingInfo
    }

    fun changeShipped() {}
    fun changeShippingInfo() {}
    fun cancel() {}
    fun completePayment() {}
}