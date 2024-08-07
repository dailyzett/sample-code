package org.example.bankapp.service.payment.event

import org.example.bankapp.domain.payment.PaymentEvent

data class PaymentEventsDto(
    val event: PaymentEvent,
    val amount: Int,
)