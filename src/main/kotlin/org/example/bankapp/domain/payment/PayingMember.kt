package org.example.bankapp.domain.payment

import jakarta.persistence.*
import org.example.bankapp.domain.member.Member
import org.example.bankapp.domain.member.MemberId

@Embeddable
data class PayingMember(
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "id", column = Column(name = "member_id"))
    )
    val memberId: MemberId,
) {
    companion object {
        fun of(member: Member): PayingMember {
            return PayingMember(MemberId(member.id))
        }
    }
}