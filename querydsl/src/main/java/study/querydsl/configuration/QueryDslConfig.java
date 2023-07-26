package study.querydsl.configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {

	/**
	 * 싱글톤이지만, thread-safe 하다.
	 * JPAQueryFactory 는 EntityManager 구현에 의존한다.
	 * EntityManger 은 트랜잭션 단위로 움직이기 때문에 동시성 문제를 해결할 수 있다.
	 * 스프링은 EntityManger 을 바로 호출하는 것이 아니라 일단 프록시로 선언하고 트랜잭션에서 사용되면, 실제 EntityManager 를 라우팅해준다.
	 */
	@Bean
	JPAQueryFactory jpaQueryFactory(EntityManager em) {
		return new JPAQueryFactory(em);
	}
}
