package com.example.jpapractice.repository;

import com.example.jpapractice.entity.Role;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class RoleRepository {

    private final EntityManager em;

    public void save() {
        Role role = Role.builder()
                .id("admin")
                .name("관리자")
                .permissions(Set.of("READ", "WRITE"))
                .build();

        em.persist(role);
        em.flush();
    }

}
