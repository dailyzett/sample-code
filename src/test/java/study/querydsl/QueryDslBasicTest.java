package study.querydsl;

import static com.querydsl.jpa.JPAExpressions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;

@SpringBootTest
@Transactional
public class QueryDslBasicTest {

	@PersistenceContext
	EntityManager em;

	JPAQueryFactory queryFactory = null;

	@BeforeEach
	void before() {
		queryFactory = new JPAQueryFactory(em);

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

	@Test
	void startJPQL() {
		//member1 JPQL
		Member findByJPQL = em.createQuery("select m from Member m where m.username = :username",
				Member.class)
			.setParameter("username", "member1")
			.getSingleResult();

		assertThat(findByJPQL.getUsername()).isEqualTo("member1");
	}

	@Test
	void startDSL() {
		Member findMember = queryFactory
			.select(member)
			.from(member)
			.where(member.username.eq("member1"))
			.fetchOne();

		assertThat(Objects.requireNonNull(findMember).getUsername()).isEqualTo("member1");
	}

	@Test
	void search() {
		Member member1 = queryFactory
			.selectFrom(member)
			.where(member.username.eq("member1").and(member.age.eq(10)))
			.fetchOne();

		assertThat(Objects.requireNonNull(member1).getUsername()).isEqualTo("member1");
	}

	@Test
	void searchAndParam() {
		Member member1 = queryFactory
			.selectFrom(member)
			.where(
				member.username.eq("member1"),
				member.age.eq(10)
			)
			.fetchOne();

		assertThat(Objects.requireNonNull(member1).getUsername()).isEqualTo("member1");
	}

	@Test
	void resultFetchTest() {
		List<Member> result = queryFactory
			.selectFrom(member)
			.where(
				member.username.eq("member1"),
				member.age.eq(10)
			).fetch();

		assertThat(result.size()).isEqualTo(1);

		Long totalCount = queryFactory
			.select(member.count())
			.from(member)
			.fetchOne();

		System.out.println("totalCount = " + totalCount);
	}

	/**
	 * 회원 오름차 순 NULL 일 때는 제일 마지막에 출력
	 * 나이 내림차 순
	 */

	@Test
	void sort() {

		em.persist(new Member(null, 100));
		em.persist(new Member("member5", 100));
		em.persist(new Member("member6", 100));

		List<Member> result = queryFactory
			.selectFrom(member)
			.where(member.age.eq(100))
			.orderBy(member.age.desc(), member.username.asc().nullsLast())
			.fetch();

		assertThat(result.get(0).getUsername()).isEqualTo("member5");
		assertThat(result.get(1).getUsername()).isEqualTo("member6");
		assertThat(result.get(2).getUsername()).isNull();
	}

	@Test
	void paging1() {
		List<Member> result = queryFactory
			.selectFrom(member)
			.orderBy(member.username.desc())
			.offset(1)
			.limit(2)
			.fetch();

		assertThat(result.size()).isEqualTo(2);
		for (Member member1 : result) {
			System.out.println("member1 = " + member1);
		}
	}

	@Test
	void aggregation() {

		//쿼리 DSL 이 제공하는 튜플 조회
		//실무에선 DTO 를 뽑아오는 방식으로 많이 사용한다.
		List<Tuple> fetch = queryFactory
			.select(
				member.count(),
				member.age.sum(),
				member.age.avg(),
				member.age.min()
			)
			.from(member)
			.fetch();

		Tuple tuple = fetch.get(0);
		assertThat(tuple.get(member.count())).isEqualTo(4);
		assertThat(tuple.get(member.age.sum())).isEqualTo(100);
	}

	/**
	 * 팀의 이름과 각 팀의 평균 연령을 구해라
	 */
	@Test
	void group() {
		List<Tuple> result = queryFactory
			.select(team.name, member.age.avg())
			.from(member)
			.join(member.team, team)
			.groupBy(team.name)
			.fetch();

		Tuple teamA = result.get(0);
		Tuple teamB = result.get(1);

		assertThat(teamA.get(team.name)).isEqualTo("teamA");
		assertThat(teamA.get(member.age.avg())).isEqualTo(15);

		assertThat(teamB.get(team.name)).isEqualTo("teamB");
		assertThat(teamB.get(member.age.avg())).isEqualTo(35);
	}

	@Test
	void join() {
		List<Member> result = queryFactory
			.selectFrom(member)
			.join(member.team, team)
			.where(team.name.eq("teamA"))
			.fetch();

		assertThat(result)
			.extracting("username")
			.containsExactly("member1", "member2");
	}

	/**
	 * 회원의 이름이 팀 이름과 같은 회원 조회
	 */
	@Test
	void thetaJoin() {
		em.persist(new Member("teamA"));
		em.persist(new Member("teamB"));
		em.persist(new Member("teamC"));

		List<Member> result = queryFactory
			.select(member)
			.from(member, team)
			.where(member.username.eq(team.name))
			.fetch();

		assertThat(result)
			.extracting("username")
			.containsExactly("teamA", "teamB");
	}

	/**
	 * 회원과 팀을 조인하면서, 팀 이름이 teamA인 팀만 조인, 회원은 모두 조회
	 */
	@Test
	void join_on_filtering() {
		List<Tuple> result = queryFactory
			.select(member, team)
			.from(member)
			.leftJoin(member.team, team)
			.on(team.name.eq("teamA"))
			.fetch();

		for (Tuple tuple : result) {
			System.out.println("tuple = " + tuple);
		}
	}

	/**
	 * 연관관계가 없는 엔티티 외부 조인
	 */
	@Test
	void join_on_no_relation() {
		em.persist(new Member("teamA"));
		em.persist(new Member("teamB"));
		em.persist(new Member("teamC"));

		List<Tuple> result = queryFactory
			.select(member, team)
			.from(member)
			.leftJoin(team).on(member.username.eq(team.name))
			.fetch();

		for (Tuple tuple : result) {
			System.out.println("tuple = " + tuple);
		}
	}

	@PersistenceUnit
	EntityManagerFactory emf;

	@Test
	void fetchJoinNo() {
		//given
		em.flush();
		em.clear();

		Member findMember = queryFactory
			.selectFrom(member)
			.where(member.username.eq("member1"))
			.fetchOne();

		//when

		//then
		boolean loaded = emf.getPersistenceUnitUtil().isLoaded(Objects.requireNonNull(findMember).getTeam());
		assertThat(loaded).isFalse();
	}

	@Test
	void fetchJoinYes() {
		//given
		em.flush();
		em.clear();

		//when
		Member findMember = queryFactory
			.selectFrom(member)
			.join(member.team, team).fetchJoin()
			.where(member.username.eq("member1"))
			.fetchOne();

		//then
		boolean loaded = emf.getPersistenceUnitUtil().isLoaded(Objects.requireNonNull(findMember).getTeam());
		assertThat(loaded).isTrue();
	}

	/**
	 * 나이가 가장 많은 회원을 조회
	 */
	@Test
	void subQuery() {
		//given
		QMember memberSub = new QMember("memberSub");

		//when
		List<Member> result = queryFactory
			.selectFrom(member)
			.where(member.age.eq(
				select(memberSub.age.max())
					.from(memberSub)
			))
			.fetch();

		//then
		assertThat(result).extracting("age")
			.containsExactly(40);
	}

	/**
	 * 나이가 평균 이상인 사람 조회
	 */
	@Test
	void subQueryGoe() {
		//given
		QMember memberSub = new QMember("memberSub");

		//when
		List<Member> result = queryFactory
			.selectFrom(member)
			.where(member.age.goe(
				select(memberSub.age.avg())
					.from(memberSub)
			))
			.fetch();

		//then
		assertThat(result).extracting("age")
			.containsExactly(30, 40);
	}

	@Test
	void subQueryIn() {
		//given
		QMember memberSub = new QMember("memberSub");

		//when
		List<Member> result = queryFactory
			.selectFrom(member)
			.where(member.age.in(
				select(memberSub.age)
					.from(memberSub)
					.where(memberSub.age.gt(10))
			))
			.fetch();

		//then
		assertThat(result).extracting("age")
			.containsExactly(20, 30, 40);
	}

	@Test
	void selectSubQuery() {
		//given
		QMember memberSub = new QMember("memberSub");

		//when
		List<Tuple> fetch = queryFactory
			.select(member.username,
				select(memberSub.age.avg())
					.from(memberSub))
			.from(member)
			.fetch();

		//then
		for (Tuple tuple : fetch) {
			System.out.println("tuple = " + tuple);
		}
	}

	@Test
	void basicCase() {
		//given
		List<String> fetch = queryFactory
			.select(
				member.age
					.when(10).then("열살")
					.when(20).then("스무살")
					.otherwise("기타")
			)
			.from(member)
			.fetch();

		//when

		//then
		for (String s : fetch) {
			System.out.println("s = " + s);
		}
	}

	@Test
	void complexCase() {
		//given
		List<String> result = queryFactory
			.select(new CaseBuilder()
				.when(member.age.between(0, 20)).then("0~20")
				.when(member.age.between(21, 30)).then("21-30")
				.otherwise("기타"))
			.from(member)
			.fetch();
		//when

		//then
		for (String s : result) {
			System.out.println("s = " + s);
		}
	}

	@Test
	void constant() {
		//given
		List<Tuple> result = queryFactory
			.select(member.username, Expressions.constant("A"))
			.from(member)
			.fetch();

		for (Tuple tuple : result) {
			System.out.println("tuple = " + tuple);
		}
	}

	@Test
	void concat() {
		//given

		//{username}_{age}
		List<String> fetch = queryFactory
			.select(member.username.concat("_").concat(member.age.stringValue()))
			.from(member)
			.where(member.username.eq("member1"))
			.fetch();

		for (String s : fetch) {
			System.out.println("s = " + s);
		}
	}
}
