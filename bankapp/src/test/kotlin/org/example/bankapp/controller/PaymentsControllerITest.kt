package org.example.bankapp.controller

import io.kotest.matchers.shouldBe
import io.mockk.every
import kotlinx.coroutines.*
import org.example.bankapp.domain.dto.PaymentCancelRequestDto
import org.example.bankapp.domain.dto.PaymentRequestDto
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.payment.PaymentEventId
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers


class PaymentsControllerITest : BaseIntegrationTest() {

    private val paymentCancelEventId = PaymentEventId("PAYMENT-CANCEL-ID")

    @BeforeEach
    fun setUp() {
        every { eventIdService.createPaymentEventId(any()) } returns paymentEventId
        every { eventIdService.createPaymentCancelEventId(any()) } returns paymentCancelEventId
    }

    @AfterEach
    fun setDown() {
        memberRepository.deleteById(testMemberId)
    }

    @Test
    @Sql(scripts = ["classpath:init-table.sql", "classpath:insert-test-member-data.sql"])
    fun `결제 동시 테스트`() {
        val req = PaymentRequestDto(
            payingMemberId = MemberId(testMemberId),
            amount = 1000
        )

        val scope = CoroutineScope(Dispatchers.IO)
        val jobs = (1..numberOfThreads).map {
            scope.launch {
                mockMvc.perform(
                    MockMvcRequestBuilders.post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
                )
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn()
            }
        }

        runBlocking {
            jobs.joinAll()
        }

        val testMember = memberRepository.findByIdOrNull(testMemberId)!!
        testMember.getWallet().currentBalance shouldBe 50000 - 1_000
        testMember.getBalanceLimit().dailyPaymentLimit = 100000 - 1_000
        testMember.getBalanceLimit().monthlyPaymentLimit = 200000 - 1_000
    }

    @Test
    @Sql(scripts = ["classpath:init-table.sql", "classpath:insert-test-member-data.sql", "classpath:insert-payment-event-data.sql"])
    fun `결제 취소 동시 테스트`() {
        val req = PaymentCancelRequestDto(
            cancellingMemberId = MemberId(testMemberId),
            paymentEventId = paymentEventId.id
        )

        val scope = CoroutineScope(Dispatchers.IO)
        val jobs = (1..numberOfThreads).map {
            scope.launch {
                mockMvc.perform(
                    MockMvcRequestBuilders.post("/payments/cancel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
                )
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn()
            }
        }

        runBlocking {
            jobs.joinAll()
        }

        val testMember = memberRepository.findByIdOrNull(testMemberId)!!
        testMember.getWallet().currentBalance shouldBe 50000 + 1000
        testMember.getBalanceLimit().dailyPaymentLimit = 100000 + 1_000
        testMember.getBalanceLimit().monthlyPaymentLimit = 200000 + 1_000
    }
}