package com.example.dddstart.common.event

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class OrderCanceledEventHandler(
    private val refundService: RefundService
) {

    @EventListener(OrderCanceledEvent::class)
    fun handle(event: OrderCanceledEvent) {
        refundService.refund(event.getOrderNumber())
    }
}