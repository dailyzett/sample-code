package org.example.eventsourcing.aggregate

data class Item(
    val productNo: String = "",
    val productName: String = "",
    var quantity: Int = 0
) {
    fun changeQuantity(quantity: Int) {
        this.quantity = quantity
    }

    fun addQuantity() = quantity++
}