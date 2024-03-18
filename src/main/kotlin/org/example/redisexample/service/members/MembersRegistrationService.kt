package org.example.redisexample.service.members

import org.example.redisexample.domain.entity.Member
import org.example.redisexample.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MembersRegistrationService(
    private val memberRepository: MemberRepository,
) {
    fun addMember(name: String) {
        memberRepository.save(Member(name = name, score = 0))
    }
}