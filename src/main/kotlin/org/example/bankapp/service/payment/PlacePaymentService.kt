package org.example.bankapp.service.payment

import org.example.bankapp.common.event.Events
import org.example.bankapp.domain.dto.CommonResponse
import org.example.bankapp.domain.dto.PaymentCancelRequestDto
import org.example.bankapp.domain.dto.PaymentRequestDto
import org.example.bankapp.domain.dto.PaymentResponseDto
import org.example.bankapp.domain.member.Member
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.payment.EventType
import org.example.bankapp.domain.payment.PaymentEvent
import org.example.bankapp.domain.payment.PaymentEventId
import org.example.bankapp.domain.payment.PaymentOrder
import org.example.bankapp.domain.payment.cancel.CancellingMember
import org.example.bankapp.domain.payment.cancel.PaymentCancelEvent
import org.example.bankapp.repository.payment.payment.PaymentJdbcRepository
import org.example.bankapp.repository.payment.payment.PaymentOrderRepository
import org.example.bankapp.service.payment.event.PaymentCancelEventsDto
import org.example.bankapp.service.payment.event.PaymentEventsDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlacePaymentService(
    private val paymentEventIdService: PaymentEventIdService,
    private val payingMemberService: PayingMemberService,
    private val paymentOrderRepository: PaymentOrderRepository,
    private val paymentValidationService: PaymentValidationService,
    private val paymentJdbcRepository: PaymentJdbcRepository,
) {
    @Transactional
    fun placePayment(paymentRequestDto: PaymentRequestDto): CommonResponse {
        val createdPayingMember: Member = payingMemberService.createPayingMember(paymentRequestDto)
        val eventId: PaymentEventId = paymentEventIdService.createPaymentEventId(MemberId(createdPayingMember.id))

        val paymentEvent = PaymentEvent(eventId, paymentRequestDto)
        paymentJdbcRepository.insertPaymentEvent(paymentEvent)

        val paymentOrder = PaymentOrder(paymentRequestDto.amount, paymentEvent)
        paymentOrderRepository.save(paymentOrder)

        Events.raise(PaymentEventsDto(paymentEvent, paymentRequestDto.amount))
        return CommonResponse(data = PaymentResponseDto.of(paymentEvent))
    }

    @Transactional
    fun placePaymentCancel(paymentCancelRequestDto: PaymentCancelRequestDto): PaymentCancelEvent {
        val paymentEventId = PaymentEventId(paymentCancelRequestDto.paymentEventId)
        val cancellingMember = CancellingMember(paymentCancelRequestDto.cancellingMemberId)
        val cancelEventId: PaymentEventId = paymentEventIdService.createPaymentEventId(cancellingMember.memberId)

        paymentValidationService.checkPossibleCancelEvent(paymentEventId)

        val paymentCancelEvent = PaymentCancelEvent(
            eventId = cancelEventId.id,
            cancellingMember = cancellingMember,
            eventType = EventType.PAYMENT,
            paymentEventId = paymentEventId
        )
        paymentJdbcRepository.insertPaymentCancelEvent(paymentCancelEvent)

        Events.raise(PaymentCancelEventsDto(paymentCancelEvent))
        return paymentCancelEvent
    }
}