package com.example.dddstart.order.command.domain

import jakarta.persistence.*

@Embeddable
class ShippingInfo(
    @Embedded
    private val receiver: Receiver,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "zipCode", column = Column(name = "shipping_zip_code")),
        AttributeOverride(name = "address1", column = Column(name = "shipping_addr1")),
        AttributeOverride(name = "address2", column = Column(name = "shipping_addr2"))
    )
    private val address: Address
) {
}