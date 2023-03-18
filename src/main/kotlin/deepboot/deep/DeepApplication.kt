package deepboot.deep

import org.springframework.boot.runApplication
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.servlet.DispatcherServlet


@Component
@Configuration
class DeepApplication {

    @Bean
    fun servletWebServerFactory() = TomcatServletWebServerFactory()

    @Bean
    fun dispatcherServlet() = DispatcherServlet()
}

fun main(args: Array<String>) {
    runApplication<DeepApplication>(*args)
}

