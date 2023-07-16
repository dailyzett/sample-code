package com.example.jpapractice.repository;

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
        Hotel hotel = new Hotel();
        hotel.setName("호텔");
        hotel.setGrade(Grade.GOOD);
        hotel.setYear(2021);
        hotel.setCreated(LocalDateTime.now());

        log.info("hotel = {}", hotel);

        em.persist(hotel);

        log.info("hotel = {}", hotel);
    }
}
