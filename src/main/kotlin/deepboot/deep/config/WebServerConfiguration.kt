package deepboot.deep.config

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class WebServerConfiguration {

    @Bean
    fun customWebServerFactory(): ServletWebServerFactory {
        val serverFactory = TomcatServletWebServerFactory()
        serverFactory.port = 9090
        return serverFactory
    }
}