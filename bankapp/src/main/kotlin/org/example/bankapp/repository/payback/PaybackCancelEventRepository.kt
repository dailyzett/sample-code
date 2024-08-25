package org.example.bankapp.repository.payback

import org.example.bankapp.domain.payback.PaybackCancelEvents
import org.example.bankapp.domain.payback.PaybackEventId
import org.springframework.data.jpa.repository.JpaRepository

interface PaybackCancelEventRepository : JpaRepository<PaybackCancelEvents, String> {
    fun countByPaybackEventId(paybackEventId: PaybackEventId): Int
}