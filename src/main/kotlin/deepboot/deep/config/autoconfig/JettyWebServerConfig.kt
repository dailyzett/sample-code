package deepboot.deep.config.autoconfig

import deepboot.deep.annotation.MyAutoConfiguration
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory
import org.springframework.context.annotation.Bean

@MyAutoConfiguration
class JettyWebServerConfig {
    @Bean("jettyWebServerFactory")
    fun servletWebServerFactory() = JettyServletWebServerFactory()
}