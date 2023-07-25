package com.example.dddstart.domain

import com.example.dddstart.domain.enum.OrderState

class Order {
    private var orderLines: List<OrderLine>? = null
    private var totalAmounts: Money? = null
    private var state: OrderState? = null
    private var shippingInfo: ShippingInfo? = null

    constructor(orderLines: List<OrderLine>, shippingInfo: ShippingInfo) {
        setOrderLines(orderLines)
        setShippingInfo(shippingInfo)
    }

    private fun setShippingInfo(shippingInfo: ShippingInfo?) {
        if (shippingInfo == null) {
            throw IllegalArgumentException("no ShippingInfo")
        }
        this.shippingInfo = shippingInfo
    }

    private fun setOrderLines(orderLines: List<OrderLine>?) {
        verifyAtLeastOneOrMoreOrderLines(orderLines)
        this.orderLines = orderLines
        calculateTotalAmounts()
    }

    private fun verifyAtLeastOneOrMoreOrderLines(orderLines: List<OrderLine>?) {
        if (orderLines.isNullOrEmpty()) {
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
        verifyNotYetShipped()
        setShippingInfo(newShippingInfo)
    }

    fun cancel() {
        verifyNotYetShipped()
        this.state = OrderState.CANCELED
    }

    private fun verifyNotYetShipped() {
        if (!state!!.isShippingChangeable()) {
            throw IllegalStateException("can't change shipping in $state")
        }
    }

    fun changeShipped() {}
    fun completePayment() {}
}