package org.example.bankapp.domain.payment.cancel

import jakarta.persistence.*
import org.example.bankapp.domain.member.MemberId

@Embeddable
data class CancellingMember(
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "id", column = Column(name = "member_id"))
    )
    val memberId: MemberId
)