package org.example.bankapp.domain.dto

import org.example.bankapp.domain.payment.PaymentEvent

data class PaymentResponseDto(
    val eventId: String,
    val payingMemberId: Long
) {
    companion object {
        fun of(paymentEvent: PaymentEvent): PaymentResponseDto {
            return PaymentResponseDto(
                eventId = paymentEvent.id.id,
                payingMemberId = paymentEvent.payingMember.memberId.id
            )
        }
    }
}

data class PaymentCancelResponseDto(
    val cancelEventId: String,
)