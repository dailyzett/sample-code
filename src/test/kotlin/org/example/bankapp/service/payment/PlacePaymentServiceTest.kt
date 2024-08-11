package org.example.bankapp.service.payment

import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.example.bankapp.common.exception.DuplicatedPaymentCancelException
import org.example.bankapp.common.exception.DuplicatedPaymentException
import org.example.bankapp.domain.dto.PaymentCancelRequestDto
import org.example.bankapp.domain.dto.PaymentRequestDto
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.payment.*
import org.example.bankapp.domain.payment.cancel.CancellingMember
import org.example.bankapp.domain.payment.cancel.PaymentCancelEvent
import org.example.bankapp.repository.payment.payment.PaymentCancelEventRepository
import org.example.bankapp.repository.payment.payment.PaymentEventRepository
import org.example.bankapp.repository.payment.payment.PaymentJdbcRepository
import org.example.bankapp.repository.payment.payment.PaymentOrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
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
    lateinit var paymentEventIdService: PaymentEventIdService

    @MockkBean
    lateinit var paymentExecutorService: PaymentExecutorService

    @Autowired
    lateinit var paymentJdbcRepository: PaymentJdbcRepository

    @Autowired
    lateinit var paymentCancelEventRepository: PaymentCancelEventRepository

    val paymentEventId = PaymentEventId("testUnixTimeStamp-1")
    val paymentEvent = PaymentEvent(paymentEventId, false, PayingMember(MemberId(1L)))

    init {
        Given("결제 요청이 주어질 때") {
            val dto = PaymentRequestDto(MemberId(1L), 300)
            paymentEventRepository.delete(paymentEvent)
            every { paymentEventIdService.createPaymentEventId(any()) } returns paymentEventId

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
                paymentJdbcRepository.insertPaymentEvent(paymentEvent)

                Then("두번째 결제 주문은 DuplicatedPaymentException 을 던지고 이벤트가 발행되지 않는다.") {
                    shouldThrow<DuplicatedPaymentException> {
                        placePaymentService.placePayment(dto)
                    }

                    val paymentOrderList = paymentOrderRepository.findByPaymentEvent(paymentEvent)
                    paymentOrderList.size shouldBe 0
                }
            }
        }

        Given("결제 취소 요청이 주어질 때") {
            val cancelEventId = PaymentEventId("CANCEL-EVENT-ID")
            val dto = PaymentCancelRequestDto(MemberId(1L), "PAYMENT-EVENT-ID")
            val paymentEvent = PaymentEvent(PaymentEventId("PAYMENT-EVENT-ID"), true, PayingMember(MemberId(1L)))
            val paymentCancelEvent = PaymentCancelEvent(
                eventId = cancelEventId.id,
                cancellingMember = CancellingMember(dto.cancellingMemberId),
                eventType = EventType.PAYMENT,
                paymentEventId = paymentEvent.id
            )
            every { paymentEventIdService.createPaymentEventId(any()) } returns cancelEventId
            paymentEventRepository.save(paymentEvent)

            When("취소 요청이 들어왔을 때") {
                placePaymentService.placePaymentCancel(dto)

                Then("이벤트에 저장된 내역은 아래의 예상값과 일치해야한다.") {
                    val foundPaymentCancelEvent = paymentCancelEventRepository.findByIdOrNull(cancelEventId.id)!!
                    foundPaymentCancelEvent.id shouldBe cancelEventId.id
                    foundPaymentCancelEvent.eventType shouldBe EventType.PAYMENT
                    foundPaymentCancelEvent.cancellingMember shouldBe CancellingMember(dto.cancellingMemberId)
                }
                paymentCancelEventRepository.deleteById(cancelEventId.id)
            }

            When("취소 요청이 이미 있고 동시에 요청이 들어왔을 때") {
                try {
                    paymentJdbcRepository.insertPaymentCancelEvent(paymentCancelEvent)
                } catch (_: Exception) {
                }

                Then("두번째 취소 주문은 DuplicatedPaymentCancelException 을 던지고 이벤트가 발행되지 않는다.") {
                    shouldThrow<DuplicatedPaymentCancelException> {
                        placePaymentService.placePaymentCancel(dto)
                    }
                }
            }
        }
    }
}