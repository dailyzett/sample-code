package study.querydsl.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

	@PersistenceContext
	EntityManager em;

	@Autowired
	MemberJpaRepository memberJpaRepository;

	@Test
	void basicTest() {
		//given
		Member member = new Member("member1", 10);
		memberJpaRepository.save(member);
		//when
		Member findMember = memberJpaRepository.findById(member.getId()).get();
		assertThat(findMember).isEqualTo(member);

		List<Member> result1 = memberJpaRepository.findAll();
		assertThat(result1).containsExactly(member);

		List<Member> result2 = memberJpaRepository.findByUsername("member1");
		assertThat(result2).containsExactly(member);
	}

	@Test
	void basicQueryDslTest() {
		Member member = new Member("member1", 10);
		memberJpaRepository.save(member);

		Member findMember = memberJpaRepository.findById(member.getId()).get();
		assertThat(findMember).isEqualTo(member);

		List<Member> result1 = memberJpaRepository.findAllQueryDsl();
		assertThat(result1).containsExactly(member);

		List<Member> result2 = memberJpaRepository.findByUsernameQueryDsl("member1");
		assertThat(result2).containsExactly(member);
	}
}