package org.example.eventsourcing.store.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.eventsourcing.aggregate.Cart

@Entity
@Table(name = "carts")
class CartJpo(
    @Id
    @Column(name = "cart_id")
    val cartId: String = "",
) {
    constructor(cart: Cart) : this(
        cartId = cart.cartId,
    )

    fun toCart(): Cart {
        return Cart(cartId)
    }
}