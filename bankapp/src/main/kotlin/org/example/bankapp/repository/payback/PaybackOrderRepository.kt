package org.example.bankapp.repository.payback

import org.example.bankapp.domain.payback.PaybackEventId
import org.example.bankapp.domain.payback.PaybackOrderStatus
import org.example.bankapp.domain.payback.PaybackOrders
import org.example.bankapp.domain.payment.PaymentEventId
import org.springframework.data.jpa.repository.JpaRepository

interface PaybackOrderRepository : JpaRepository<PaybackOrders, PaymentEventId> {

    fun findTopByPaymentEventIdOrderByIdDesc(paymentEventId: PaymentEventId): PaybackOrders?

    fun findByPaybackEventIdAndPaybackOrderStatus(
        paybackEventId: PaybackEventId,
        paybackOrderStatus: PaybackOrderStatus
    ): PaybackOrders?
}