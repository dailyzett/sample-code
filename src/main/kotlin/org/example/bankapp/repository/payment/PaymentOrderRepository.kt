package org.example.bankapp.repository.payment

import org.example.bankapp.domain.payment.PaymentEvents
import org.example.bankapp.domain.payment.PaymentOrderStatus
import org.example.bankapp.domain.payment.PaymentOrders
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PaymentOrderRepository : JpaRepository<PaymentOrders, Long> {
    @Query("SELECT p FROM PaymentOrders p WHERE p.paymentEvents.id.id = :paymentEventId AND p.paymentOrderStatus = :status")
    fun findByIdAndStatus(paymentEventId: String, status: PaymentOrderStatus): PaymentOrders?

    fun existsByPaymentEventsAndPaymentOrderStatus(paymentEvents: PaymentEvents, status: PaymentOrderStatus): Boolean
}