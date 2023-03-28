package deepboot.deep

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jdbc.core.JdbcTemplate
import javax.annotation.PostConstruct


@SpringBootApplication
class DeepApplication {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @PostConstruct
    fun init() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `hello`(name varchar(50) primary key, count int)")
    }
}

fun main(args: Array<String>) {
    runApplication<DeepApplication>(*args)
}