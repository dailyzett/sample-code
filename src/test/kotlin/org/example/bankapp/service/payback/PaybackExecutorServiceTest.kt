package org.example.bankapp.service.payback

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.example.bankapp.common.exception.NotNeedToPayback
import org.example.bankapp.domain.dto.PaybackEventsDto
import org.example.bankapp.domain.member.BalanceLimit
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.member.Members
import org.example.bankapp.domain.member.Wallet
import org.example.bankapp.domain.payback.PaybackEventId
import org.example.bankapp.domain.payback.PaybackEvents
import org.example.bankapp.domain.payback.PaybackOrderStatus
import org.example.bankapp.domain.payback.PaybackOrders
import org.example.bankapp.domain.payment.PaymentEventId
import org.example.bankapp.repository.member.MemberRepository
import org.example.bankapp.repository.payback.PaybackEventRepository
import org.example.bankapp.repository.payback.PaybackOrderRepository
import org.springframework.data.repository.findByIdOrNull

private const val PAYMENT_EVENT_ID = "PAYMENT_EVENT_ID"
private const val PAYBACK_EVENT_ID = "PAYBACK_EVENT_ID"

class PaybackExecutorServiceTest : BehaviorSpec({
    val paybackEventRepository = mockk<PaybackEventRepository>()
    val paybackOrderRepository = mockk<PaybackOrderRepository>()
    val memberRepository = mockk<MemberRepository>()

    val svc = PaybackExecutorService(paybackEventRepository, paybackOrderRepository, memberRepository)

    val eventDto = PaybackEventsDto(
        paymentEventId = PaymentEventId(PAYMENT_EVENT_ID),
        paybackPercent = 10,
        paybackEvents = PaybackEvents(
            id = PaybackEventId(PAYBACK_EVENT_ID),
            paybackTargetId = MemberId(1L),
            isPaybackDone = false
        )
    )

    val foundPaybackOrders = PaybackOrders(
        id = 1L,
        paybackEventId = null,
        paymentEventId = PaymentEventId(PAYMENT_EVENT_ID),
        paybackOrderStatus = PaybackOrderStatus.NOT_STARTED,
        paybackAmount = null,
        paybackTargetId = MemberId(1L),
        paymentAmount = 10,
    )

    val foundMembers = Members(
        id = 1L,
        userName = "",
        isRegistered = true,
        wallet = Wallet(1000),
        balanceLimit = BalanceLimit(1000, 1000, 1000)
    )

    val foundPaybackEvents = PaybackEvents(
        id = PaybackEventId(PAYBACK_EVENT_ID),
        isPaybackDone = false,
        paybackTargetId = MemberId(1L)
    )

    Given("페이백 이벤트가 발행됐을 때") {
        every { paybackOrderRepository.findTopByPaymentEventIdOrderByIdDesc(any()) } returns foundPaybackOrders
        every { memberRepository.findByIdOrNull(any()) } returns foundMembers
        every { paybackEventRepository.findByIdOrNull(any()) } returns foundPaybackEvents
        every { paybackOrderRepository.save(any()) } returnsArgument 0

        When("페이백 만큼 사용자 잔고를 업데이트하는 메서드가 실행되면") {
            svc.addPaybackAmount(eventDto)

            Then("예상되는 값은 아래와 같아야 한다.") {
                foundPaybackEvents.isPaybackDone shouldBe true
                foundMembers.getWallet().currentBalance shouldBe 1001
            }
        }
    }

    Given("취소된 결제 이벤트에 대해 페이백 이벤트가 발행됐을 때") {
        val cancelPaybackOrders = PaybackOrders(
            id = 1L,
            paybackEventId = null,
            paymentEventId = PaymentEventId(PAYMENT_EVENT_ID),
            paybackOrderStatus = PaybackOrderStatus.NOT_NEED_TO_PAYBACK,
            paybackAmount = null,
            paybackTargetId = MemberId(1L),
            paymentAmount = 10,
        )

        every { paybackOrderRepository.findTopByPaymentEventIdOrderByIdDesc(any()) } returns cancelPaybackOrders
        every { memberRepository.findByIdOrNull(any()) } returns foundMembers
        every { paybackEventRepository.findByIdOrNull(any()) } returns foundPaybackEvents
        every { paybackOrderRepository.save(any()) } returnsArgument 0

        When("페이백을 주는 이벤트 메서드가 실행 됐을 때") {
            Then("NotNeedToPayback 예외를 던진다") {
                shouldThrow<NotNeedToPayback> {
                    svc.addPaybackAmount(eventDto)
                }
            }
        }
    }
})