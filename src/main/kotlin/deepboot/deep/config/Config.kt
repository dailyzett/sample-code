package deepboot.deep.config

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.DispatcherServlet

@Configuration
class Config {

    @Bean
    fun servletWebServerFactory() = TomcatServletWebServerFactory()

    @Bean
    fun dispatcherServlet() = DispatcherServlet()
}