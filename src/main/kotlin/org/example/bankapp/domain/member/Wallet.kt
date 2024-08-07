package org.example.bankapp.domain.member

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Wallet(

    @Column(name = "current_balance")
    val currentBalance: Int
)