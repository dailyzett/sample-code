package org.example.eventsourcing.aggregate

import org.example.eventsourcing.command.AddItem
import org.example.eventsourcing.command.ChangeQuantity
import org.example.eventsourcing.command.RemoveItem
import org.example.eventsourcing.event.Event
import org.example.eventsourcing.event.ItemAdded
import org.example.eventsourcing.event.ItemRemoved
import org.example.eventsourcing.event.QuantityChanged
import java.lang.reflect.Method

class Cart(
    val cartId: String = "",
    val items: MutableList<Item> = mutableListOf(),
    val events: MutableList<Event> = mutableListOf()
) {
    constructor(cartId: String) : this(
        cartId = cartId,
        items = mutableListOf(),
        events = mutableListOf()
    )

    fun addItem(command: AddItem) {
        if (!containsItem(command.productNo)) {
            val event = ItemAdded(command.cartId, command.productNo, command.productName, command.quantity)
            this.applyEvent(event)
        }
    }

    private fun containsItem(productNo: String) = items.any { it.productNo == productNo }

    private fun on(event: ItemAdded) {
        this.items.add(Item(event.productNo, event.productName, event.quantity))
    }

    fun changeQuantity(command: ChangeQuantity) {
        if (command.quantity > 0) {
            this.applyEvent(QuantityChanged(command.productNo, command.quantity))
        } else {
            this.applyEvent(ItemRemoved(command.productNo))
        }
    }

    private fun on(event: QuantityChanged) {
        this.items.find { it.productNo == event.productNo }?.changeQuantity(event.quantity)
    }

    fun removeItem(event: RemoveItem) {
        applyEvent(ItemRemoved(event.productNo))
    }

    private fun on(event: ItemRemoved) {
        this.items.removeIf { it.productNo == event.productNo }
    }

    fun findItem(productNo: String) = items.find { it.productNo == productNo }

    fun applyEvent(event: Event) {
        this.applyEvent(event, true)
    }

    fun applyEvent(event: Event, isNew: Boolean) {
        val handler: Method? = javaClass.getDeclaredMethod("on", event.javaClass)
        if (handler != null) {
            handler.isAccessible = true
            handler.invoke(this, event)
            if (isNew) {
                this.events.add(event)
            }
        }
    }
}