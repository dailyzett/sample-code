package jpabook.jpashop.service;

import static org.assertj.core.api.Assertions.*;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

	@Autowired
	MemberService memberService;

	@Test
	@DisplayName("회원 가입")
	void join() {
		//given
		Member initMember1 = new Member();
		initMember1.setName("park");
		Address address1 = new Address("서울", "관악구", "233");
		initMember1.setAddress(address1);

		//when
		Long savedId = memberService.join(initMember1);

		//then
		assertThat(initMember1).isSameAs(memberService.findOne(savedId));
	}

	@Test
	@DisplayName("중복 회원 가입")
	void duplicateTest() {
		//given
		Member initMember1 = new Member();
		Member initMember2 = new Member();

		initMember1.setName("park");
		initMember2.setName("park");

		//when
		memberService.join(initMember1);
		assertThatThrownBy(() -> {
			//then
			memberService.join(initMember2);
		}).isInstanceOf(IllegalStateException.class);
	}
}