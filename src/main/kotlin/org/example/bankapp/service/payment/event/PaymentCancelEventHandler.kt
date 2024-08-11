package org.example.bankapp.service.payment.event

import org.example.bankapp.service.payback.PaybackTargetService
import org.example.bankapp.service.refund.RefundService
import org.springframework.stereotype.Component

@Component
class PaymentCancelEventHandler(
    private val refundService: RefundService,
    private val paybackTargetService: PaybackTargetService,
) : GenericEventHandler<PaymentCancelEventsDto>() {

    override fun executeEvent(event: PaymentCancelEventsDto) {
        refundService.executeRefund(event)
        paybackTargetService.excludeFromPaybackTarget(event)
    }

    override fun onFailure(event: PaymentCancelEventsDto) {
        refundService.fail(event)
    }

    override fun isEventTypeSupported(event: Any): Boolean {
        return event is PaymentCancelEventsDto
    }
}