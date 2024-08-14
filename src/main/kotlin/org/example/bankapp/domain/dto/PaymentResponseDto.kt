package org.example.bankapp.domain.dto

import org.example.bankapp.domain.payment.PaymentEvents

data class PaymentResponseDto(
    val eventId: String,
    val payingMemberId: Long
) {
    companion object {
        fun of(paymentEvents: PaymentEvents): PaymentResponseDto {
            return PaymentResponseDto(
                eventId = paymentEvents.id.id,
                payingMemberId = paymentEvents.payingMember.memberId.id
            )
        }
    }
}

data class PaymentCancelResponseDto(
    val cancelEventId: String,
)