package org.example.eventsourcing.aggregate

import org.example.eventsourcing.event.Event
import org.example.eventsourcing.event.ItemAdded
import org.example.eventsourcing.event.ItemRemoved
import org.example.eventsourcing.event.QuantityChanged

class Cart(
    val cartId: String = "",
    private val items: MutableList<Item> = mutableListOf(),
    val events: MutableList<Event> = mutableListOf()
) {
    constructor(cartId: String) : this(
        cartId = cartId,
        items = mutableListOf(),
        events = mutableListOf()
    )

    fun addItem(productNo: String, productName: String, quantity: Int) {
        items.add(Item(productNo, productName, quantity))
        val event = ItemAdded(productNo, productName, quantity)
        this.events.add(event)
    }

    fun changeQuantity(productNo: String, quantity: Int) {
        val foundItem = findItem(productNo) ?: return
        foundItem.changeQuantity(quantity)

        val event = QuantityChanged(productNo, quantity)
        this.events.add(event)
    }

    fun removeItem(productNo: String) {
        val foundItem = findItem(productNo) ?: return
        items.remove(foundItem)

        val event = ItemRemoved(productNo)
        this.events.add(event)
    }

    fun findItem(productNo: String) = items.find { it.productNo == productNo }
}