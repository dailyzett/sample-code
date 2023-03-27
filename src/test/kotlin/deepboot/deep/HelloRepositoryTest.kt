package deepboot.deep

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate

@MyTransactionTest
class HelloRepositoryTest {

    @Autowired
    lateinit var helloRepository: HelloRepository

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @BeforeEach
    fun setUp() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `hello`(name varchar(50) primary key, count int)")
    }

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