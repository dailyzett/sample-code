package org.example.eventsourcing.aggregate

import jakarta.persistence.*

@Entity
@Table(name = "carts")
class Carts(
    @Id
    @Column(name = "cart_id")
    val cartId: String = "",

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "items", joinColumns = [JoinColumn(name = "cart_id")])
    val items: MutableList<Items> = mutableListOf()
) {
    fun addItem(productNo: String, productName: String, quantity: Int) {
        items.add(Items(productNo, productName, quantity))
    }

    fun changeQuantity(productNo: String, quantity: Int) {
        val foundItem = findItem(productNo) ?: return
        foundItem.changeQuantity(quantity)
    }

    fun removeItem(productNo: String) {
        val foundItem = findItem(productNo) ?: return
        items.remove(foundItem)
    }

    fun findItem(productNo: String) = items.find { it.productNo == productNo }
}