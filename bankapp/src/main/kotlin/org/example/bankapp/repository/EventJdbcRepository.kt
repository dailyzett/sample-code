package org.example.bankapp.repository

import org.example.bankapp.common.exception.DuplicatedPaybackCancelException
import org.example.bankapp.common.exception.DuplicatedPaybackException
import org.example.bankapp.common.exception.DuplicatedPaymentCancelException
import org.example.bankapp.common.exception.DuplicatedPaymentException
import org.example.bankapp.domain.payback.PaybackCancelEvents
import org.example.bankapp.domain.payback.PaybackEvents
import org.example.bankapp.domain.payment.PaymentEvents
import org.example.bankapp.domain.payment.cancel.PaymentCancelEvents
import org.springframework.dao.DuplicateKeyException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class EventJdbcRepository(
    private val jdbcTemplate: JdbcTemplate,
) {
    fun insertPaymentEvent(paymentEvents: PaymentEvents) {
        val sql = """
            INSERT INTO payment_events(id, is_payment_done, member_id, created_dt)
            VALUES (?, ?, ?, ?);
        """.trimIndent()

        try {
            jdbcTemplate.update(
                sql,
                paymentEvents.id.id,
                paymentEvents.getIsPaymentDone(),
                paymentEvents.payingMember.memberId.id,
                LocalDateTime.now()
            )
        } catch (e: DuplicateKeyException) {
            throw DuplicatedPaymentException("")
        }
    }

    fun insertPaymentCancelEvent(paymentCancelEvents: PaymentCancelEvents) {
        val sql = """
            INSERT INTO payment_cancel_events(id, is_cancel_done, event_type, payment_event_id ,member_id, created_dt)
            VALUES (?, ?, ?, ?, ?, ?)
        """.trimIndent()

        try {
            jdbcTemplate.update(
                sql,
                paymentCancelEvents.id,
                paymentCancelEvents.getIsCancelDone(),
                paymentCancelEvents.eventType.name,
                paymentCancelEvents.paymentEventId.id,
                paymentCancelEvents.cancellingMember.memberId.id,
                paymentCancelEvents.createdDt
            )
        } catch (e: DuplicateKeyException) {
            throw DuplicatedPaymentCancelException("")
        }
    }

    fun insertPaybackEvent(paybackEvents: PaybackEvents) {
        val sql = """
            INSERT INTO payback_events (id, is_payback_done, member_id, created_dt)
            VALUES (?, ?, ?, ?)
        """.trimIndent()

        try {
            jdbcTemplate.update(
                sql,
                paybackEvents.id.id,
                paybackEvents.isPaybackDone,
                paybackEvents.paybackTargetId.id,
                paybackEvents.createdDt
            )
        } catch (e: DuplicateKeyException) {
            throw DuplicatedPaybackException("")
        } catch (e: Exception) {
            throw e
        }
    }

    fun insertPaybackCancelEvent(paybackCancelEvents: PaybackCancelEvents) {
        val sql = """
            INSERT INTO payback_cancel_events (id, is_cancel_done, payback_event_id, created_dt)
            VALUES (?, ?, ?, ?)
        """.trimIndent()

        try {
            jdbcTemplate.update(
                sql,
                paybackCancelEvents.id,
                paybackCancelEvents.isCancelDone,
                paybackCancelEvents.paybackEventId.id,
                paybackCancelEvents.createdDt
            )
        } catch (e: DuplicateKeyException) {
            throw DuplicatedPaybackCancelException("")
        } catch (e: Exception) {
            throw e
        }
    }
}