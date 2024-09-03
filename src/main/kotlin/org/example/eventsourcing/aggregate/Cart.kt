package org.example.eventsourcing.aggregate

import com.fasterxml.jackson.annotation.JsonIgnore
import org.example.eventsourcing.JsonUtil
import org.example.eventsourcing.command.AddItem
import org.example.eventsourcing.command.ChangeQuantity
import org.example.eventsourcing.command.CreateCart
import org.example.eventsourcing.command.RemoveItem
import org.example.eventsourcing.event.*
import org.example.eventsourcing.eventsourcing.Snapshot
import org.slf4j.LoggerFactory
import java.lang.reflect.Method

class Cart(
    var cartId: String = "",
    val items: MutableList<Item> = mutableListOf(),
    val events: MutableList<Event> = mutableListOf(),
    var deleted: Boolean = false,
    var version: Long = 0,
    @JsonIgnore var snapshot: Snapshot? = null
) {
    private val log = LoggerFactory.getLogger(this.javaClass)

    data class Item(
        val productNo: String = "",
        val productName: String = "",
        var quantity: Int = 0
    ) {
        fun changeQuantity(quantity: Int) {
            this.quantity = quantity
        }

        fun addQuantity() = quantity++
    }

    constructor(cartId: String) : this(
        cartId = cartId,
        items = mutableListOf(),
        events = mutableListOf()
    )

    constructor(command: CreateCart) : this() {
        this.applyEvent(CartCreated(command.cartId))
    }

    fun delete() {
        this.applyEvent(CartDeleted(cartId))
    }

    private fun on(event: CartDeleted) {
        this.markDelete()
    }

    private fun markDelete() {
        deleted = true
    }

    fun addItem(command: AddItem) {
        if (!containsItem(command.productNo)) {
            val event = ItemAdded(command.cartId, command.productNo, command.productName, command.quantity)
            this.applyEvent(event)
        } else {
            val foundItem: Item = this.findItem(command.productNo)!!
            this.applyEvent(QuantityChanged(command.productNo, foundItem.quantity + 1))
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

    private fun on(event: CartCreated) {
        this.cartId = event.cartId
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

    fun snapshot(): Snapshot {
        val time = this.events[this.events.size - 1].time
        return Snapshot(JsonUtil.toJson(this), time)
    }

    fun takeSnapshot() {
        val eventTime = this.events.last().time
        val currentTime = System.currentTimeMillis()

        snapshot = snapshot?.let {
            if (eventTime - it.time > 600000) Snapshot(JsonUtil.toJson(this), eventTime)
            else it
        } ?: Snapshot(JsonUtil.toJson(this), currentTime)
    }
}