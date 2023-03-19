package deepboot.deep.annotation

import org.springframework.context.annotation.Configuration

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Configuration(proxyBeanMethods = false)
annotation class MyAutoConfiguration()
