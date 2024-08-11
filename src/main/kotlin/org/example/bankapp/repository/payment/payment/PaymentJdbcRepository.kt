package org.example.bankapp.repository.payment.payment

import org.example.bankapp.common.exception.DuplicatedPaymentCancelException
import org.example.bankapp.common.exception.DuplicatedPaymentException
import org.example.bankapp.domain.payment.PaymentEvent
import org.example.bankapp.domain.payment.cancel.PaymentCancelEvent
import org.springframework.dao.DuplicateKeyException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class PaymentJdbcRepository(
    private val jdbcTemplate: JdbcTemplate,
) {
    fun insertPaymentEvent(paymentEvent: PaymentEvent) {
        val sql = """
            INSERT INTO T_PAYMENT_EVENT(id, is_payment_done, member_id, created_dt)
            VALUES (?, ?, ?, ?);
        """.trimIndent()

        try {
            jdbcTemplate.update(
                sql,
                paymentEvent.id.id,
                paymentEvent.getIsPaymentDone(),
                paymentEvent.payingMember.memberId.id,
                LocalDateTime.now()
            )
        } catch (e: DuplicateKeyException) {
            throw DuplicatedPaymentException("")
        }
    }

    fun insertPaymentCancelEvent(paymentCancelEvent: PaymentCancelEvent) {
        val sql = """
            INSERT INTO T_PAYMENT_CANCEL_EVENT(id, is_cancel_done, event_type, payment_event_id ,member_id, created_dt)
            VALUES (?, ?, ?, ?, ?, ?)
        """.trimIndent()

        try {
            jdbcTemplate.update(
                sql,
                paymentCancelEvent.id,
                paymentCancelEvent.getIsCancelDone(),
                paymentCancelEvent.eventType.name,
                paymentCancelEvent.paymentEventId.id,
                paymentCancelEvent.cancellingMember.memberId.id,
                paymentCancelEvent.createdDt
            )
        } catch (e: DuplicateKeyException) {
            throw DuplicatedPaymentCancelException("")
        }
    }
}