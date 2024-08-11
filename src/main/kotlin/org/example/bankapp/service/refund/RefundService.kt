package org.example.bankapp.service.refund

import org.example.bankapp.domain.member.Member
import org.example.bankapp.domain.payment.PaymentOrder
import org.example.bankapp.domain.payment.PaymentOrderStatus.CANCELLED
import org.example.bankapp.domain.payment.PaymentOrderStatus.SUCCESS
import org.example.bankapp.domain.payment.cancel.PaymentCancelEvent
import org.example.bankapp.repository.payment.member.MemberRepository
import org.example.bankapp.repository.payment.payment.PaymentCancelEventRepository
import org.example.bankapp.repository.payment.payment.PaymentOrderRepository
import org.example.bankapp.service.payment.event.PaymentCancelEventsDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class RefundService(
    private val memberRepository: MemberRepository,
    private val paymentOrderRepository: PaymentOrderRepository,
    private val paymentCancelEventRepository: PaymentCancelEventRepository
) {
    @Transactional
    fun executeRefund(paymentCancelEventsDto: PaymentCancelEventsDto) {
        val cancelEvent = paymentCancelEventsDto.cancelEvent
        val cancellingMemberId = cancelEvent.cancellingMember.memberId
        val paymentEventId = paymentCancelEventsDto.cancelEvent.paymentEventId

        val paymentOrder: PaymentOrder = paymentOrderRepository.findByIdAndStatus(paymentEventId.id, SUCCESS)!!
        val foundMember: Member = memberRepository.findByIdOrNull(cancellingMemberId.id)!!
        val paymentCancelEvent: PaymentCancelEvent = paymentCancelEventRepository.findByIdOrNull(cancelEvent.id)!!

        foundMember.addMemberBalanceInfo(paymentOrder.amount)
        paymentCancelEvent.changeIsCancelDone()

        val savedPaymentOrder = PaymentOrder(paymentOrder.amount, paymentOrder.paymentEvent, CANCELLED)
        paymentOrderRepository.save(savedPaymentOrder)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun fail(paymentCancelEventsDto: PaymentCancelEventsDto) {

    }
}