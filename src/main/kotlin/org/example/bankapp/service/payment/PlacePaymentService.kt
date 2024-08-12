package org.example.bankapp.service.payment

import org.example.bankapp.common.event.Events
import org.example.bankapp.domain.dto.*
import org.example.bankapp.domain.member.Member
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.payment.EventType
import org.example.bankapp.domain.payment.PaymentEvent
import org.example.bankapp.domain.payment.PaymentEventId
import org.example.bankapp.domain.payment.PaymentOrder
import org.example.bankapp.domain.payment.cancel.CancellingMember
import org.example.bankapp.domain.payment.cancel.PaymentCancelEvent
import org.example.bankapp.repository.EventJdbcRepository
import org.example.bankapp.repository.payment.PaymentOrderRepository
import org.example.bankapp.service.event.EventIdService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlacePaymentService(
    private val eventIdService: EventIdService,
    private val payingMemberService: PayingMemberService,
    private val paymentOrderRepository: PaymentOrderRepository,
    private val paymentValidationService: PaymentValidationService,
    private val eventJdbcRepository: EventJdbcRepository,
) {
    @Transactional
    fun placePaymentEvent(paymentRequestDto: PaymentRequestDto): PaymentResponseDto {
        val createdPayingMember: Member = payingMemberService.createPayingMember(paymentRequestDto)
        val eventId: PaymentEventId = eventIdService.createPaymentEventId(MemberId(createdPayingMember.id))

        val paymentEvent = PaymentEvent(eventId, paymentRequestDto)
        eventJdbcRepository.insertPaymentEvent(paymentEvent)

        val paymentOrder = PaymentOrder(paymentRequestDto.amount, paymentEvent)
        paymentOrderRepository.save(paymentOrder)

        Events.raise(PaymentEventsDto(paymentEvent, paymentRequestDto.amount))
        return PaymentResponseDto.of(paymentEvent)
    }

    @Transactional
    fun placePaymentCancelEvent(paymentCancelRequestDto: PaymentCancelRequestDto): PaymentCancelEvent {
        val paymentEventId = PaymentEventId(paymentCancelRequestDto.paymentEventId)
        val cancellingMember = CancellingMember(paymentCancelRequestDto.cancellingMemberId)
        val cancelEventId: PaymentEventId = eventIdService.createPaymentEventId(cancellingMember.memberId)

        paymentValidationService.checkPossibleCancelEvent(paymentEventId)

        val paymentCancelEvent = PaymentCancelEvent(
            eventId = cancelEventId.id,
            cancellingMember = cancellingMember,
            eventType = EventType.PAYMENT,
            paymentEventId = paymentEventId
        )
        eventJdbcRepository.insertPaymentCancelEvent(paymentCancelEvent)

        Events.raise(PaymentCancelEventsDto(paymentCancelEvent))
        return paymentCancelEvent
    }
}