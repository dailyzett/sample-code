package study.querydsl;

import static com.querydsl.jpa.JPAExpressions.select;
import static org.assertj.core.api.Assertions.assertThat;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberDto;
import study.querydsl.dto.QMemberDto;
import study.querydsl.dto.UserDto;
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

	@Test
	void simpleProjection() {
		//given
		List<String> fetch = queryFactory
			.select(member.username)
			.from(member)
			.fetch();

		List<Tuple> tuples = queryFactory
			.select(member.username, member.age)
			.from(member)
			.fetch();

		for (Tuple tuple : tuples) {
			String username = tuple.get(member.username);
			Integer age = tuple.get(member.age);
			System.out.println("username = " + username);
			System.out.println("age = " + age);
		}

		/* 컨트롤러까지 넘어가는건 좋은 설계가 아님.
		 * 리포지토리나 DAO 계층에서만 사용할 수 있도록 해야됨.
		 * 바깥 계층으로 던지는 것은 DTO 로 변환하는 것이 낫다.*/
	}

	/**
	 * New Operation 을 사용해야하기 때문에 별로다. 패키지명을 언제 다 적고 있나? 생성자 방식만 지원한다.
	 */
	@Test
	void findDtoByJPQL() {
		List<MemberDto> resultList = em.createQuery(
				"select new study.querydsl.dto.MemberDto(m.username, m.age) "
					+ "from Member m", MemberDto.class)
			.getResultList();

		for (MemberDto memberDto : resultList) {
			System.out.println("memberDto = " + memberDto);
		}
	}

	@Test
	void findDtoBySetter() {
		//given
		List<MemberDto> fetch = queryFactory
			.select(Projections.bean(MemberDto.class, member.username, member.age))
			.from(member)
			.fetch();

		for (MemberDto memberDto : fetch) {
			System.out.println("memberDto = " + memberDto);
		}
	}

	@Test
	void findDtoByField() {
		//given
		List<MemberDto> fetch = queryFactory
			.select(Projections.fields(MemberDto.class, member.username, member.age))
			.from(member)
			.fetch();

		for (MemberDto memberDto : fetch) {
			System.out.println("memberDto = " + memberDto);
		}
	}

	@Test
	void findDtoByConstructor() {
		//given
		List<MemberDto> fetch = queryFactory
			.select(Projections.constructor(MemberDto.class, member.username, member.age))
			.from(member)
			.fetch();

		for (MemberDto memberDto : fetch) {
			System.out.println("memberDto = " + memberDto);
		}
	}

	/**
	 * 별칭이 다를 때
	 */
	@Test
	void findUserDtoByField() {
		//given
		List<UserDto> fetch = queryFactory
			.select(Projections.fields(UserDto.class, member.username.as("name"), member.age))
			.from(member)
			.fetch();

		for (UserDto memberDto : fetch) {
			System.out.println("memberDto = " + memberDto);
		}
	}

	/*
	 * 장점:
	 * 1. 기본 DTO 프로젝션을 사용하면 런타임 에러가 발생
	 * 2. 하지만 @QueryProjection 어노테이션을 이용하면 컴파일 에러가 발생한다
	 *
	 * 단점:
	 * 1. Q 파일을 생성해야한다는 것
	 * 2. DTO 계층이 QueryDSL 에 대한 라이브러리 의존성이 생긴다.
	 * */
	@Test
	void findDtoByQueryProjection() {
		//given
		List<MemberDto> fetch = queryFactory
			.select(new QMemberDto(member.username, member.age))
			.from(member)
			.fetch();

		for (MemberDto memberDto : fetch) {
			System.out.println("memberDto = " + memberDto);
		}
	}

	/**
	 * 1. 동적 쿼리 - BooleanBuilder 사용
	 */

	@Test
	void dynamicQuery_BooleanBuilder() {
		String usernameParam = "member1";
		Integer ageParam = 10;

		List<Member> result = searchMember1(usernameParam, ageParam);
		assertThat(result.size()).isEqualTo(1);
	}

	private List<Member> searchMember1(String usernameCond, Integer ageCond) {

		BooleanBuilder builder = new BooleanBuilder();

		if (usernameCond != null) {
			builder.and(member.username.eq(usernameCond));
		}

		if (ageCond != null) {
			builder.and(member.age.eq(ageCond));
		}

		return queryFactory
			.selectFrom(member)
			.where(builder)
			.fetch();
	}

	/**
	 * 2. Where 로 동적 쿼리 작성하는 방법 where 구문에 null 이 들어가면 무시된다.
	 */
	@Test
	void dynamicQueryWhereParameter() {
		String usernameParam = "member1";
		Integer ageParam = 10;

		List<Member> result = searchMember2(usernameParam, ageParam);
		assertThat(result.size()).isEqualTo(1);
	}

	private List<Member> searchMember2(String usernameCond, Integer ageCond) {
		return queryFactory
			.selectFrom(member)
//			.where(usernameEq(usernameCond), ageEq(ageCond))
			.where(allEq(usernameCond, ageCond))
			.fetch();
	}

	private BooleanExpression ageEq(Integer ageCond) {
		if (ageCond == null) {
			return null;
		}
		return member.age.eq(ageCond);
	}

	private BooleanExpression usernameEq(String usernameCond) {
		if (usernameCond == null) {
			return null;
		}
		return member.username.eq(usernameCond);
	}

	// 광고 상태 isValid, 날짜가 IN : 두 개를 조합해서 isServiceable
	// 자바 코드기 때문에 조합이 가능하다는 장점이 있다.

	private BooleanExpression allEq(String usernameCond, Integer ageCond) {
		return usernameEq(usernameCond).and(ageEq(ageCond));
	}

	/**
	 * 변경 감지는 건마다 이뤄진다. 한 번에 update 를 많이 하는 쿼리인 경우 더티 체킹은 효율성이 너무 떨어짐. 이 때 벌크 연산을 사용해야한다.
	 */

	@Test
	void bulkUpdate() {

		//member1 = 10 -> 비회원
		//member2 = 20 -> 비회원
		//나머지 유지

		//벌크 연산 사용 시 DB의 상태와 영속성 컨텍스트 불일치 상태를 항상 유의해야한다.

		long count = queryFactory
			.update(member)
			.set(member.username, "비회원")
			.where(member.age.lt(28))
			.execute();

		em.flush();
		em.clear();

		List<Member> result = queryFactory
			.selectFrom(member)
			.fetch();

		for (Member member1 : result) {
			System.out.println("member1 = " + member1);
		}

		assertThat(count).isEqualTo(2);
	}

	@Test
	void bulkAdd() {
		//given
		long count = queryFactory
			.update(member)
			.set(member.age, member.age.add(1))
			.execute();
	}

	@Test
	void bulkDelete() {
		long count = queryFactory
			.delete(member)
			.where(member.age.gt(18))
			.execute();
	}

	/**
	 * SQL Function 호출하기
	 */
	@Test
	void sqlFunction() {
		List<String> result = queryFactory
			.select(
				Expressions.stringTemplate("function('replace', {0}, {1}, {2})",
					member.username, "member", "M"))
			.from(member)
			.fetch();

		for (String s : result) {
			System.out.println("s = " + s);
		}
	}

	@Test
	void sqlFunction2() {
		List<String> fetch = queryFactory
			.select(member.username)
			.from(member)
//			.where(member.username.eq(Expressions.stringTemplate(
//				"function('lower', {0})", member.username
			.where(member.username.eq(member.username.lower()))
			.fetch();

		for (String s : fetch) {
			System.out.println("s = " + s);
		}
	}
}
