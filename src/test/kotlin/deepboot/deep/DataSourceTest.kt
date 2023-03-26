package deepboot.deep

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.sql.DataSource

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [DeepApplication::class])
@TestPropertySource("classpath:/application.yml")
class DataSourceTest {

    @Autowired
    lateinit var dataSource: DataSource

    @Test
    fun test() {
        val connection = dataSource.connection
        connection.close()
    }
}