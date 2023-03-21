package deepboot.deep.config.autoconfig

import deepboot.deep.annotation.MyAutoConfiguration
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.context.annotation.Conditional
import org.springframework.core.type.AnnotatedTypeMetadata
import org.springframework.util.ClassUtils

@MyAutoConfiguration
@Conditional(JettyWebServerConfig.Companion.JettyCondition::class)
class JettyWebServerConfig {
    @Bean("jettyWebServerFactory")
    fun servletWebServerFactory() = JettyServletWebServerFactory()

    companion object {
        class JettyCondition : Condition {
            override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean {
                return ClassUtils.isPresent(
                    "org.eclipse.jetty.server.Server",
                    context.classLoader
                )
            }
        }
    }
}