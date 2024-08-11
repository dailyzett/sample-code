package org.example.bankapp.service.payback

import org.example.bankapp.common.exception.NotFoundPaymentEvent
import org.example.bankapp.common.exception.PaymentCancelNotCompletedException
import org.example.bankapp.common.exception.PaymentNotCompletedException
import org.example.bankapp.domain.payback.PaybackTarget
import org.example.bankapp.domain.payment.PaymentEvent
import org.example.bankapp.domain.payment.cancel.PaymentCancelEvent
import org.example.bankapp.repository.payment.payback.PaybackTargetRepository
import org.example.bankapp.repository.payment.payment.PaymentCancelEventRepository
import org.example.bankapp.repository.payment.payment.PaymentEventRepository
import org.example.bankapp.service.payment.event.PaymentCancelEventsDto
import org.example.bankapp.service.payment.event.PaymentEventsDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PaybackTargetService(
    private val paymentEventRepository: PaymentEventRepository,
    private val paymentCancelEventRepository: PaymentCancelEventRepository,
    private val paybackTargetRepository: PaybackTargetRepository
) {
    @Transactional
    fun addPaybackTarget(paymentEventsDto: PaymentEventsDto) {
        val paymentEvent: PaymentEvent = paymentEventsDto.event

        val foundPaymentEvent = paymentEventRepository.findByIdOrNull(paymentEvent.id)
            ?: throw NotFoundPaymentEvent("")

        if (!foundPaymentEvent.getIsPaymentDone()) throw PaymentNotCompletedException("")

        val target = PaybackTarget(
            paymentEventId = paymentEvent.id,
            paybackTargetId = paymentEvent.payingMember.memberId,
            paymentAmount = paymentEventsDto.amount,
            isPaybackDone = false,
            isCancelDone = false
        )
        paybackTargetRepository.save(target)
    }

    @Transactional
    fun excludeFromPaybackTarget(paybackCancelEventsDto: PaymentCancelEventsDto) {
        val cancelEvent: PaymentCancelEvent = paybackCancelEventsDto.cancelEvent

        val foundCancelEvent: PaymentCancelEvent =
            paymentCancelEventRepository.findByPaymentEventId(cancelEvent.paymentEventId)
                ?: throw NotFoundPaymentEvent("")

        if (!foundCancelEvent.getIsCancelDone()) throw PaymentCancelNotCompletedException("")

        val paybackTarget: PaybackTarget? = paybackTargetRepository.findByIdOrNull(cancelEvent.paymentEventId)
        paybackTarget?.toggleIsCancelDone()
    }
}