package org.example.bankapp.domain.dto

data class PaybackRequestDto(
    val memberId: String,
    val percent: Int
)