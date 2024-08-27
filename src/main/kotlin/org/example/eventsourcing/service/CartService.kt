package org.example.eventsourcing.service

import org.example.eventsourcing.store.CartStore
import org.springframework.stereotype.Service

@Service
class CartService(
    private val cartStore: CartStore,
) {
    fun addItem(cartId: String, productNo: String, productName: String, quantity: Int) {
        val foundCart = cartStore.load(cartId)
        foundCart.addItem(productNo, productName, quantity)
        cartStore.save(foundCart)
    }

    fun changeQuantity(cartId: String, productNo: String, quantity: Int) {
        val foundCart = cartStore.load(cartId)
        foundCart.changeQuantity(productNo, quantity)
        cartStore.save(foundCart)
    }

    fun removeItem(cartId: String, productNo: String) {
        val foundCart = cartStore.load(cartId)
        foundCart.removeItem(productNo)
        cartStore.save(foundCart)
    }
}