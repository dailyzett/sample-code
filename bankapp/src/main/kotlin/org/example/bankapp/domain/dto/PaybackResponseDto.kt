package org.example.bankapp.domain.dto

data class PaybackResponseDto(
    val paybackEventId: String
)

data class PaybackCancelResponseDto(
    val paybackCancelEventId: String
)