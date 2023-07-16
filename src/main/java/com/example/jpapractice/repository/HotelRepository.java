package com.example.jpapractice.repository;

import com.example.jpapractice.entity.Address;
import com.example.jpapractice.entity.Grade;
import com.example.jpapractice.entity.Hotel;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Repository
@RequiredArgsConstructor
public class HotelRepository {

    private final EntityManager em;

    @Transactional
    public void save() {
        Hotel hotel = Hotel.builder()
                .name("호텔")
                .grade(Grade.EXCELLENT)
                .created(LocalDateTime.now())
                .address(Address.builder()
                        .city("서울")
                        .state("서초구")
                        .street("반포대로")
                        .zipCode("12345")
                        .build())
                .build();
        em.persist(hotel);
    }

    public Hotel findByName(String name) {
        return em.createQuery("select h from Hotel h where h.name = :name", Hotel.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
