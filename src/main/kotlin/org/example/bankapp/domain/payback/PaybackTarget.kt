package org.example.bankapp.domain.payback

import jakarta.persistence.*
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.payment.PaymentEventId
import java.time.LocalDateTime

@Entity
@Table(name = "T_PAYBACK_TARGET")
class PaybackTarget(
    @EmbeddedId
    @AttributeOverrides(
        AttributeOverride(name = "id", column = Column(name = "payment_event_id"))
    )
    val paymentEventId: PaymentEventId,

    @Embedded
    val paybackTargetId: MemberId,

    @Column(name = "payment_amount")
    val paymentAmount: Int,

    @Column(name = "is_payback_done")
    val isPaybackDone: Boolean,

    @Column(name = "is_cancel_done")
    private var isCancelDone: Boolean,

    @Column(name = "created_dt")
    val createdDt: LocalDateTime = LocalDateTime.now(),
) {
    fun toggleIsCancelDone() {
        isCancelDone = !isCancelDone
    }
}