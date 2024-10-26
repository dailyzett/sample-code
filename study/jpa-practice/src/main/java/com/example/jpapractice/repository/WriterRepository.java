package com.example.jpapractice.repository;

import com.example.jpapractice.entity.Address;
import com.example.jpapractice.entity.Intro;
import com.example.jpapractice.entity.Writer;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WriterRepository {

    private final EntityManager em;

    public void save() {
        Writer writer = Writer.builder()
                .name("수현")
                .intro(Intro.builder()
                        .contentType("소설")
                        .content("자바의 정석")
                        .build())
                .address(Address.builder()
                        .city("서울")
                        .state("서초구")
                        .street("반포대로")
                        .zipCode("12345")
                        .build())
                .build();

        em.persist(writer);
    }

    public Writer findByName(String name) {
        return em.createQuery("select w from Writer w where w.name = :name", Writer.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
