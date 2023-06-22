package com.example.ch7


class ShoppingCartRepositoryImpl : ShoppingCartRepository {
    override fun cartsPaidToday(): List<ShoppingCart>? {
        return null
    }

    override fun persist(cart: ShoppingCart) {
        return
    }
}

class CustomerNotifierImpl : CustomerNotifier {
    override fun sendEstimateDeliveryNotification(cart: ShoppingCart) {
        return
    }

}

class SAPSoapWebService : SAP {
    override fun cartReadyForDelivery(cart: ShoppingCart) {
        return
    }
}

class SMTPCustomerNotifier : CustomerNotifier {
    override fun sendEstimateDeliveryNotification(cart: ShoppingCart) {
        return
    }
}