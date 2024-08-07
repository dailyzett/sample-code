package org.example.bankapp.domain.dto

import org.example.bankapp.domain.member.MemberId

data class PaymentRequestDto (
    val payingMemberId: MemberId,
    val amount: Int,
)