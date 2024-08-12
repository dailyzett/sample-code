package org.example.bankapp.service.event

import org.example.bankapp.domain.dto.PaybackEventsDto
import org.example.bankapp.service.payback.PaybackExecutorService
import org.springframework.stereotype.Component

@Component
class PaybackEventHandler(
    private val paybackExecutorService: PaybackExecutorService,
) : GenericEventHandler<PaybackEventsDto>() {
    override fun executeEvent(event: PaybackEventsDto) {
        paybackExecutorService.addPaybackAmount(event)
    }

    override fun onFailure(event: PaybackEventsDto) {
        paybackExecutorService.fail(event)
    }

    override fun isEventTypeSupported(event: Any): Boolean {
        return event is PaybackEventsDto
    }
}