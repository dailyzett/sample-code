package org.example.bankapp.service.refund

import org.example.bankapp.common.exception.MemberNotFoundException
import org.example.bankapp.common.exception.NotFoundPaybackEvent
import org.example.bankapp.common.exception.NotFoundPaybackOrder
import org.example.bankapp.common.exception.PaybackNotCompletedException
import org.example.bankapp.domain.dto.PaybackCancelEventsDto
import org.example.bankapp.domain.dto.PaymentCancelEventsDto
import org.example.bankapp.domain.member.Members
import org.example.bankapp.domain.payback.PaybackCancelEvents
import org.example.bankapp.domain.payback.PaybackEvents
import org.example.bankapp.domain.payback.PaybackOrderStatus
import org.example.bankapp.domain.payback.PaybackOrders
import org.example.bankapp.domain.payment.PaymentOrderStatus.CANCELLED
import org.example.bankapp.domain.payment.PaymentOrderStatus.SUCCESS
import org.example.bankapp.domain.payment.PaymentOrders
import org.example.bankapp.domain.payment.cancel.PaymentCancelEvents
import org.example.bankapp.repository.member.MemberRepository
import org.example.bankapp.repository.payback.PaybackCancelEventRepository
import org.example.bankapp.repository.payback.PaybackEventRepository
import org.example.bankapp.repository.payback.PaybackOrderRepository
import org.example.bankapp.repository.payment.PaymentCancelEventRepository
import org.example.bankapp.repository.payment.PaymentOrderRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class RefundService(
    private val memberRepository: MemberRepository,
    private val paymentOrderRepository: PaymentOrderRepository,
    private val paymentCancelEventRepository: PaymentCancelEventRepository,
    private val paybackEventRepository: PaybackEventRepository,
    private val paybackOrderRepository: PaybackOrderRepository,
    private val paybackCancelEventRepository: PaybackCancelEventRepository,
) {
    @Transactional
    fun executePaymentRefund(paymentCancelEventsDto: PaymentCancelEventsDto) {
        val cancelEvent = paymentCancelEventsDto.cancelEvent
        val cancellingMemberId = cancelEvent.cancellingMember.memberId
        val paymentEventId = paymentCancelEventsDto.cancelEvent.paymentEventId

        val paymentOrders: PaymentOrders = paymentOrderRepository.findByIdAndStatus(paymentEventId.id, SUCCESS)!!
        val foundMembers: Members = memberRepository.findByIdOrNull(cancellingMemberId.id)
            ?: throw MemberNotFoundException("")
        val paymentCancelEvents: PaymentCancelEvents = paymentCancelEventRepository.findByIdOrNull(cancelEvent.id)!!

        foundMembers.addMemberBalanceInfo(paymentOrders.amount)
        paymentCancelEvents.changeIsCancelDone()

        val savedPaymentOrders = PaymentOrders(paymentOrders.amount, paymentOrders.paymentEvents, CANCELLED)
        paymentOrderRepository.save(savedPaymentOrders)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun fail(paymentCancelEventsDto: PaymentCancelEventsDto) {

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun fail(paybackCancelEventsDto: PaybackCancelEventsDto) {

    }

    /**
     * `executePaybackRefund` 함수는 환급 취소 이벤트를 실행하는 함수입니다.
     *
     * @param paybackCancelEventsDto 환급 취소 이벤트에 대한 데이터 전송 객체입니다.
     *
     * 이 함수는 아래와 같이 작동합니다:
     * 1. 먼저, `ensurePaybackCompleted` 함수를 통해 해당 환급 이벤트가 완료된 이벤트인지 확인합니다.
     * 2. 그 다음, `fetchPaybackMember` 함수를 통해 환급 대상인 멤버를 찾습니다.
     * 3. 이어서, `paybackOrderRepository`의 `findPaybackEventIdAndPaybackOrderStatus` 함수를 이용하여 성공한 환급 주문을 찾습니다.
     * 만일 해당 주문을 찾지 못할 경우 `NotFoundPaybackOrder` 예외가 발생하게 됩니다.
     * 4. 환급 금액을 계산하고, 해당 금액만큼 회원의 지갑 잔액을 업데이트합니다. 환급금액이 없을 경우, 환급 금액을 0으로 처리합니다.
     * 5. 마지막으로, `updateCancelStateToDone` 함수를 이용하여 취소 상태를 완료 상태로 업데이트합니다.
     */
    @Transactional
    fun executePaybackRefund(paybackCancelEventsDto: PaybackCancelEventsDto) {
        val foundPaybackEvents: PaybackEvents = ensurePaybackCompleted(paybackCancelEventsDto.paybackCancelEvents)
        val foundMembers: Members = fetchPaybackMember(foundPaybackEvents)

        val foundPaybackOrders: PaybackOrders = paybackOrderRepository.findByPaybackEventIdAndPaybackOrderStatus(
            foundPaybackEvents.id,
            PaybackOrderStatus.SUCCESS
        ) ?: throw NotFoundPaybackOrder("")

        val refundAmount: Int = foundPaybackOrders.paybackAmount ?: 0
        foundMembers.subtractMemberWalletBalance(refundAmount)

        updateCancelStateToDone(paybackCancelEventsDto.paybackCancelEvents)
        paybackOrderRepository.save(PaybackOrders.fromForCancel(foundPaybackOrders))
    }

    private fun fetchPaybackMember(foundPaybackEvents: PaybackEvents): Members {
        return memberRepository.findByIdOrNull(foundPaybackEvents.paybackTargetId.id)
            ?: throw MemberNotFoundException("")
    }

    private fun ensurePaybackCompleted(paybackCancelEvents: PaybackCancelEvents): PaybackEvents {
        val foundPaybackEvent = paybackEventRepository.findByIdOrNull(paybackCancelEvents.paybackEventId)
            ?: throw NotFoundPaybackEvent("")
        if (!foundPaybackEvent.isPaybackDone) throw PaybackNotCompletedException("")
        return foundPaybackEvent
    }

    private fun updateCancelStateToDone(paybackCancelEvents: PaybackCancelEvents) {
        val foundPaybackCancelEvent = paybackCancelEventRepository.findByIdOrNull(paybackCancelEvents.id)!!
        foundPaybackCancelEvent.updateToCancelDoneTrue()
    }
}