package org.example.bankapp.domain.payment.cancel

import jakarta.persistence.*
import org.example.bankapp.domain.payment.EventType
import org.example.bankapp.domain.payment.PaymentEventId
import java.time.LocalDateTime

@Entity
@Table(name = "payment_cancel_events")
class PaymentCancelEvents(
    @Id
    val id: String,

    @Column(name = "is_cancel_done")
    private var isCancelDone: Boolean,

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    val eventType: EventType,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "id", column = Column(name = "payment_event_id"))
    )
    val paymentEventId: PaymentEventId,

    @Embedded
    val cancellingMember: CancellingMember,

    @Column(name = "created_dt")
    val createdDt: LocalDateTime,
) {
    constructor(
        eventId: String,
        cancellingMember: CancellingMember,
        eventType: EventType,
        paymentEventId: PaymentEventId
    ) : this(
        id = eventId,
        isCancelDone = false,
        paymentEventId = paymentEventId,
        eventType = eventType,
        cancellingMember = cancellingMember,
        createdDt = LocalDateTime.now()
    )

    fun changeIsCancelDone() {
        isCancelDone = true
    }

    fun getIsCancelDone(): Boolean = isCancelDone
}
