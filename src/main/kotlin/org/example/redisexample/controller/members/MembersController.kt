package org.example.redisexample.controller.members

import org.example.redisexample.domain.req.MemberReq
import org.example.redisexample.service.MembersRegistrationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/members")
class MembersController(
    private val membersRegistrationService: MembersRegistrationService,
) {

    @PostMapping
    fun addUser(@RequestBody member: MemberReq): ResponseEntity<String> {
        membersRegistrationService.addMember(member.name)
        return ResponseEntity.ok("성공적으로 저장되었습니다.")
    }
}