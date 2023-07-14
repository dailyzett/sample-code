package com.example.jpapractice.repository;

import com.example.jpapractice.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    @Transactional
    public void save() {
        User user = new User("ssu30@inavi.kr", "수현", new Date());
        em.persist(user);
    }

    @Transactional
    public String findName() {
        User user = em.find(User.class, "ssu30@inavi.kr");
        return user.getName();
    }
}
