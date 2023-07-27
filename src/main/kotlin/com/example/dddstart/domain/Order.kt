package com.example.dddstart.domain

import com.example.dddstart.domain.enum.OrderState
import com.example.dddstart.domain.value.Money
import com.example.dddstart.domain.value.OrderNo

class Order {
    private var id: OrderNo? = null
    private var orderer: Orderer? = null
    private var orderLines: List<OrderLine>? = null
    private var totalAmounts: Money? = null
    private var state: OrderState? = null
    private var shippingInfo: ShippingInfo? = null

    constructor(
        orderer: Orderer,
        orderLines: List<OrderLine>,
        shippingInfo: ShippingInfo,
        orderState: OrderState
    ) {
        setOrderer(orderer)
        setOrderLines(orderLines)
        setShippingInfo(shippingInfo)
    }

    private fun setOrderer(orderer: Orderer?) {
        if (orderer == null) {
            throw IllegalArgumentException("no Orderer")
        }
        this.orderer = orderer
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