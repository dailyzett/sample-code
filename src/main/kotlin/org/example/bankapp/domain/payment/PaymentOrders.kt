package org.example.bankapp.domain.payment

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "payment_orders")
class PaymentOrders(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val amount: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_event_id")
    val paymentEvents: PaymentEvents,

    @Column(name = "payment_order_status")
    @Enumerated(EnumType.STRING)
    val paymentOrderStatus: PaymentOrderStatus,

    @Column(name = "created_dt")
    val createdDt: LocalDateTime = LocalDateTime.now(),
) {
    constructor(amount: Int, paymentEvents: PaymentEvents) : this(
        amount = amount,
        paymentEvents = paymentEvents,
        paymentOrderStatus = PaymentOrderStatus.EXECUTING,
        createdDt = LocalDateTime.now(),
    )

    constructor(amount: Int, paymentEvents: PaymentEvents, paymentOrderStatus: PaymentOrderStatus) : this(
        amount = amount,
        paymentEvents = paymentEvents,
        paymentOrderStatus = paymentOrderStatus,
        createdDt = LocalDateTime.now()
    )
}
