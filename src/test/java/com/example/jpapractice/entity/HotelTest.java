package com.example.jpapractice.entity;

import com.example.jpapractice.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class HotelTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void saveTest() {
        hotelRepository.save();
    }
}