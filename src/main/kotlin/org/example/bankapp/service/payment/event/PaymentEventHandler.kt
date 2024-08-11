package org.example.bankapp.service.payment.event

import org.example.bankapp.service.payback.PaybackTargetService
import org.example.bankapp.service.payment.PaymentExecutorService
import org.springframework.stereotype.Component

@Component
class PaymentEventHandler(
    private val paymentExecutorService: PaymentExecutorService,
    private val paybackTargetService: PaybackTargetService,
) : GenericEventHandler<PaymentEventsDto>() {

    override fun executeEvent(event: PaymentEventsDto) {
        paymentExecutorService.executePaymentOrder(event)
        paybackTargetService.addPaybackTarget(event)
    }

    override fun onFailure(event: PaymentEventsDto) {
        paymentExecutorService.fail(event)
    }

    override fun isEventTypeSupported(event: Any): Boolean {
        return event is PaymentEventsDto
    }
}
