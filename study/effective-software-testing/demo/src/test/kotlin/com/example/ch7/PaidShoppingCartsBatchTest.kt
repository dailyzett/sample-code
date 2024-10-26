package com.example.ch7

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate
import java.util.*

@ExtendWith(MockitoExtension::class)
class PaidShoppingCartsBatchTest(
    @Mock private val db: ShoppingCartRepository,
    @Mock private val deliveryCenter: DeliveryCenter,
    @Mock private val notifier: CustomerNotifier,
    @Mock private val sap: SAP
) {

    @Test
    fun `the whole process happens`() {
        val batch = PaidShoppingCartsBatch(db, deliveryCenter, notifier, sap)

        val someCart = ShoppingCart()
        assertThat(someCart.isReadyForDelivery()).isFalse()

        val someDate = LocalDate.now()
        `when`(db.cartsPaidToday()).thenReturn(listOf(someCart))
        `when`(deliveryCenter.deliver(someCart)).thenReturn(someDate)

        batch.processAll()

        verify(deliveryCenter).deliver(someCart)
        verify(notifier).sendEstimateDeliveryNotification(someCart)
        verify(db).persist(someCart)
        verify(sap).cartReadyForDelivery(someCart)

        assertThat(someCart.isReadyForDelivery()).isTrue()
    }
}