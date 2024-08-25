package org.example.bankapp.service.payback

import org.example.bankapp.common.exception.NotFoundPaymentCancelEvent
import org.example.bankapp.common.exception.NotFoundPaymentEvent
import org.example.bankapp.common.exception.PaymentCancelNotCompletedException
import org.example.bankapp.common.exception.PaymentNotCompletedException
import org.example.bankapp.domain.dto.PaymentCancelEventsDto
import org.example.bankapp.domain.dto.PaymentEventsDto
import org.example.bankapp.domain.payback.PaybackOrderStatus.NOT_NEED_TO_PAYBACK
import org.example.bankapp.domain.payback.PaybackOrderStatus.NOT_STARTED
import org.example.bankapp.domain.payback.PaybackOrders
import org.example.bankapp.domain.payment.PaymentEvents
import org.example.bankapp.domain.payment.PaymentOrderStatus
import org.example.bankapp.domain.payment.PaymentOrders
import org.example.bankapp.domain.payment.cancel.PaymentCancelEvents
import org.example.bankapp.repository.payback.PaybackOrderRepository
import org.example.bankapp.repository.payment.PaymentCancelEventRepository
import org.example.bankapp.repository.payment.PaymentEventRepository
import org.example.bankapp.repository.payment.PaymentOrderRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PaybackOrderService(
    private val paymentEventRepository: PaymentEventRepository,
    private val paymentCancelEventRepository: PaymentCancelEventRepository,
    private val paybackOrderRepository: PaybackOrderRepository,
    private val paymentOrderRepository: PaymentOrderRepository,
) {
    @Transactional
    fun addPaybackOrder(paymentEventsDto: PaymentEventsDto) {
        val paymentEvents: PaymentEvents = paymentEventsDto.event

        val foundPaymentEvent = paymentEventRepository.findByIdOrNull(paymentEvents.id)
            ?: throw NotFoundPaymentEvent("")

        if (!foundPaymentEvent.getIsPaymentDone()) throw PaymentNotCompletedException("")

        val target = PaybackOrders(
            paybackEventId = null,
            paymentEventId = paymentEvents.id,
            paybackOrderStatus = NOT_STARTED,
            paymentAmount = paymentEventsDto.amount,
            paybackAmount = null,
            paybackTargetId = paymentEvents.payingMember.memberId
        )

        paybackOrderRepository.save(target)
    }

    @Transactional
    fun excludeFromPaybackOrder(paybackCancelEventsDto: PaymentCancelEventsDto) {
        val cancelEvent: PaymentCancelEvents = paybackCancelEventsDto.cancelEvent
        val paymentEventId = cancelEvent.paymentEventId

        val foundCancelEvent: PaymentCancelEvents = paymentCancelEventRepository.findByPaymentEventId(paymentEventId)
            ?: throw NotFoundPaymentEvent("")
        if (!foundCancelEvent.getIsCancelDone()) throw PaymentCancelNotCompletedException("")

        val paymentOrders: PaymentOrders =
            paymentOrderRepository.findByIdAndStatus(paymentEventId.id, PaymentOrderStatus.CANCELLED)
                ?: throw NotFoundPaymentCancelEvent("")

        val target = PaybackOrders(
            paybackEventId = null,
            paymentEventId = paymentEventId,
            paybackOrderStatus = NOT_NEED_TO_PAYBACK,
            paymentAmount = paymentOrders.amount,
            paybackAmount = null,
            paybackTargetId = cancelEvent.cancellingMember.memberId
        )

        paybackOrderRepository.save(target)
    }
}