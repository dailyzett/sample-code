package jpabook.jpashop;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@Test
	void testMember() throws Exception {
		//given
		Member member = new Member();
		member.setUsername("memberA");

		//when
		Long saveId = memberRepository.save(member);

		//then
		Member findMember = memberRepository.find(saveId);
		assertThat(member.getId()).isEqualTo(findMember.getId());
		assertThat(member.getUsername()).isEqualTo(findMember.getUsername());
		assertThat(member).isSameAs(findMember);
	}
}