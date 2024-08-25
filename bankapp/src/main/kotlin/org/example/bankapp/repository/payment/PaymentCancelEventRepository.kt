package org.example.bankapp.repository.payment

import org.example.bankapp.domain.payment.PaymentEventId
import org.example.bankapp.domain.payment.cancel.PaymentCancelEvents
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentCancelEventRepository : JpaRepository<PaymentCancelEvents, String> {
    fun findByPaymentEventId(paymentEventId: PaymentEventId): PaymentCancelEvents?
}