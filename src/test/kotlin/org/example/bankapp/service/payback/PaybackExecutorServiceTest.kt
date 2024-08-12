package org.example.bankapp.service.payback

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.example.bankapp.domain.dto.PaybackEventsDto
import org.example.bankapp.domain.member.BalanceLimit
import org.example.bankapp.domain.member.Member
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.member.Wallet
import org.example.bankapp.domain.payback.PaybackEvent
import org.example.bankapp.domain.payback.PaybackEventId
import org.example.bankapp.domain.payback.PaybackOrder
import org.example.bankapp.domain.payback.PaybackOrderStatus
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

    Given("페이백 이벤트가 발행됐을 때") {
        val eventDto = PaybackEventsDto(
            paymentEventId = PaymentEventId(PAYMENT_EVENT_ID),
            paybackPercent = 10,
            paybackEvent = PaybackEvent(
                id = PaybackEventId(PAYBACK_EVENT_ID),
                paybackTargetId = MemberId(1L),
                isPaybackDone = false
            )
        )

        val foundPaybackOrder = PaybackOrder(
            id = 1L,
            paybackEventId = null,
            paymentEventId = PaymentEventId(PAYMENT_EVENT_ID),
            paybackOrderStatus = PaybackOrderStatus.NOT_STARTED,
            paybackAmount = null,
            paybackTargetId = MemberId(1L),
            paymentAmount = 10,
        )

        val foundMember = Member(
            id = 1L,
            userName = "",
            isRegistered = true,
            wallet = Wallet(1000),
            balanceLimit = BalanceLimit(1000, 1000, 1000)
        )

        val foundPaybackEvent = PaybackEvent(
            id = PaybackEventId(PAYBACK_EVENT_ID),
            isPaybackDone = false,
            paybackTargetId = MemberId(1L)
        )

        every { paybackOrderRepository.findTopByPaymentEventIdOrderByIdDesc(any()) } returns foundPaybackOrder
        every { memberRepository.findByIdOrNull(any()) } returns foundMember
        every { paybackEventRepository.findByIdOrNull(any()) } returns foundPaybackEvent
        every { paybackOrderRepository.save(any()) } returnsArgument 0

        When("페이백 만큼 사용자 잔고를 업데이트하는 메서드가 실행되면") {
            svc.addPaybackAmount(eventDto)

            Then("예상되는 값은 아래와 같아야 한다.") {
                foundPaybackEvent.isPaybackDone shouldBe true
                foundMember.getWallet().currentBalance shouldBe 1001
            }
        }
    }
})