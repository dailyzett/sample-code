package org.example.eventsourcing.store

import jakarta.transaction.Transactional
import org.example.eventsourcing.aggregate.Cart
import org.example.eventsourcing.event.Event
import org.example.eventsourcing.store.jpa.CartEventJpo
import org.example.eventsourcing.store.jpa.CartEventRepository
import org.example.eventsourcing.store.jpa.CartJpo
import org.example.eventsourcing.store.jpa.CartRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
@Transactional
class CartStore(
    private val cartRepository: CartRepository,
    private val cartEventRepository: CartEventRepository,
) {
    fun save(cart: Cart) {
        cartRepository.save(CartJpo(cart))
        cartEventRepository.saveAll(cart.events.map { event -> CartEventJpo(cart, event) })
    }

    fun load(cartId: String): Cart {
        val cartJpo = cartRepository.findByIdOrNull(cartId)
            ?: throw NoSuchElementException("")

        val eventJpos: List<CartEventJpo> = cartEventRepository.findByCartIdOrderByTimeAsc(cartId)
        val events: List<Event> = eventJpos.map { eventJpo -> eventJpo.toEvent() }

        val cart = cartJpo.toCart()
        events.forEach { event -> cart.applyEvent(event, false) }

        return cart
    }
}