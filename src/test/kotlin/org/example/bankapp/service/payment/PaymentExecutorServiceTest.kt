package org.example.bankapp.service.payment

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.example.bankapp.common.exception.PaymentAlreadySuccessException
import org.example.bankapp.domain.member.BalanceLimit
import org.example.bankapp.domain.member.Member
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.member.Wallet
import org.example.bankapp.domain.payment.PayingMember
import org.example.bankapp.domain.payment.PaymentEvent
import org.example.bankapp.domain.payment.PaymentEventId
import org.example.bankapp.domain.payment.PaymentOrder
import org.example.bankapp.domain.payment.PaymentOrderStatus.EXECUTING
import org.example.bankapp.domain.payment.PaymentOrderStatus.SUCCESS
import org.example.bankapp.repository.payment.member.MemberRepository
import org.example.bankapp.repository.payment.payment.PaymentEventRepository
import org.example.bankapp.repository.payment.payment.PaymentOrderRepository
import org.example.bankapp.service.payment.event.PaymentEventsDto
import org.springframework.data.repository.findByIdOrNull

class PaymentExecutorServiceTest : BehaviorSpec({
    val paymentEventRepository = mockk<PaymentEventRepository>()
    val paymentOrderRepository = mockk<PaymentOrderRepository>()
    val memberRepository = mockk<MemberRepository>()

    val paymentExecutorService = PaymentExecutorService(
        paymentEventRepository,
        paymentOrderRepository,
        memberRepository
    )

    Given("결제 이벤트 정보가 담긴 DTO 가 주어질 때") {
        val memberId = MemberId(1L)
        val paymentEvent = PaymentEvent(
            PaymentEventId("1313-1"),
            false,
            PayingMember(memberId)
        )
        val paymentEventsDto = PaymentEventsDto(
            event = paymentEvent,
            amount = 300,
        )
        val foundMember = Member(
            1L,
            "kim",
            true,
            BalanceLimit(1000, 2000, 3000),
            Wallet(1500)
        )
        PaymentOrder(1L, 300, paymentEvent, EXECUTING)
        PaymentOrder(1L, 300, paymentEvent, SUCCESS)

        every { memberRepository.findByIdOrNull(memberId.id) } returns foundMember
        every { paymentOrderRepository.save(any()) } returns PaymentOrder(1L, 300, paymentEvent, SUCCESS)
        every { paymentEventRepository.findByIdOrNull(any()) } returns paymentEvent

        When("결제 실행 서비스가 완료되었을 때") {
            every { paymentOrderRepository.existsByPaymentEventAndPaymentOrderStatus(any(), any()) } returns false
            paymentExecutorService.executePaymentOrder(paymentEventsDto)

            Then("송금 한도를 지갑의 돈을 차감하고 이벤트 완료 여부가 TRUE 여야 한다.") {
                val balanceLimit = foundMember.getBalanceLimit()
                balanceLimit.dailyPaymentLimit shouldBe 1700
                balanceLimit.monthlyPaymentLimit shouldBe 2700

                val wallet = foundMember.getWallet()
                wallet.currentBalance shouldBe 1200

                paymentEvent.getIsPaymentDone() shouldBe true
            }
        }

        When("결제가 이미 완료된 건일 때") {
            every { paymentOrderRepository.existsByPaymentEventAndPaymentOrderStatus(any(), any()) } returns true

            Then("PaymentAlreadySuccessException 예외를 던진다") {
                shouldThrowExactly<PaymentAlreadySuccessException> {
                    paymentExecutorService.executePaymentOrder(paymentEventsDto)
                }
            }
        }
    }
})