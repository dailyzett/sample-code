package org.example.bankapp.repository.member

import org.example.bankapp.domain.member.Members
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Members, Long> {
}