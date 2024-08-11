package org.example.bankapp.repository.payment.member

import org.example.bankapp.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
}