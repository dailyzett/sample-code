package org.example.redisexample.controller.members

import org.example.redisexample.domain.entity.Member
import org.example.redisexample.domain.req.MemberReq
import org.example.redisexample.domain.res.MembersRankRes
import org.example.redisexample.service.members.MembersReaderService
import org.example.redisexample.service.members.MembersRegistrationService
import org.example.redisexample.service.members.RankReaderService
import org.example.redisexample.service.members.ScoresModifyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/members")
class MembersController(
    private val membersRegistrationService: MembersRegistrationService,
    private val scoresModifyService: ScoresModifyService,
    private val rankReaderService: RankReaderService,
    private val membersReaderService: MembersReaderService,
) {

    @PostMapping
    fun addUser(@RequestBody member: MemberReq): ResponseEntity<String> {
        membersRegistrationService.addMember(member.name)
        return ResponseEntity.ok("성공적으로 저장되었습니다.")
    }

    @PatchMapping("/score")
    fun updateUserScore(@RequestParam score: Int): ResponseEntity<String> {
        val membersId = scoresModifyService.plusScoreByMember(score)
        return ResponseEntity.ok("멤버 PK: $membersId 추가된 점수: $score")
    }

    @GetMapping("/rank")
    fun memberRankDetails(@RequestParam memberName: String): ResponseEntity<MembersRankRes> {
        val membersRankRes = rankReaderService.findRelativeLeaderBoardRank(memberName)
        return ResponseEntity.ok(membersRankRes)
    }

    @GetMapping("/random")
    fun randomMemberList(): ResponseEntity<List<Member>> {
        val res = membersReaderService.findRandomTwoMembers()
        return ResponseEntity.ok(res)
    }
}