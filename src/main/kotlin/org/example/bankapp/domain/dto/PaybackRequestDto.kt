package org.example.bankapp.domain.dto

import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.payment.PaymentEventId

data class PaybackRequestDto(
    val memberId: Long,
    val paymentEventId: PaymentEventId,
    val percent: Int
)

data class PaybackCancelRequestDto(
    val cancellingMemberId: MemberId,
    val paybackEventId: String
)