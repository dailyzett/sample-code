package org.example.bankapp.repository.payback

import org.example.bankapp.domain.payback.PaybackEvent
import org.example.bankapp.domain.payback.PaybackEventId
import org.springframework.data.jpa.repository.JpaRepository

interface PaybackEventRepository : JpaRepository<PaybackEvent, PaybackEventId> {
}