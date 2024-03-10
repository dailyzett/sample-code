package org.example.redisexample.service

import org.example.redisexample.domain.entity.Member
import org.example.redisexample.repository.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ScoresModifyService(
    private val memberRepository: MemberRepository,
) {
    @Transactional
    fun plusScoreByMember(score: Int): Int {
        val allMembers: List<Member> = memberRepository.findAll()
        val memberIds: List<Int> = allMembers.map { it.id }
        val randomMemberId: Int = memberIds.randomOrNull() ?: throw IllegalStateException("랜덤 함수 추출 불가")

        randomMemberId.let { memberId ->
            val member = memberRepository.findByIdOrNull(memberId) ?: throw IllegalStateException("없는 멤버")
            member.updatedScore(score)
        }

        return randomMemberId
    }
}