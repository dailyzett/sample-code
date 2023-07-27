package com.example.dddstart.domain

import com.example.dddstart.domain.value.Address
import com.example.dddstart.domain.value.Receiver

class ShippingInfo(
    private val receiver: Receiver,
    private val address: Address
) {
}