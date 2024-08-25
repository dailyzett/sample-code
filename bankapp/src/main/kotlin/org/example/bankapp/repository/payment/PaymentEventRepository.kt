package org.example.bankapp.repository.payment

import org.example.bankapp.domain.payment.PaymentEventId
import org.example.bankapp.domain.payment.PaymentEvents
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentEventRepository : JpaRepository<PaymentEvents, PaymentEventId> {
}