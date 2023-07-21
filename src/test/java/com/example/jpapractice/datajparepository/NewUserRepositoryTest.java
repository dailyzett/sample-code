package com.example.jpapractice.datajparepository;

import com.example.jpapractice.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class NewUserRepositoryTest {

    @Autowired
    private NewUserRepository newUserRepository;

    @Test
    @Commit
    void save() {
        User user = User.builder()
                .email("new@email.com")
                .name("new")
                .createDate(LocalDateTime.now())
                .build();

        newUserRepository.save(user);
    }

    @Test
    void find() {
        User user = newUserRepository.findByEmail("new@email.com");
        assertThat(user.getName()).isEqualTo("new");
    }
}