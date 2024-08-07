package org.example.bankapp.repository.payment

import org.example.bankapp.domain.payment.PaymentEvent
import org.example.bankapp.domain.payment.PaymentEventId
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentEventRepository : JpaRepository<PaymentEvent, PaymentEventId> {
}