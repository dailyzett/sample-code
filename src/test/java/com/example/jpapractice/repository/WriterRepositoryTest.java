package com.example.jpapractice.repository;

import com.example.jpapractice.entity.Address;
import com.example.jpapractice.entity.Intro;
import com.example.jpapractice.entity.Writer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class WriterRepositoryTest {

    @Autowired
    private WriterRepository writerRepository;

    @Test
    void save() {
        writerRepository.save();
        Writer writer = writerRepository.findByName("수현");
        assertThat(writer.getName()).isEqualTo("수현");

        Intro intro = writer.getIntro();
        assertThat(intro.getContentType()).isEqualTo("소설");

        Address address = writer.getAddress();
        assertThat(address.getCity()).isEqualTo("서울");
    }
}