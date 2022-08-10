package jpabook.jpashop.repository;

import java.util.List;
import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepositoryIn extends JpaRepository<Member, Long> {

	// select m from Member m where m.name = ?
	List<Member> findByName(String name);
}
