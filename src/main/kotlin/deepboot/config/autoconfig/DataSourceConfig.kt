package deepboot.config.autoconfig

import deepboot.config.ConditionalMyOnClass
import deepboot.config.EnableMyConfigurationProperties
import deepboot.config.MyAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.SimpleDriverDataSource
import java.sql.Driver
import javax.sql.DataSource

<<<<<<< HEAD
=======
@Suppress("UNCHECKED_CAST")
>>>>>>> origin/main
@MyAutoConfiguration
@ConditionalMyOnClass("org.springframework.jdbc.core.JdbcOperations")
@EnableMyConfigurationProperties(MyDataSourceProperties::class)
class DataSourceConfig {
<<<<<<< HEAD

=======
>>>>>>> origin/main
    @Bean
    fun datasource(properties: MyDataSourceProperties): DataSource {
        val dataSource = SimpleDriverDataSource()

<<<<<<< HEAD
        try {
            dataSource.setDriverClass((Class.forName(properties.driverClassName) as Class<out Driver>))
            dataSource.url = properties.url
            dataSource.username = properties.username
            dataSource.password = properties.password
        } catch (e: Exception) {
            e.printStackTrace()
        }
=======
        dataSource.setDriverClass(Class.forName(properties.driverClassName) as Class<out Driver>)
        dataSource.url = properties.url
        dataSource.username = properties.username
        dataSource.password = properties.password

        println("dataSource: $dataSource")
        println("dataSource.url: ${dataSource.url}")
        println("dataSource.username: ${dataSource.username}")
        println("dataSource.password: ${dataSource.password}")

>>>>>>> origin/main
        return dataSource
    }
}