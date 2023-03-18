package deepboot.deep.annotation

import deepboot.deep.config.autoconfig.DispatcherServletConfig
import deepboot.deep.config.autoconfig.TomcatWebServerConfig
import org.springframework.context.annotation.Import

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Import(
    DispatcherServletConfig::class,
    TomcatWebServerConfig::class
)
annotation class EnableMyAutoConfiguration {
}