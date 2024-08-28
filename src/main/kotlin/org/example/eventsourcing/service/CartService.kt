package org.example.eventsourcing.service

import jakarta.persistence.OptimisticLockException
import org.example.eventsourcing.aggregate.Cart
import org.example.eventsourcing.command.AddItem
import org.example.eventsourcing.command.ChangeQuantity
import org.example.eventsourcing.command.CreateCart
import org.example.eventsourcing.command.RemoveItem
import org.example.eventsourcing.store.CartStore
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CartService(
    private val cartStore: CartStore,
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    fun addItem(command: AddItem) {
        val foundCart: Cart = cartStore.load(command.cartId)
        foundCart.addItem(command)

        try {
            cartStore.save(foundCart)
        } catch (e: OptimisticLockException) {
            throw RuntimeException("")
        }
    }

    fun changeQuantity(command: ChangeQuantity) {
        command.validate()

        val foundCart = cartStore.load(command.cartId)
        foundCart.changeQuantity(command)
        cartStore.save(foundCart)
    }

    fun removeItem(command: RemoveItem) {
        val foundCart = cartStore.load(command.cartId)
        foundCart.removeItem(command)
        cartStore.save(foundCart)
    }

    fun createCart(command: CreateCart) {
        if (this.cartStore.exists(command.cartId)) throw RuntimeException("Cart already exists")
        val cart = Cart(command.cartId)
        cartStore.save(cart)
    }
}