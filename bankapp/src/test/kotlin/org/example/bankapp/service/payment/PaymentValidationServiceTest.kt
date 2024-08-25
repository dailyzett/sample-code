package org.example.bankapp.service.payment

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.example.bankapp.common.exception.AlreadyCancelledPaymentException
import org.example.bankapp.common.exception.NotFoundPaymentSuccessEvent
import org.example.bankapp.common.exception.PaymentNotCompletedException
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.payment.EventType
import org.example.bankapp.domain.payment.PayingMember
import org.example.bankapp.domain.payment.PaymentEventId
import org.example.bankapp.domain.payment.PaymentEvents
import org.example.bankapp.domain.payment.cancel.CancellingMember
import org.example.bankapp.domain.payment.cancel.PaymentCancelEvents
import org.example.bankapp.repository.payment.PaymentCancelEventRepository
import org.example.bankapp.repository.payment.PaymentEventRepository
import org.springframework.data.repository.findByIdOrNull

class PaymentValidationServiceTest : BehaviorSpec({
    val paymentCancelEventRepository = mockk<PaymentCancelEventRepository>()
    val paymentEventRepository = mockk<PaymentEventRepository>()

    val paymentValidationService = PaymentValidationService(paymentEventRepository, paymentCancelEventRepository)

    Given("취소 이벤트 검증에 필요한 결제 이벤트 아이디와 취소 이벤트 아이디가 주어질 때") {
        val paymentEventId = PaymentEventId("PAYMENT-ID")
        val cancelEventId = "CANCEL-ID"

        When("결제 이벤트가 완료된 건이 없을 때") {
            every { paymentEventRepository.findByIdOrNull(paymentEventId) } returns null

            Then("NotFoundPaymentSuccessEvent 예외를 던진다") {
                shouldThrow<NotFoundPaymentSuccessEvent> {
                    paymentValidationService.checkPossibleCancelEvent(paymentEventId)
                }
            }
        }

        When("결제가 완료된 상태가 아닐 때") {
            every { paymentEventRepository.findByIdOrNull(paymentEventId) } returns PaymentEvents(
                id = PaymentEventId(""),
                isPaymentDone = false,
                payingMember = PayingMember(MemberId(1L)),
            )

            Then("PaymentNotCompletedException 예외를 던진다") {
                shouldThrow<PaymentNotCompletedException> {
                    paymentValidationService.checkPossibleCancelEvent(paymentEventId)
                }
            }
        }

        When("동일 취소 이벤트가 이미 있다면") {
            every { paymentEventRepository.findByIdOrNull(paymentEventId) } returns PaymentEvents(
                id = PaymentEventId(""),
                isPaymentDone = true,
                payingMember = PayingMember(MemberId(1L)),
            )

            val cancelEvent = PaymentCancelEvents(
                eventId = "",
                cancellingMember = CancellingMember(MemberId(1L)),
                eventType = EventType.PAYMENT,
                paymentEventId = paymentEventId
            )
            every { paymentCancelEventRepository.findByPaymentEventId(paymentEventId) } returns cancelEvent
            cancelEvent.changeIsCancelDone()

            Then("AlreadyCancelledPaymentException 예외를 던진다") {
                shouldThrow<AlreadyCancelledPaymentException> {
                    paymentValidationService.checkPossibleCancelEvent(paymentEventId)
                }
            }
        }
    }
})