package org.example.redisexample.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.redisexample.repository.MemberRepository
import org.example.redisexample.util.DailyScoreKeyGenerator
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import kotlin.math.min

@Service
class RankReaderService(
    private val redisTemplate: RedisTemplate<String, String>,
    private val memberRepository: MemberRepository,
) {

    private val log = KotlinLogging.logger {}
    fun findRelativeLeaderBoardRank(memberName: String): Set<String>? {
        val searchedMember = memberRepository.findMemberByName(memberName)

        val zSetOperations = redisTemplate.opsForZSet()
        val key = DailyScoreKeyGenerator.generateKey()
        val memberRank = zSetOperations.reverseRank(key, searchedMember.id)?.toInt() ?: 0
        val totalMembers = zSetOperations.zCard(key)?.toInt() ?: 0

        log.info { "key: $key memberId: ${searchedMember.id}" }
        log.info { "member rank: $memberRank totalMember: $totalMembers" }
        val startIndex = when {
            memberRank < 2 -> 0
            memberRank > totalMembers - 2 -> totalMembers - 5
            else -> memberRank - 2
        }

        val endIndex = min(startIndex + 4, totalMembers - 1)
        log.info { "startIndex: $startIndex endIndex: $endIndex" }
        return zSetOperations.reverseRange(key, startIndex.toLong(), endIndex.toLong())
    }
}