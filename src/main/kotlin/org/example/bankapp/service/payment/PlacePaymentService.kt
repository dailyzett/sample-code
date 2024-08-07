package org.example.bankapp.service.payment

import org.example.bankapp.common.event.Events
import org.example.bankapp.common.exception.DuplicatedPaymentException
import org.example.bankapp.domain.dto.PaymentRequestDto
import org.example.bankapp.domain.member.Member
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.payment.PaymentEvent
import org.example.bankapp.domain.payment.PaymentOrder
import org.example.bankapp.repository.payment.PaymentEventRepository
import org.example.bankapp.repository.payment.PaymentOrderRepository
import org.example.bankapp.service.payment.event.PaymentEventsDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlacePaymentService(
    private val paymentEventService: PaymentEventService,
    private val payingMemberService: PayingMemberService,
    private val paymentEventRepository: PaymentEventRepository,
    private val paymentOrderRepository: PaymentOrderRepository,
) {
    @Transactional
    fun placePayment(paymentRequestDto: PaymentRequestDto): PaymentEvent {
        val createdPayingMember: Member = payingMemberService.createPayingMember(paymentRequestDto)
        val paymentEventId = paymentEventService.createPaymentEventId(MemberId(createdPayingMember.id))

        if (paymentEventRepository.existsById(paymentEventId)) {
            throw DuplicatedPaymentException("")
        }

        val paymentEvent = PaymentEvent(paymentEventId, paymentRequestDto)
        val savedPaymentEvent = paymentEventRepository.save(PaymentEvent(paymentEventId, paymentRequestDto))
        val paymentOrder = PaymentOrder(paymentRequestDto.amount, paymentEvent)
        paymentOrderRepository.save(paymentOrder)

        Events.raise(PaymentEventsDto(paymentEvent, paymentRequestDto.amount))
        return savedPaymentEvent
    }
}