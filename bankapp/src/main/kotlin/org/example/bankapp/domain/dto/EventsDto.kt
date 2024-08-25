package org.example.bankapp.domain.dto

import org.example.bankapp.domain.payback.PaybackCancelEvents
import org.example.bankapp.domain.payback.PaybackEvents
import org.example.bankapp.domain.payment.PaymentEventId
import org.example.bankapp.domain.payment.PaymentEvents
import org.example.bankapp.domain.payment.cancel.PaymentCancelEvents

data class PaymentEventsDto(
    val event: PaymentEvents,
    val amount: Int,
)

data class PaymentCancelEventsDto(
    val cancelEvent: PaymentCancelEvents,
)

data class PaybackEventsDto(
    val paybackEvents: PaybackEvents,
    val paybackPercent: Int,
    val paymentEventId: PaymentEventId,
)

data class PaybackCancelEventsDto(
    val paybackCancelEvents: PaybackCancelEvents,
)