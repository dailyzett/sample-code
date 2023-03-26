package deepboot.deep

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import javax.sql.DataSource

@SpringBootTest
@ContextConfiguration(classes = [DeepApplication::class])
@TestPropertySource("classpath:/application.properties")
class DataSourceTest {

    @Autowired
    lateinit var dataSource: DataSource

    @Test
    fun test() {
        val connection = dataSource.connection
        connection.close()
    }
}