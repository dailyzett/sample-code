package org.example.eventsourcing.store.jpa

import jakarta.persistence.*
import org.example.eventsourcing.aggregate.Cart
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "carts")
class CartJpo(
    @Id
    @Column(name = "cart_id")
    val cartId: String = "",

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "snapshot_payload", columnDefinition = "json")
    var snapshot: String = "",

    @Column(name = "snapshot_time")
    var snapShotTime: Long? = 0,

    @Version
    val version: Long
) {
    constructor(cart: Cart) : this(
        cartId = cart.cartId,
        snapshot = cart.snapshot().payload,
        snapShotTime = cart.snapshot().time,
        version = cart.version
    )

    fun toCart(): Cart {
        return Cart(this.cartId, this.version)
    }
}