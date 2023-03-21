package deepboot.deep.config.autoconfig

import deepboot.deep.annotation.ConditionalMyOnClass
import deepboot.deep.annotation.MyAutoConfiguration
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory
import org.springframework.context.annotation.Bean

@MyAutoConfiguration
@ConditionalMyOnClass("org.eclipse.jetty.server.Server")
class JettyWebServerConfig {
    @Bean("jettyWebServerFactory")
    fun servletWebServerFactory() = JettyServletWebServerFactory()
}