package org.example.bankapp.repository.payback

import org.example.bankapp.domain.payback.PaybackOrder
import org.example.bankapp.domain.payment.PaymentEventId
import org.springframework.data.jpa.repository.JpaRepository

interface PaybackOrderRepository : JpaRepository<PaybackOrder, PaymentEventId> {

    fun findTopByPaymentEventIdOrderByIdDesc(paymentEventId: PaymentEventId): PaybackOrder?
}