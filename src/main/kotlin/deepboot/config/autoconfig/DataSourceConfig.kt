package deepboot.config.autoconfig

import deepboot.config.ConditionalMyOnClass
import deepboot.config.EnableMyConfigurationProperties
import deepboot.config.MyAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.SimpleDriverDataSource
import java.sql.Driver
import javax.sql.DataSource

@MyAutoConfiguration
@ConditionalMyOnClass("org.springframework.jdbc.core.JdbcOperations")
@EnableMyConfigurationProperties(MyDataSourceProperties::class)
class DataSourceConfig {

    @Bean
    fun datasource(properties: MyDataSourceProperties): DataSource {
        val dataSource = SimpleDriverDataSource()

        try {
            dataSource.setDriverClass((Class.forName(properties.driverClassName) as Class<out Driver>))
            dataSource.url = properties.url
            dataSource.username = properties.username
            dataSource.password = properties.password
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dataSource
    }
}