package org.example.eventsourcing.store.jpa

import jakarta.persistence.*
import org.example.eventsourcing.aggregate.Cart

@Entity
@Table(name = "carts")
class CartJpo(
    @Id
    @Column(name = "cart_id")
    val cartId: String = "",

    @Version
    val version: Long = 0,
) {
    constructor(cart: Cart) : this(
        cartId = cart.cartId,
    )

    fun toCart(): Cart {
        return Cart(this.cartId, this.version)
    }
}