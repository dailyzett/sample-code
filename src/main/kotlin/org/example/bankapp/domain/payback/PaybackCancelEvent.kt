package org.example.bankapp.domain.payback

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "T_PAYBACK_CANCEL_EVENT")
class PaybackCancelEvent(
    @Id
    val id: String,

    @Column(name = "is_cancel_done")
    var isCancelDone: Boolean,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "id", column = Column(name = "payback_event_id"))
    )
    val paybackEventId: PaybackEventId,

    @Column(name = "created_dt")
    val createdDt: LocalDateTime = LocalDateTime.now(),
) {
    fun updateToCancelDoneTrue() {
        isCancelDone = true
    }
}