package org.example.bankapp.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import org.example.bankapp.domain.payment.PaymentEventId
import org.example.bankapp.repository.member.MemberRepository
import org.example.bankapp.service.event.EventIdService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
class BaseIntegrationTest {

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @Autowired
    protected lateinit var memberRepository: MemberRepository

    @MockkBean
    protected lateinit var eventIdService: EventIdService

    protected val testMemberId = 10000001L
    protected val paymentEventId = PaymentEventId("PAYMENT-TEST-ID")

    protected val numberOfThreads = 10
}