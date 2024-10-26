package com.example.jpapractice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Commit
    void save() {
        userRepository.save();
    }

    @Test
    void find() {
        String name = userRepository.findName();
        assertThat(name).isEqualTo("수현");
    }

    @Test
    @Commit
    void saveCard() {
        userRepository.saveCard();
    }

    @Test
    void findCard() {
        userRepository.findCardNumber();
        System.out.println("userRepository.findCardNumber() = " + userRepository.findCardNumber());
    }
}