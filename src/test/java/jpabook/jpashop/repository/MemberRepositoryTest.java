package jpabook.jpashop.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

	@PersistenceContext
	EntityManager em;

	@Autowired
	MemberRepository memberRepository;

	Member initMember1 = new Member();
	Member initMember2 = new Member();

	@BeforeEach
	void initData() {
		initMember1.setName("park");
		Address address1 = new Address("서울", "관악구", "233");
		initMember1.setAddress(address1);

		initMember2.setName("kim");
		Address address2 = new Address("부산", "강서구", "477");
		initMember2.setAddress(address2);
	}

	@Test
	@DisplayName("회원 한명 찾기")
	void findOne() {
		//given
		memberRepository.save(initMember1);
		em.flush();
		em.clear();

		//when
		Member one = memberRepository.findOne(initMember1.getId());

		//then
		assertThat(one.getName()).isEqualTo(initMember1.getName());
		assertThat(one.getAddress().getCity()).isEqualTo(initMember1.getAddress().getCity());
		assertThat(one.getAddress().getStreet()).isEqualTo(initMember1.getAddress().getStreet());
	}

	@Test
	@DisplayName("회원 여러명 찾기")
	void findAll() {
		//given
		memberRepository.save(initMember1);
		memberRepository.save(initMember2);
		em.flush();
		em.clear();

		//when
		List<Member> all = memberRepository.findAll();

		//then
		assertThat(all).hasSize(2);
		assertThat(all.get(0).getName()).isEqualTo(initMember1.getName());
		assertThat(all.get(1).getName()).isEqualTo(initMember2.getName());
	}

	@Test
	@DisplayName("이름으로 특정 회원 찾기")
	void findByName() {
		//given
		memberRepository.save(initMember1);
		em.flush();
		em.clear();

		//when
		List<Member> byName = memberRepository.findByName(initMember1.getName());
		Member member = byName.get(0);

		//then
		assertThat(member.getName()).isEqualTo(initMember1.getName());
		assertThat(member.getAddress().getCity()).isEqualTo(initMember1.getAddress().getCity());
		assertThat(member.getAddress().getZipcode()).isEqualTo(
			initMember1.getAddress().getZipcode());
	}
}