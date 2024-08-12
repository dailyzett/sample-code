package org.example.bankapp.domain.dto

import org.example.bankapp.domain.payment.PaymentEventId

data class PaybackRequestDto(
    val memberId: Long,
    val paymentEventId: PaymentEventId,
    val percent: Int
)