//package org.example.bankapp.service.payment.event
//
//import io.mockk.every
//import io.mockk.mockk
//import kotlinx.coroutines.TimeoutCancellationException
//import kotlinx.coroutines.async
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.withTimeout
//import org.example.bankapp.domain.member.MemberId
//import org.example.bankapp.domain.payment.PayingMember
//import org.example.bankapp.domain.payment.PaymentEvent
//import org.example.bankapp.domain.payment.PaymentEventId
//import org.example.bankapp.service.payment.PaymentExecutorService
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertThrows
//
//class PaymentEventHandlerTest {
//
//    private val paymentExecutorService = mockk<PaymentExecutorService>()
//    private val paymentEventHandler = PaymentEventHandler(paymentExecutorService)
//
//    private val dto = PaymentEventsDto(
//        event = PaymentEvent(PaymentEventId("123"), false, PayingMember(MemberId(1L))),
//        amount = 100
//    )
//
//    @Test
//    fun `handle timeout Test`() = runTest {
//        every { paymentExecutorService.executePaymentOrder(any()) } coAnswers {
//            delay(1000)
//        }
//
//        val job = async {
//            paymentEventHandler.handle(dto)
//        }
//
//        assertThrows<TimeoutCancellationException> {
//            withTimeout(5000) {
//                job.await()
//            }
//        }
//    }
//}