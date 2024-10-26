package deepboot.deep

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository

interface HelloRepository {
    fun findHello(name: String): Hello?
    fun increaseCount(name: String)
}

fun HelloRepository.countOf(name: String): Int {
    return findHello(name)?.count ?: 0
}

@Repository
class HelloRepositoryImpl : HelloRepository {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    override fun findHello(name: String): Hello? {
        return try {
            jdbcTemplate.queryForObject("SELECT * FROM `hello` WHERE name = ?", name) { rs, _ ->
                Hello(rs.getString("name"), rs.getInt("count"))
            }
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    override fun increaseCount(name: String) {
        when (val hello = findHello(name)) {
            null -> jdbcTemplate.update("INSERT INTO `hello`(name, count) VALUES(?, ?)", name, 1)
            else -> jdbcTemplate.update("UPDATE `hello` SET count = ? WHERE name = ?", hello.count + 1, name)
        }
    }
}

data class Hello(
    val name: String,
    val count: Int,
)