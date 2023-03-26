package deepboot.deep

import org.junit.jupiter.api.Test
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import javax.sql.DataSource

@SpringBootTest
@ContextConfiguration(classes = [DeepApplication::class])
@TestPropertySource("classpath:/application.yml")
=======
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.sql.DataSource

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [DeepApplication::class])
>>>>>>> origin/main
class DataSourceTest {

    @Autowired
    lateinit var dataSource: DataSource

    @Test
    fun test() {
<<<<<<< HEAD
        val connection = dataSource.connection
        connection.close()
=======
        dataSource.connection
        dataSource.connection.close()
>>>>>>> origin/main
    }
}