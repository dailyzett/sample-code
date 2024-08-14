package org.example.bankapp.service.payment

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.example.bankapp.common.exception.PaymentAlreadySuccessException
import org.example.bankapp.domain.dto.PaymentEventsDto
import org.example.bankapp.domain.member.BalanceLimit
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.member.Members
import org.example.bankapp.domain.member.Wallet
import org.example.bankapp.domain.payment.PayingMember
import org.example.bankapp.domain.payment.PaymentEventId
import org.example.bankapp.domain.payment.PaymentEvents
import org.example.bankapp.domain.payment.PaymentOrderStatus.EXECUTING
import org.example.bankapp.domain.payment.PaymentOrderStatus.SUCCESS
import org.example.bankapp.domain.payment.PaymentOrders
import org.example.bankapp.repository.member.MemberRepository
import org.example.bankapp.repository.payment.PaymentEventRepository
import org.example.bankapp.repository.payment.PaymentOrderRepository
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
        val paymentEvents = PaymentEvents(
            PaymentEventId("1313-1"),
            false,
            PayingMember(memberId)
        )
        val paymentEventsDto = PaymentEventsDto(
            event = paymentEvents,
            amount = 300,
        )
        val foundMembers = Members(
            1L,
            "kim",
            true,
            BalanceLimit(1000, 2000, 3000),
            Wallet(1500)
        )
        PaymentOrders(1L, 300, paymentEvents, EXECUTING)
        PaymentOrders(1L, 300, paymentEvents, SUCCESS)

        every { memberRepository.findByIdOrNull(memberId.id) } returns foundMembers
        every { paymentOrderRepository.save(any()) } returns PaymentOrders(1L, 300, paymentEvents, SUCCESS)
        every { paymentEventRepository.findByIdOrNull(any()) } returns paymentEvents

        When("결제 실행 서비스가 완료되었을 때") {
            every { paymentOrderRepository.existsByPaymentEventsAndPaymentOrderStatus(any(), any()) } returns false
            paymentExecutorService.executePaymentOrder(paymentEventsDto)

            Then("송금 한도를 지갑의 돈을 차감하고 이벤트 완료 여부가 TRUE 여야 한다.") {
                val balanceLimit = foundMembers.getBalanceLimit()
                balanceLimit.dailyPaymentLimit shouldBe 1700
                balanceLimit.monthlyPaymentLimit shouldBe 2700

                val wallet = foundMembers.getWallet()
                wallet.currentBalance shouldBe 1200

                paymentEvents.getIsPaymentDone() shouldBe true
            }
        }

        When("결제가 이미 완료된 건일 때") {
            every { paymentOrderRepository.existsByPaymentEventsAndPaymentOrderStatus(any(), any()) } returns true

            Then("PaymentAlreadySuccessException 예외를 던진다") {
                shouldThrowExactly<PaymentAlreadySuccessException> {
                    paymentExecutorService.executePaymentOrder(paymentEventsDto)
                }
            }
        }
    }
})