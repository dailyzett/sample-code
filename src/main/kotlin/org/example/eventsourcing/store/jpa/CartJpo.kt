package org.example.eventsourcing.store.jpa

import jakarta.persistence.*
import org.example.eventsourcing.JsonUtil
import org.example.eventsourcing.aggregate.Cart
import org.example.eventsourcing.eventsourcing.Snapshot
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
        version = cart.version,
    ) {
        if (cart.snapshot != null) {
            this.snapshot = cart.snapshot!!.payload
            this.snapShotTime = cart.snapshot!!.time
        }
    }

    fun toCart(): Cart {
        if ((this.snapShotTime ?: 0) > 0) {
            val cart = JsonUtil.fromJson(this.snapshot, Cart::class.java)!!
            cart.snapshot = Snapshot(this.snapshot, this.snapShotTime!!)
            return cart;
        }

        return JsonUtil.fromJson("{}", Cart::class.java)!!
    }
}