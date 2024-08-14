package org.example.bankapp.service.payment

import org.example.bankapp.common.event.Events
import org.example.bankapp.domain.dto.*
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.member.Members
import org.example.bankapp.domain.payment.EventType
import org.example.bankapp.domain.payment.PaymentEventId
import org.example.bankapp.domain.payment.PaymentEvents
import org.example.bankapp.domain.payment.PaymentOrders
import org.example.bankapp.domain.payment.cancel.CancellingMember
import org.example.bankapp.domain.payment.cancel.PaymentCancelEvents
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
        val createdPayingMembers: Members = payingMemberService.createPayingMember(paymentRequestDto)
        val eventId: PaymentEventId = eventIdService.createPaymentEventId(MemberId(createdPayingMembers.id))

        val paymentEvents = PaymentEvents(eventId, paymentRequestDto)
        eventJdbcRepository.insertPaymentEvent(paymentEvents)

        val paymentOrders = PaymentOrders(paymentRequestDto.amount, paymentEvents)
        paymentOrderRepository.save(paymentOrders)

        Events.raise(PaymentEventsDto(paymentEvents, paymentRequestDto.amount))
        return PaymentResponseDto.of(paymentEvents)
    }

    @Transactional
    fun placePaymentCancelEvent(paymentCancelRequestDto: PaymentCancelRequestDto): PaymentCancelResponseDto {
        val paymentEventId = PaymentEventId(paymentCancelRequestDto.paymentEventId)
        val cancellingMember = CancellingMember(paymentCancelRequestDto.cancellingMemberId)
        val cancelEventId: PaymentEventId = eventIdService.createPaymentCancelEventId(cancellingMember.memberId)

        paymentValidationService.checkPossibleCancelEvent(paymentEventId)

        val paymentCancelEvents = PaymentCancelEvents(
            eventId = cancelEventId.id,
            cancellingMember = cancellingMember,
            eventType = EventType.PAYMENT,
            paymentEventId = paymentEventId
        )
        eventJdbcRepository.insertPaymentCancelEvent(paymentCancelEvents)

        Events.raise(PaymentCancelEventsDto(paymentCancelEvents))
        return PaymentCancelResponseDto(paymentCancelEvents.id)
    }
}