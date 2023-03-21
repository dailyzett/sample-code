package deepboot.deep.config.autoconfig

import deepboot.deep.annotation.ConditionalMyOnClass
import deepboot.deep.annotation.MyAutoConfiguration
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.context.annotation.Bean

@MyAutoConfiguration
@ConditionalMyOnClass("org.apache.catalina.startup.Tomcat")
class TomcatWebServerConfig {
    @Bean("tomcatWebServerFactory")
    fun servletWebServerFactory() = TomcatServletWebServerFactory()
}