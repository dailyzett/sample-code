package org.example.bankapp.domain.payback

import jakarta.persistence.*
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.payment.PaymentEventId
import java.time.LocalDateTime

@Entity
@Table(name = "T_PAYBACK_ORDER")
class PaybackOrder(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "id", column = Column(name = "payback_event_id"))
    )
    val paybackEventId: PaybackEventId?,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "id", column = Column(name = "payment_event_id"))
    )
    val paymentEventId: PaymentEventId,

    @Column(name = "payback_order_status")
    @Enumerated(EnumType.STRING)
    val paybackOrderStatus: PaybackOrderStatus,

    @Column(name = "payment_amount")
    val paymentAmount: Int,

    @Column(name = "payback_amount")
    val paybackAmount: Int? = null,

    @Embedded
    val paybackTargetId: MemberId,

    @Column(name = "created_dt")
    val createdDt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun fromForCancel(paybackOrder: PaybackOrder): PaybackOrder {
            return PaybackOrder(
                paybackEventId = paybackOrder.paybackEventId,
                paymentEventId = paybackOrder.paymentEventId,
                paybackOrderStatus = PaybackOrderStatus.CANCELLED,
                paymentAmount = paybackOrder.paymentAmount,
                paybackAmount = paybackOrder.paybackAmount,
                paybackTargetId = paybackOrder.paybackTargetId,
                createdDt = paybackOrder.createdDt
            )
        }
    }
}