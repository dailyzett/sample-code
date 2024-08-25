package org.example.bankapp.service.event

import org.example.bankapp.domain.dto.PaybackCancelEventsDto
import org.example.bankapp.service.refund.RefundService
import org.springframework.stereotype.Component

@Component
class PaybackCancelEventHandler(
    private val refundService: RefundService,
) : GenericEventHandler<PaybackCancelEventsDto>() {
    override fun executeEvent(event: PaybackCancelEventsDto) {
        refundService.executePaybackRefund(event)
    }

    override fun onFailure(event: PaybackCancelEventsDto) {
        refundService.fail(event)
    }

    override fun isEventTypeSupported(event: Any): Boolean {
        return event is PaybackCancelEventsDto
    }
}