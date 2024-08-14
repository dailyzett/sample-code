package org.example.bankapp.repository.payback

import org.example.bankapp.domain.payback.PaybackEventId
import org.example.bankapp.domain.payback.PaybackEvents
import org.springframework.data.jpa.repository.JpaRepository

interface PaybackEventRepository : JpaRepository<PaybackEvents, PaybackEventId> {
}