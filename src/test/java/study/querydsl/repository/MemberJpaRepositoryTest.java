package study.querydsl.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

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

	/**
	 * BooleanBuilder 로 동적 쿼리 테스트
	 */
	@Test
	void searchTest() {
		createMultipleMemberInfo();

		MemberSearchCondition condition = new MemberSearchCondition();
		condition.setAgeGoe(35);
		condition.setAgeLoe(40);
		condition.setTeamName("teamB");

		List<MemberTeamDto> result = memberJpaRepository.searchByBuilder(condition);

		assertThat(result).extracting("username").containsExactly("member4");
	}

	@Test
	void searchTest2() {
		createMultipleMemberInfo();

		MemberSearchCondition condition = new MemberSearchCondition();
		condition.setTeamName("teamB");

		List<MemberTeamDto> result = memberJpaRepository.searchByBuilder(condition);

		assertThat(result).extracting("username").containsExactly("member3", "member4");
	}

	/**
	 * where 절로 동적 쿼리
	 */
	@Test
	void searchTest3() {
		createMultipleMemberInfo();

		MemberSearchCondition condition = new MemberSearchCondition();
		condition.setTeamName("teamB");

		List<MemberTeamDto> result = memberJpaRepository.search(condition);

		assertThat(result).extracting("username").containsExactly("member3", "member4");
	}

	@Test
	void searchTest4() {
		createMultipleMemberInfo();

		MemberSearchCondition condition = new MemberSearchCondition();
		condition.setAgeGoe(35);
		condition.setAgeLoe(40);
		condition.setTeamName("teamB");

		List<MemberTeamDto> result = memberJpaRepository.search(condition);

		assertThat(result).extracting("username").containsExactly("member4");
	}

	private void createMultipleMemberInfo() {
		Team teamA = new Team("teamA");
		Team teamB = new Team("teamB");
		em.persist(teamA);
		em.persist(teamB);

		Member member1 = new Member("member1", 10, teamA);
		Member member2 = new Member("member2", 20, teamA);
		Member member3 = new Member("member3", 30, teamB);
		Member member4 = new Member("member4", 40, teamB);

		em.persist(member1);
		em.persist(member2);
		em.persist(member3);
		em.persist(member4);
	}
}