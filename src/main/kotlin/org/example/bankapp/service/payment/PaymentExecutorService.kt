package org.example.bankapp.service.payment

import org.example.bankapp.common.exception.PaymentAlreadySuccessException
import org.example.bankapp.domain.payment.PaymentEvent
import org.example.bankapp.domain.payment.PaymentOrder
import org.example.bankapp.domain.payment.PaymentOrderStatus.FAILED
import org.example.bankapp.domain.payment.PaymentOrderStatus.SUCCESS
import org.example.bankapp.repository.payment.member.MemberRepository
import org.example.bankapp.repository.payment.payment.PaymentEventRepository
import org.example.bankapp.repository.payment.payment.PaymentOrderRepository
import org.example.bankapp.service.payment.event.PaymentEventsDto
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

/**
 * 이벤트를 받아 실제 결제를 수행하는 서비스 클래스입니다.
 */
@Service
class PaymentExecutorService(
    private val paymentEventRepository: PaymentEventRepository,
    private val paymentOrderRepository: PaymentOrderRepository,
    private val memberRepository: MemberRepository
) {

    private val logger = LoggerFactory.getLogger(PaymentExecutorService::class.java)

    @Transactional
    fun executePaymentOrder(paymentEventsDto: PaymentEventsDto) {
        val payingMemberInfo = paymentEventsDto.event.payingMember

        if (isPaymentAlreadyExecute(paymentEventsDto.event)) {
            throw PaymentAlreadySuccessException("")
        }

        val foundMember = memberRepository.findByIdOrNull(payingMemberInfo.memberId.id)!!
        foundMember.subtractMemberBalanceInfo(paymentEventsDto.amount)

        val paymentEvent = paymentEventRepository.findByIdOrNull(paymentEventsDto.event.id)
        paymentEvent!!.changePaymentDoneState()

        val paymentOrder = PaymentOrder(paymentEventsDto.amount, paymentEventsDto.event, SUCCESS)
        paymentOrderRepository.save(paymentOrder)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun fail(paymentEventsDto: PaymentEventsDto) {
        logger.info("[FAIL TIME EXCEED]")
        val paymentOrder = PaymentOrder(paymentEventsDto.amount, paymentEventsDto.event, FAILED)
        paymentOrderRepository.save(paymentOrder)
    }

    private fun isPaymentAlreadyExecute(paymentEvent: PaymentEvent) =
        paymentOrderRepository.existsByPaymentEventAndPaymentOrderStatus(paymentEvent, SUCCESS)
}