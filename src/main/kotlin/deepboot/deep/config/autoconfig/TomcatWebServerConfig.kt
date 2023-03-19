package deepboot.deep.config.autoconfig

import deepboot.deep.annotation.MyAutoConfiguration
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.context.annotation.Bean

@MyAutoConfiguration
class TomcatWebServerConfig {
    @Bean
    fun servletWebServerFactory() = TomcatServletWebServerFactory()
}