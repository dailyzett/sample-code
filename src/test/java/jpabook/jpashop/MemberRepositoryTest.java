package jpabook.jpashop;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class MemberRepositoryTest {

	@Autowired
	TestEntityManager em;

	@Test
	void testMember() throws Exception {
		//given
		Member member = new Member();
		member.setUsername("memberA");

		//when
		em.persist(member);
		//then
		Member findMember = em.find(Member.class, member.getId());
		assertThat(member.getId()).isEqualTo(findMember.getId());
		assertThat(member.getUsername()).isEqualTo(findMember.getUsername());
		assertThat(member).isSameAs(findMember);
	}
}