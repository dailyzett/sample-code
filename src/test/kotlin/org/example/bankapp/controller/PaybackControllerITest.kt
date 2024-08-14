package org.example.bankapp.controller

import io.kotest.matchers.shouldBe
import io.mockk.every
import kotlinx.coroutines.*
import org.example.bankapp.domain.dto.PaybackCancelRequestDto
import org.example.bankapp.domain.dto.PaybackRequestDto
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.payback.PaybackEventId
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers


class PaybackControllerITest : BaseIntegrationTest() {

    private val paybackEventId = PaybackEventId("PAYBACK-EVENT-ID")
    private val paybackCancelEventId = "PAYBACK-CANCEL-ID"

    @BeforeEach
    fun setUp() {
        every { eventIdService.createPaybackEventId(any()) } returns paybackEventId
        every { eventIdService.createPaybackCancelEventId(any()) } returns paybackCancelEventId
    }

    @Test
    @Sql(
        scripts = [
            "classpath:init-table.sql",
            "classpath:insert-test-member-data.sql",
            "classpath:insert-payment-event-data.sql",
        ]
    )
    fun `페이백 지급 동시 테스트`() {
        val req = PaybackRequestDto(
            memberId = testMemberId,
            paymentEventId = paymentEventId,
            percent = 10
        )

        val scope = CoroutineScope(Dispatchers.IO)
        val jobs = (1..numberOfThreads).map {
            scope.launch {
                mockMvc.perform(
                    MockMvcRequestBuilders.post("/payback")
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

        val member = memberRepository.findByIdOrNull(testMemberId)!!
        member.getWallet().currentBalance shouldBe (50000 + 1000 * (req.percent / 100.0)).toInt()
    }

    @Test
    @Sql(
        scripts = [
            "classpath:init-table.sql",
            "classpath:insert-test-member-data.sql",
            "classpath:insert-payment-event-data.sql",
            "classpath:insert-payback-event-data.sql",
            "classpath:insert-payback-order-data.sql"
        ]
    )
    fun `페이백 지급 취소 동시 테스트`() {
        val req = PaybackCancelRequestDto(
            cancellingMemberId = MemberId(testMemberId),
            paybackEventId = paybackEventId.id,
        )

        val scope = CoroutineScope(Dispatchers.IO)
        val jobs = (1..numberOfThreads).map {
            scope.launch {
                mockMvc.perform(
                    MockMvcRequestBuilders.post("/payback/cancel")
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

        val member = memberRepository.findByIdOrNull(testMemberId)!!
        member.getWallet().currentBalance shouldBe 49900
    }
}