package com.example.ch7

import java.time.LocalDate

interface ShoppingCartRepository {
    fun cartsPaidToday(): List<ShoppingCart>?
    fun persist(cart: ShoppingCart)
}

interface CustomerNotifier {
    fun sendEstimateDeliveryNotification(cart: ShoppingCart)
}

interface SAP {
    fun cartReadyForDelivery(cart: ShoppingCart)
}

interface DeliveryCenter {
    fun deliver(cart: ShoppingCart): LocalDate
}