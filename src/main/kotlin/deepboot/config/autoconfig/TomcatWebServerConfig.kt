package deepboot.config.autoconfig

import deepboot.config.ConditionalMyOnClass
import deepboot.config.EnableMyConfigurationProperties
import deepboot.config.MyAutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ServletWebServerFactory
import org.springframework.context.annotation.Bean

@MyAutoConfiguration
@ConditionalMyOnClass("org.apache.catalina.startup.Tomcat")
@EnableMyConfigurationProperties(ServerProperties::class)
class TomcatWebServerConfig() {
    @Bean("tomcatWebServerFactory")
    @ConditionalOnMissingBean
    fun servletWebServerFactory(serverProperties: ServerProperties): ServletWebServerFactory {
        val factory = TomcatServletWebServerFactory()
        factory.contextPath = serverProperties.contextPath
        factory.port = serverProperties.port
        return factory
    }
}