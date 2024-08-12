package org.example.bankapp.service.event

import org.example.bankapp.domain.dto.PaymentCancelEventsDto
import org.example.bankapp.service.payback.PaybackOrderService
import org.example.bankapp.service.refund.RefundService
import org.springframework.stereotype.Component

@Component
class PaymentCancelEventHandler(
    private val refundService: RefundService,
    private val paybackOrderService: PaybackOrderService,
) : GenericEventHandler<PaymentCancelEventsDto>() {

    override fun executeEvent(event: PaymentCancelEventsDto) {
        refundService.executeRefund(event)
        paybackOrderService.excludeFromPaybackOrder(event)
    }

    override fun onFailure(event: PaymentCancelEventsDto) {
        refundService.fail(event)
    }

    override fun isEventTypeSupported(event: Any): Boolean {
        return event is PaymentCancelEventsDto
    }
}