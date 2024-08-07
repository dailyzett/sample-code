package org.example.bankapp.repository.payment

import org.example.bankapp.domain.payment.PaymentEvent
import org.example.bankapp.domain.payment.PaymentOrder
import org.example.bankapp.domain.payment.PaymentOrderStatus
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentOrderRepository : JpaRepository<PaymentOrder, Long> {
    fun findByPaymentEvent(paymentEvent: PaymentEvent): List<PaymentOrder>

    fun existsByPaymentEventAndPaymentOrderStatus(paymentEvent: PaymentEvent, status: PaymentOrderStatus): Boolean
}