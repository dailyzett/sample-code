package org.example.bankapp.repository.payment

import org.example.bankapp.domain.payment.PaymentEvent
import org.example.bankapp.domain.payment.PaymentOrder
import org.example.bankapp.domain.payment.PaymentOrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PaymentOrderRepository : JpaRepository<PaymentOrder, Long> {
    fun findByPaymentEvent(paymentEvent: PaymentEvent): List<PaymentOrder>

    @Query("SELECT p FROM PaymentOrder p WHERE p.paymentEvent.id.id = :paymentEventId AND p.paymentOrderStatus = :status")
    fun findByIdAndStatus(paymentEventId: String, status: PaymentOrderStatus): PaymentOrder?

    @Query(
        """SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END 
                FROM PaymentOrder p 
                WHERE p.paymentEvent = :paymentEvent 
                AND p.paymentOrderStatus = :status"""
    )
    fun existsByEventAndStatus(paymentEvent: PaymentEvent, status: PaymentOrderStatus): Boolean

    fun existsByPaymentEventAndPaymentOrderStatus(paymentEvent: PaymentEvent, status: PaymentOrderStatus): Boolean
}