package com.example.dddstart.order.command.domain

class ShippingInfo(
    private val receiver: Receiver,
    private val address: Address
) {
}