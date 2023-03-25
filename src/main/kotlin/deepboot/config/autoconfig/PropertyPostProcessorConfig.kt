package deepboot.config.autoconfig

import deepboot.config.MyAutoConfiguration
import deepboot.config.MyConfigurationProperties
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.boot.context.properties.bind.Binder
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.core.env.Environment

@MyAutoConfiguration
class PropertyPostProcessorConfig {
    @Bean
    fun propertyPostProcessor(env: Environment): BeanPostProcessor {
        return object : BeanPostProcessor {
            override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
                AnnotationUtils.findAnnotation(bean::class.java, MyConfigurationProperties::class.java)?.let {
                    return Binder.get(env).bindOrCreate("", bean::class.java)
                } ?: return bean
            }
        }
    }
}