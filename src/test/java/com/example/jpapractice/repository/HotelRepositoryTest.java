package com.example.jpapractice.repository;

import com.example.jpapractice.entity.Hotel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class HotelRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void save() {
        hotelRepository.save();
        Hotel hotel = hotelRepository.findByName("호텔");

        assertThat(hotel.getName()).isEqualTo("호텔");
    }
}