package study.querydsl.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static study.querydsl.entity.QMember.member;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

	@PersistenceContext
	EntityManager em;

	@Autowired
	MemberRepository memberRepository;

	@Test
	void basicTest() {
		//given
		Member member = new Member("member1", 10);
		memberRepository.save(member);
		//when
		Member findMember = memberRepository.findById(member.getId()).get();
		assertThat(findMember).isEqualTo(member);

		List<Member> result1 = memberRepository.findAll();
		assertThat(result1).containsExactly(member);

		List<Member> result2 = memberRepository.findByUsername("member1");
		assertThat(result2).containsExactly(member);
	}

	/**
	 * where 절로 동적 쿼리
	 */
	@Test
	void searchTest3() {
		createMultipleMemberInfo();

		MemberSearchCondition condition = new MemberSearchCondition();
		condition.setTeamName("teamB");

		List<MemberTeamDto> result = memberRepository.search(condition);

		assertThat(result).extracting("username").containsExactly("member3", "member4");
	}

	@Test
	void searchTest4() {
		createMultipleMemberInfo();

		MemberSearchCondition condition = new MemberSearchCondition();
		condition.setAgeGoe(35);
		condition.setAgeLoe(40);
		condition.setTeamName("teamB");

		List<MemberTeamDto> result = memberRepository.search(condition);

		assertThat(result).extracting("username").containsExactly("member4");
	}

	/**
	 * 페이징 테스트
	 */
	@Test
	void searchTest5() {
		createMultipleMemberInfo();

		MemberSearchCondition condition = new MemberSearchCondition();
		PageRequest pageRequest = PageRequest.of(0, 3);

		Page<MemberTeamDto> result = memberRepository.searchPageSimple(condition, pageRequest);

		assertThat(result.getSize()).isEqualTo(3);
		assertThat(result.getContent()).extracting("username")
			.containsExactly("member1", "member2", "member3");
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

	/**
	 * QuerydslPredicateExecutor<Member> 를 상속받으면 where 절에 바로 predicate 적용 가능.
	 * 하지만 조인이 안됨.
	 * 클라이언트가 Querydsl 에 의존해야 한다. 서비스 클래스가 Querydsl 이라는 구현 기술에 의존해야 한다.
	 * 그래서 실무 환경에서 사용하기에는 한계가 명확하다.
	 */
	@Test
	void querydslPredicateExecutorTest() {
		Iterable<Member> result = memberRepository.findAll(
			member.age.between(10, 40).and(member.username.eq("member1")));
		for (Member member1 : result) {
			System.out.println("member1 = " + member1);
		}
	}
}