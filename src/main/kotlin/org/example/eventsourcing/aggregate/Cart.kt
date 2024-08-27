package org.example.eventsourcing.aggregate

import org.example.eventsourcing.command.AddItem
import org.example.eventsourcing.command.ChangeQuantity
import org.example.eventsourcing.command.RemoveItem
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

    fun addItem(command: AddItem) {
        items.add(Item(command.productNo, command.productName, command.quantity))
        val event = ItemAdded(command.productNo, command.productName, command.quantity)
        this.events.add(event)
    }

    fun changeQuantity(command: ChangeQuantity) {
        val foundItem = findItem(command.productNo) ?: return
        foundItem.changeQuantity(command.quantity)

        val event = QuantityChanged(command.productNo, command.quantity)
        this.events.add(event)
    }

    fun removeItem(command: RemoveItem) {
        val foundItem = findItem(command.productNo) ?: return
        items.remove(foundItem)

        val event = ItemRemoved(command.productNo)
        this.events.add(event)
    }

    fun findItem(productNo: String) = items.find { it.productNo == productNo }
}