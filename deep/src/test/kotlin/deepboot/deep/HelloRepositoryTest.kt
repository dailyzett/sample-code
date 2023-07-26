package deepboot.deep

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class HelloRepositoryTest {

    @Autowired
    lateinit var helloRepository: HelloRepository

    @Test
    fun findHelloFailed() {
        assertThat(helloRepository.findHello("Toby")).isNull()
    }

    @Test
    fun increaseCount() {
        assertThat(helloRepository.countOf("Toby")).isEqualTo(0)

        helloRepository.increaseCount("Toby")
        assertThat(helloRepository.countOf("Toby")).isEqualTo(1)

        helloRepository.increaseCount("Toby")
        assertThat(helloRepository.countOf("Toby")).isEqualTo(2)
    }

}