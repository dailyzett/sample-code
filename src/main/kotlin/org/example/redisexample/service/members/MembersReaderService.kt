package org.example.redisexample.service.members

import org.example.redisexample.domain.entity.Member
import org.example.redisexample.repository.MemberRepository
import org.example.redisexample.util.KeyGenerator
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class MembersReaderService(
    private val redisTemplate: RedisTemplate<String, Member>,
    private val memberRepository: MemberRepository,
) {

    fun findRandomTwoMembers(): List<Member> {
        val allMembers: List<Member> = memberRepository.findAll()
        val hashOps = redisTemplate.opsForHash<String, Member>()

        val key = KeyGenerator.generateUserHashKey()
        allMembers.forEach { member ->
            hashOps.put(key, member.id.toString(), member)
        }

        val randomMembers = hashOps.randomEntries(key, 2)
        return randomMembers?.values?.toList() ?: listOf()
    }
}