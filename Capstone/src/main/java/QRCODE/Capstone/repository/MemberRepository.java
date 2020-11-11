package QRCODE.Capstone.repository;


import QRCODE.Capstone.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.username = :username and m.password = :password")
    Member findMember(String username, String password);

    @Query("select m from Member m where m.username = :username")
    List<Member> findByUserName(String username);
}
