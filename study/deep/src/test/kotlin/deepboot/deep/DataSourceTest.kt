package deepboot.deep

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import javax.sql.DataSource


@JdbcTest
class DataSourceTest {

    @Autowired
    lateinit var dataSource: DataSource

    @Test
    fun test() {
        val connection = dataSource.connection
        connection.close()
    }
}