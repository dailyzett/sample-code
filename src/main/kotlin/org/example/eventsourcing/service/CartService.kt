package org.example.eventsourcing.service

import org.example.eventsourcing.command.AddItem
import org.example.eventsourcing.command.ChangeQuantity
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
        val foundCart = cartStore.load(command.cartId)
        foundCart.addItem(command)
        cartStore.save(foundCart)
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
}