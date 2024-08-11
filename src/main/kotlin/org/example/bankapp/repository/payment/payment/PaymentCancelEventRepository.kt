package org.example.bankapp.repository.payment.payment

import org.example.bankapp.domain.payment.PaymentEventId
import org.example.bankapp.domain.payment.cancel.PaymentCancelEvent
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentCancelEventRepository : JpaRepository<PaymentCancelEvent, String> {
    fun findByPaymentEventId(paymentEventId: PaymentEventId): PaymentCancelEvent?
}