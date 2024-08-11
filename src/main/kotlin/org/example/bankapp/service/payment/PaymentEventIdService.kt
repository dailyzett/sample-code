package org.example.bankapp.service.payment

import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.payment.PaymentEventId
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class PaymentEventIdService {
    fun createPaymentEventId(memberId: MemberId): PaymentEventId {
        return PaymentEventId("${Instant.now().epochSecond}-${memberId.id}")
    }
}