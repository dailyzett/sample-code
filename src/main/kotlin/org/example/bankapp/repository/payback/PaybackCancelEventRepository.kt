package org.example.bankapp.repository.payback

import org.example.bankapp.domain.payback.PaybackCancelEvent
import org.example.bankapp.domain.payback.PaybackEventId
import org.springframework.data.jpa.repository.JpaRepository

interface PaybackCancelEventRepository : JpaRepository<PaybackCancelEvent, String> {
    fun countByPaybackEventId(paybackEventId: PaybackEventId): Int
}