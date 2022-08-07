package jpabook.jpashop.controller;

import java.util.ArrayList;
import java.util.List;
import jpabook.jpashop.controller.dto.MemberRequest;
import jpabook.jpashop.controller.dto.MemberResponse;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/members/new")
	public ResponseEntity<MemberResponse> create(
		@Validated @RequestBody MemberRequest memberRequest) {

		Long joinId = memberService.join(memberRequest.toEntity());
		Member member = memberService.findOne(joinId);
		log.info("member info={}", member.toString());
		return new ResponseEntity<>(MemberResponse.from(member), HttpStatus.CREATED);
	}

	@GetMapping("/members")
	public ResponseEntity<List<MemberResponse>> list() {
		List<MemberResponse> memberResponses = new ArrayList<>();
		for (Member member : memberService.findMembers()) {
			memberResponses.add(MemberResponse.from(member));
		}
		return new ResponseEntity<>(memberResponses, HttpStatus.OK);
	}
}
