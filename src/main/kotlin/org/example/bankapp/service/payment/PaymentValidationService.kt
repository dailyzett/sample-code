package org.example.bankapp.service.payment

import org.example.bankapp.common.exception.AlreadyCancelledPaymentException
import org.example.bankapp.common.exception.NotFoundPaymentSuccessEvent
import org.example.bankapp.common.exception.PaymentNotCompletedException
import org.example.bankapp.domain.payment.PaymentEventId
import org.example.bankapp.repository.payment.payment.PaymentCancelEventRepository
import org.example.bankapp.repository.payment.payment.PaymentEventRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

/**
 * 결제 관련 이벤트를 배치하기 이전 정합성을 체크하는 서비스 클래스
 */
@Service
class PaymentValidationService(
    private val paymentEventRepository: PaymentEventRepository,
    private val paymentCancelEventRepository: PaymentCancelEventRepository
) {
    fun checkPossibleCancelEvent(paymentEventId: PaymentEventId) {
        checkPaymentSuccessOrder(paymentEventId)
        checkCancelledOrderBefore(paymentEventId)
    }

    /**
     * 결제 이벤트가 완료된 건이 있는지 검사한다.
     */
    private fun checkPaymentSuccessOrder(paymentEventId: PaymentEventId) {
        val foundEvent = paymentEventRepository.findByIdOrNull(paymentEventId) ?: throw NotFoundPaymentSuccessEvent("")
        if (!foundEvent.getIsPaymentDone()) throw PaymentNotCompletedException("")
    }

    /**
     * 해당 결제 내역 이벤트 아이디로 이미 취소한 이력이 있는지 검사한다.
     */
    private fun checkCancelledOrderBefore(paymentEventId: PaymentEventId) {
        val paymentCancelEvent = paymentCancelEventRepository.findByPaymentEventId(paymentEventId)
        if (paymentCancelEvent?.getIsCancelDone() == true) {
            throw AlreadyCancelledPaymentException("")
        }
    }
}