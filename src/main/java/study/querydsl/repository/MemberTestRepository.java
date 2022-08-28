package study.querydsl.repository;

import static org.springframework.util.StringUtils.hasLength;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.entity.Member;
import study.querydsl.repository.support.Querydsl4RepositorySupport;

@Repository
public class MemberTestRepository extends Querydsl4RepositorySupport {

	public MemberTestRepository(Class<?> domainClass) {
		super(domainClass);
	}

	public List<Member> basicSelect() {
		return select(member)
			.from(member)
			.fetch();
	}

	public List<Member> basicSelectFrom() {
		return selectFrom(member)
			.fetch();
	}

	public Page<Member> searchPageByApplyPage(MemberSearchCondition condition, Pageable pageable) {
		JPAQuery<Member> query = selectFrom(member)
			.where(usernameEq(condition.getUsername()),
				teamNameEq(condition.getTeamName()),
				ageGoe(condition.getAgeGoe()),
				ageLoe(condition.getAgeLoe()));

		JPAQuery<Long> countQuery = select(member.count())
			.from(member)
			.leftJoin(member.team, team)
			.where(
				usernameEq(condition.getUsername()),
				teamNameEq(condition.getTeamName()),
				ageGoe(condition.getAgeGoe()),
				ageLoe(condition.getAgeLoe())
			);

		List<Member> content = getQuerydsl().applyPagination(pageable, query).fetch();
		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
	}

	public Page<Member> applyPagination(MemberSearchCondition condition, Pageable pageable) {
		return applyPagination(pageable, query ->
			query.selectFrom(member)
				.where(
					usernameEq(condition.getUsername()),
					teamNameEq(condition.getTeamName()),
					ageGoe(condition.getAgeGoe()),
					ageLoe(condition.getAgeLoe()))
		);
	}

	public Page<Member> applyPagination2(MemberSearchCondition condition, Pageable pageable) {
		return applyPagination(pageable, query ->
			query.selectFrom(member)
				.where(
					usernameEq(condition.getUsername()),
					teamNameEq(condition.getTeamName()),
					ageGoe(condition.getAgeGoe()),
					ageLoe(condition.getAgeLoe()))
		, countQuery -> countQuery
				.select(member.id)
				.from(member)
				.leftJoin(member.team, team)
				.where(
					usernameEq(condition.getUsername()),
					teamNameEq(condition.getTeamName()),
					ageGoe(condition.getAgeGoe()),
					ageLoe(condition.getAgeLoe())
				));
	}

	private BooleanExpression usernameEq(String username) {
		return hasLength(username) ? member.username.eq(username) : null;
	}

	private BooleanExpression teamNameEq(String teamName) {
		return hasLength(teamName) ? team.name.eq(teamName) : null;
	}

	private BooleanExpression ageGoe(Integer ageGoe) {
		return ageGoe != null ? member.age.goe(ageGoe) : null;
	}

	private BooleanExpression ageLoe(Integer ageLoe) {
		return ageLoe != null ? member.age.loe(ageLoe) : null;
	}
}
