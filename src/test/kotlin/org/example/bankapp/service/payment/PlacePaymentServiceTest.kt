package org.example.bankapp.service.payment

import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.example.bankapp.common.exception.DuplicatedPaymentException
import org.example.bankapp.domain.dto.PaymentRequestDto
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.payment.PayingMember
import org.example.bankapp.domain.payment.PaymentEvent
import org.example.bankapp.domain.payment.PaymentEventId
import org.example.bankapp.domain.payment.PaymentOrderStatus
import org.example.bankapp.repository.payment.PaymentEventRepository
import org.example.bankapp.repository.payment.PaymentOrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class PlacePaymentServiceTest : BehaviorSpec() {

    @Autowired
    lateinit var placePaymentService: PlacePaymentService

    @Autowired
    lateinit var paymentOrderRepository: PaymentOrderRepository

    @Autowired
    lateinit var paymentEventRepository: PaymentEventRepository

    @MockkBean
    lateinit var paymentEventService: PaymentEventService

    @MockkBean
    lateinit var paymentExecutorService: PaymentExecutorService

    val paymentEventId = PaymentEventId("testUnixTimeStamp-1")
    val paymentEvent = PaymentEvent(paymentEventId, false, PayingMember(MemberId(1L)))

    init {
        Given("결제 요청이 주어질 때") {
            val dto = PaymentRequestDto(MemberId(1L), 300)
            paymentEventRepository.delete(paymentEvent)
            every { paymentEventService.createPaymentEventId(any()) } returns paymentEventId

            When("결제 주문 서비스가 호출되면") {
                paymentEventRepository.delete(paymentEvent)
                paymentOrderRepository.deleteAll(paymentOrderRepository.findByPaymentEvent(paymentEvent))

                placePaymentService.placePayment(dto)
                val paymentOrders = paymentOrderRepository.findByPaymentEvent(paymentEvent)

                Then("최초 결제 주문 상태는 EXECUTING 이다.") {
                    paymentEvent.getIsPaymentDone() shouldBe false
                    paymentEvent.payingMember shouldBe PayingMember(dto.payingMemberId)
                    paymentOrders[0].paymentOrderStatus shouldBe PaymentOrderStatus.EXECUTING

                    paymentOrderRepository.deleteAll(paymentOrders)
                }
            }

            When("결제 주문이 이미 있고 동시에 요청이 들어왔을 때") {
                paymentEventRepository.delete(paymentEvent)
                paymentOrderRepository.deleteAll(paymentOrderRepository.findByPaymentEvent(paymentEvent))
                paymentEventRepository.save(paymentEvent)

                Then("두번째 결제 주문은 DuplicatedPaymentException 을 던지고 이벤트가 발행되지 않는다.") {
                    shouldThrow<DuplicatedPaymentException> {
                        placePaymentService.placePayment(dto)
                    }

                    val paymentOrderList = paymentOrderRepository.findByPaymentEvent(paymentEvent)
                    paymentOrderList.size shouldBe 0
                }
            }
        }
    }
}