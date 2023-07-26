package deepboot.deep

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.jdbc.core.JdbcTemplate

@JdbcTest
class JdbcTemplateTest {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @BeforeEach
    fun setUp() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `hello`(name varchar(50) primary key, count int)")
    }

    @Test
    fun insertAndQuery() {
        jdbcTemplate.update("INSERT INTO `hello`(name, count) VALUES('Toby', 3)")
        jdbcTemplate.update("INSERT INTO `hello`(name, count) VALUES('Spring', 1)")

        jdbcTemplate.queryForObject("SELECT count(*) FROM `hello`", Long::class.java)?.let {
            Assertions.assertThat(it).isEqualTo(2)
        }
    }
}