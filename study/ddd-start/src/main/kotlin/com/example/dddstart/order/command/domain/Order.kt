package com.example.dddstart.order.command.domain

import com.example.dddstart.common.event.Events
import com.example.dddstart.common.event.OrderCanceledEvent
import com.example.dddstart.common.jpa.MoneyConverter
import jakarta.persistence.*

@Entity
@Table(name = "purchase_order")
@Access(AccessType.FIELD)
class Order {

    @EmbeddedId
    private var number: OrderNo? = null

    @Embedded
    private var orderer: Orderer? = null

    @OrderColumn(name = "line_idx")
    @CollectionTable(name = "order_line", joinColumns = [JoinColumn(name = "order_number")])
    @ElementCollection(fetch = FetchType.LAZY)
    private var orderLines: List<OrderLine>? = null

    @Convert(converter = MoneyConverter::class)
    @Column(name = "total_amounts")
    private var totalAmounts: Money? = null

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private var state: OrderState? = null

    @Embedded
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

    private fun setShippingInfo(newShippingInfo: ShippingInfo?) {
        if (newShippingInfo == null) {
            throw IllegalArgumentException("no ShippingInfo")
        }
        this.shippingInfo = newShippingInfo
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
        totalAmounts = Money(orderLines!!.stream().mapToInt { x: OrderLine -> x.getAmounts().getValue() }.sum())
    }

    fun changeShippingInfo(newShippingInfo: ShippingInfo) {
        verifyNotYetShipped()
        setShippingInfo(newShippingInfo)
    }

    fun cancel() {
        verifyNotYetShipped()
        this.state = OrderState.CANCELED
        Events.raise(OrderCanceledEvent(number!!.getNumber()))
    }

    private fun verifyNotYetShipped() {
        if (!state!!.isShippingChangeable()) {
            throw IllegalStateException("can't change shipping in $state")
        }
    }

    fun changeShipped() {}
    fun completePayment() {}
}