package org.example.bankapp.domain.payment

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "T_PAYMENT_ORDER")
class PaymentOrder(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val amount: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_event_id")
    val paymentEvent: PaymentEvent,

    @Column(name = "payment_order_status")
    @Enumerated(EnumType.STRING)
    val paymentOrderStatus: PaymentOrderStatus,

    @Column(name = "created_dt")
    val createdDt: LocalDateTime = LocalDateTime.now(),
) {
    constructor(amount: Int, paymentEvent: PaymentEvent) : this(
        amount = amount,
        paymentEvent = paymentEvent,
        paymentOrderStatus = PaymentOrderStatus.EXECUTING,
        createdDt = LocalDateTime.now(),
    )

    constructor(amount: Int, paymentEvent: PaymentEvent, paymentOrderStatus: PaymentOrderStatus) : this(
        amount = amount,
        paymentEvent = paymentEvent,
        paymentOrderStatus = paymentOrderStatus,
        createdDt = LocalDateTime.now()
    )
}
