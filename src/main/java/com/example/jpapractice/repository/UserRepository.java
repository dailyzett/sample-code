package com.example.jpapractice.repository;

import com.example.jpapractice.entity.MembershipCard;
import com.example.jpapractice.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save() {
        User user = new User("ssu30@inavi.kr", "수현", LocalDateTime.now());
        em.persist(user);
    }

    public String findName() {
        User user = em.find(User.class, "ssu30@inavi.kr");
        return user.getName();
    }

    public void saveCard() {
        User user = em.find(User.class, "ssu30@inavi.kr");

        MembershipCard card = MembershipCard.builder()
                .cardNumber("8888111133332222")
                .owner(user)
                .expiryDate(LocalDate.of(2024, 12, 31))
                .build();

        em.persist(card);
        em.flush();
    }

    public String findCardNumber() {
        MembershipCard membershipCard = em.find(MembershipCard.class, "8888111133332222");
        return membershipCard.getCardNumber();
    }
}
