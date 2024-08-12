package org.example.bankapp.service.event

import org.example.bankapp.domain.dto.PaymentEventsDto
import org.example.bankapp.service.payback.PaybackOrderService
import org.example.bankapp.service.payment.PaymentExecutorService
import org.springframework.stereotype.Component

@Component
class PaymentEventHandler(
    private val paymentExecutorService: PaymentExecutorService,
    private val paybackOrderService: PaybackOrderService,
) : GenericEventHandler<PaymentEventsDto>() {

    override fun executeEvent(event: PaymentEventsDto) {
        paymentExecutorService.executePaymentOrder(event)
        paybackOrderService.addPaybackOrder(event)
    }

    override fun onFailure(event: PaymentEventsDto) {
        paymentExecutorService.fail(event)
    }

    override fun isEventTypeSupported(event: Any): Boolean {
        return event is PaymentEventsDto
    }
}
