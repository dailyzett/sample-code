package org.example.bankapp.domain.payback

import jakarta.persistence.*
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.payment.PaymentEventId
import java.time.LocalDateTime

@Entity
@Table(name = "payback_orders")
class PaybackOrders(
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
        fun fromForCancel(paybackOrders: PaybackOrders): PaybackOrders {
            return PaybackOrders(
                paybackEventId = paybackOrders.paybackEventId,
                paymentEventId = paybackOrders.paymentEventId,
                paybackOrderStatus = PaybackOrderStatus.CANCELLED,
                paymentAmount = paybackOrders.paymentAmount,
                paybackAmount = paybackOrders.paybackAmount,
                paybackTargetId = paybackOrders.paybackTargetId,
                createdDt = paybackOrders.createdDt
            )
        }
    }
}