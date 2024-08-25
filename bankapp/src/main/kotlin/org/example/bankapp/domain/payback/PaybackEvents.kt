package org.example.bankapp.domain.payback

import jakarta.persistence.*
import org.example.bankapp.domain.member.MemberId
import java.io.Serializable
import java.time.LocalDateTime

@Entity
@Table(name = "payback_events")
class PaybackEvents(
    @EmbeddedId
    val id: PaybackEventId,

    @Embedded
    val paybackTargetId: MemberId,

    @Column(name = "is_payback_done")
    var isPaybackDone: Boolean,

    @Column(name = "created_dt")
    val createdDt: LocalDateTime = LocalDateTime.now(),
) {
    fun updateToPaybackDoneTrue() {
        isPaybackDone = true
    }
}

@Embeddable
data class PaybackEventId(
    val id: String
) : Serializable