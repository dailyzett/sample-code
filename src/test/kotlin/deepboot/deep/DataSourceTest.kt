package deepboot.deep

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import javax.sql.DataSource


@MyTransactionTest
class DataSourceTest {

    @Autowired
    lateinit var dataSource: DataSource

    @Test
    fun test() {
        val connection = dataSource.connection
        connection.close()
    }
}