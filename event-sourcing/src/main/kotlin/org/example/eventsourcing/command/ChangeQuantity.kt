package org.example.eventsourcing.command

import org.example.eventsourcing.exception.QuantityZeroException

data class ChangeQuantity(
    val cartId: String,
    val productNo: String,
    val quantity: Int
) {
    fun validate() {
        if (cartId.isBlank()) {
            throw IllegalArgumentException()
        }

        if (quantity <= 0) {
            throw QuantityZeroException("")
        }
    }
}
