package org.example.bankapp.service

import io.kotest.core.spec.style.BehaviorSpec
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.service.event.EventIdService
import org.junit.platform.commons.logging.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [EventIdService::class])
class PaymentEventServiceTest : BehaviorSpec() {

    private val log = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var eventIdService: EventIdService

    init {
        Given("memberId 가 주어지면") {
            val memberId = MemberId(1L)
            When("결제 이벤트 아이디가 생성될 때") {
                val paymentEventId = eventIdService.createPaymentEventId(memberId)
                Then("정상적으로 실행되는지 확인한다.") {
                    log.info { paymentEventId.id }
                }
            }
        }
    }
}