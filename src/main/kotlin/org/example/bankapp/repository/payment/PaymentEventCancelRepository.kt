package org.example.bankapp.repository.payment

import org.example.bankapp.domain.payment.cancel.PaymentCancelEvent
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentEventCancelRepository : JpaRepository<PaymentCancelEvent, Long> {

}