package org.example.bankapp.service.payment.event

import org.example.bankapp.domain.payment.PaymentEvent
import org.example.bankapp.domain.payment.cancel.PaymentCancelEvent

data class PaymentEventsDto(
    val event: PaymentEvent,
    val amount: Int,
)

data class PaymentCancelEventsDto(
    val cancelEvent: PaymentCancelEvent,
)