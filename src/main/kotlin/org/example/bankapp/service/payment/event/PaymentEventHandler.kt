package org.example.bankapp.service.payment.event

import kotlinx.coroutines.*
import org.example.bankapp.common.exception.PaymentExecutionTimeoutException
import org.example.bankapp.service.payment.PaymentExecutorService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
class PaymentEventHandler(
    private val paymentExecutorService: PaymentExecutorService,
) {

    private val logger = LoggerFactory.getLogger(PaymentEventHandler::class.java)

    @TransactionalEventListener(
        classes = [PaymentEventsDto::class],
        phase = TransactionPhase.AFTER_COMMIT
    )
    fun handle(paymentEventsDto: PaymentEventsDto) {
        CoroutineScope(Dispatchers.Default).launch {
            logger.info("[Payment Event Handle] :: ${paymentEventsDto.event.id}")
            try {
                withTimeout(5000) {
                    delay(6000)
                    paymentExecutorService.executePaymentOrder(paymentEventsDto)
                }
            } catch (e: TimeoutCancellationException) {
                throw PaymentExecutionTimeoutException("")
            }
        }
    }
}
