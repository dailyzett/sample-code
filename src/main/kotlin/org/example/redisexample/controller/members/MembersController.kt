package org.example.redisexample.controller.members

import org.example.redisexample.domain.entity.Member
import org.example.redisexample.domain.req.MemberReq
import org.example.redisexample.domain.res.MembersRankRes
import org.example.redisexample.service.attendance.AttendanceService
import org.example.redisexample.service.members.MembersReaderService
import org.example.redisexample.service.members.MembersRegistrationService
import org.example.redisexample.service.members.RankReaderService
import org.example.redisexample.service.members.ScoresModifyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Clock

@RestController
@RequestMapping("/members")
class MembersController(
    private val membersRegistrationService: MembersRegistrationService,
    private val scoresModifyService: ScoresModifyService,
    private val rankReaderService: RankReaderService,
    private val membersReaderService: MembersReaderService,
    private val attendanceService: AttendanceService,
) {

    @PostMapping
    fun memberAdd(@RequestBody member: MemberReq): ResponseEntity<String> {
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

    /**
     * 테스트 편의용 출석체크 컨트롤러
     * @param id 사용자 아이디
     * @param daysBack 오늘을 포함한 이 변수의 이전 날까지 출석 체크를 처리한다.
     */
    @PutMapping("/attendance")
    fun attendanceAdd(@RequestParam userID: Int, @RequestParam daysBack: Int): ResponseEntity<List<String>> {
        val clock = Clock.systemDefaultZone()
        val res = attendanceService.addPastAttendance(userID, daysBack, clock)
        return ResponseEntity.ok(res)
    }
}