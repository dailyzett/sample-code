package org.example.bankapp.repository.payment.payback

import org.example.bankapp.domain.payback.PaybackTarget
import org.example.bankapp.domain.payment.PaymentEventId
import org.springframework.data.jpa.repository.JpaRepository

interface PaybackTargetRepository : JpaRepository<PaybackTarget, PaymentEventId>