package com.example.ch7

class PaidShoppingCartsBatch(
    private val db: ShoppingCartRepository,
    private val deliveryCenter: DeliveryCenter,
    private val notifier: CustomerNotifier,
    private val sap: SAP
) {
    fun processAll() {
        val paidShoppingCarts: List<ShoppingCart> = db.cartsPaidToday() ?: listOf()

        for (cart: ShoppingCart in paidShoppingCarts) {
            val estimateDayOfDelivery = deliveryCenter.deliver(cart)

            cart.markAsReadyForDelivery(estimateDayOfDelivery)
            db.persist(cart)

            notifier.sendEstimateDeliveryNotification(cart)

            sap.cartReadyForDelivery(cart)
        }
    }
}