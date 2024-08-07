package org.example.bankapp.repository.payment

import org.example.bankapp.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
}