package com.example.ch

import java.math.BigDecimal
import java.math.BigDecimal.*
import java.util.Collections

class Basket {
    private var _totalValue: BigDecimal = ZERO
    private val basket: MutableMap<Product, Int> = hashMapOf()

    val totalValue: BigDecimal
        get() = _totalValue

    fun add(product: Product?, qtyToAdd: Int) {
        assert(product != null) { "Product must not be null" }
        assert(qtyToAdd > 0) { "Quantity must be greater than zero" }

        val oldTotalValue: BigDecimal = _totalValue

        val existingQuantity = basket.getOrDefault(product, 0)
        val newQuantity = existingQuantity + qtyToAdd

        basket[product!!] = newQuantity

        val valueAlreadyInTheCart = product?.price?.multiply(valueOf(existingQuantity.toLong()))
        val newFinalValueForTheObject = product?.price?.multiply(valueOf(newQuantity.toLong()))

        //totalValue = totalValue - valueAlreadyInTheCart + newFinalValueForTheObject
        _totalValue = _totalValue.subtract(valueAlreadyInTheCart).add(newFinalValueForTheObject)

        //assert basket contains product
        assert(basket.containsKey(product)) { "Basket must contain product" }

        //assert totalValue compareTo oldTotalValue == 1
        assert(_totalValue.compareTo(oldTotalValue) == 1) { "Total value must be greater than old total value" }
        assert(invariant()) { "Invariant does not hold" }
    }

    fun remove(product: Product?) {
        //assert product is not null
        assert(product != null) { "Product must not be null" }
        //assert basket contains product
        assert(basket.containsKey(product)) { "Basket must contain product" }

        val qty = basket[product]

        _totalValue = _totalValue.subtract(product?.price?.multiply(valueOf(qty!!.toLong())))

        basket.remove(product)

        assert(!basket.containsKey(product)) { "Product is still in the basket" }
        assert(invariant()) { "Invariant does not hold" }
    }

    private fun invariant(): Boolean {
        return _totalValue >= ZERO
    }

    fun quantityOf(product: Product): Int? {
        assert(basket.containsKey(product)) { "Basket must contain product" }
        return basket[product]
    }

    fun products(): Set<Product> {
        return Collections.unmodifiableSet(basket.keys)
    }
}