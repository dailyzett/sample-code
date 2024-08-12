package org.example.bankapp.domain.dto

import org.example.bankapp.domain.payback.PaybackEvent
import org.example.bankapp.domain.payment.PaymentEvent
import org.example.bankapp.domain.payment.PaymentEventId
import org.example.bankapp.domain.payment.cancel.PaymentCancelEvent

data class PaymentEventsDto(
    val event: PaymentEvent,
    val amount: Int,
)

data class PaymentCancelEventsDto(
    val cancelEvent: PaymentCancelEvent,
)

data class PaybackEventsDto(
    val paybackEvent: PaybackEvent,
    val paybackPercent: Int,
    val paymentEventId: PaymentEventId,
)