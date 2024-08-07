package org.example.bankapp.domain.member

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class BalanceLimit(
    @Column(name = "single_payment_limit")
    var singlePaymentLimit: Int,

    @Column(name = "daily_payment_limit")
    var dailyPaymentLimit: Int,

    @Column(name = "monthly_payment_limit")
    var monthlyPaymentLimit: Int
)