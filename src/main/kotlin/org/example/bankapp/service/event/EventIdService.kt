package org.example.bankapp.service.event

import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.payback.PaybackEventId
import org.example.bankapp.domain.payment.PaymentEventId
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class EventIdService {
    fun createPaymentEventId(memberId: MemberId): PaymentEventId {
        return PaymentEventId("${Instant.now().epochSecond}-${memberId.id}")
    }

    fun createPaybackEventId(memberId: Long): PaybackEventId {
        return PaybackEventId("${Instant.now().epochSecond}-${memberId}")
    }
}