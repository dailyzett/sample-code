package deepboot.config

import deepboot.deep.annotation.ConditionalMyOnClass
import deepboot.deep.annotation.MyAutoConfiguration
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment

@MyAutoConfiguration
@ConditionalMyOnClass("org.apache.catalina.startup.Tomcat")
class TomcatWebServerConfig() {

    @Value(value = "\${context.path}")
    lateinit var contextPath: String

    @Bean("tomcatWebServerFactory")
    @ConditionalOnMissingBean
    fun servletWebServerFactory(env: Environment): ServletWebServerFactory {
        val factory = TomcatServletWebServerFactory()
        factory.contextPath = "/app"
        return factory
    }
}