package org.example.eventsourcing.store

import jakarta.transaction.Transactional
import org.example.eventsourcing.aggregate.Cart
import org.example.eventsourcing.event.Event
import org.example.eventsourcing.store.jpa.CartEventJpo
import org.example.eventsourcing.store.jpa.CartEventRepository
import org.example.eventsourcing.store.jpa.CartJpo
import org.example.eventsourcing.store.jpa.CartRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
@Transactional
class CartStore(
    private val cartRepository: CartRepository,
    private val cartEventRepository: CartEventRepository,
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    fun save(cart: Cart) {
        cart.takeSnapshot()
        cartRepository.save(CartJpo(cart))
        cartEventRepository.saveAll(cart.events.map { event -> CartEventJpo(cart, event) })
    }

    fun load(cartId: String): Cart {
        val cartJpo: CartJpo = cartRepository.findByIdOrNull(cartId)
            ?: throw NoSuchElementException("")

        val eventJpos: List<CartEventJpo> = cartEventRepository.findByCartIdOrderByTimeAsc(cartId)
        val events: List<Event> = eventJpos.map { eventJpo -> eventJpo.toEvent() }

        val cart: Cart = cartJpo.toCart()
        events.forEach { event -> cart.applyEvent(event, false) }
        cart.version = cartJpo.version
        return cart
    }

    fun exists(cartId: String) = cartRepository.existsById(cartId)
}