package org.example.eventsourcing.aggregate

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Items(
    @Column(name = "product_no")
    val productNo: String = "",

    @Column(name = "product_name")
    val productName: String = "",

    @Column(name = "price")
    val price: Int = 0,

    @Column(name = "quantity")
    private var quantity: Int = 0
) {
    fun changeQuantity(quantity: Int) {
        this.quantity = quantity
    }
}