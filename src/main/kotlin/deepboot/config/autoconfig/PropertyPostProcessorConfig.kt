package deepboot.config.autoconfig

import deepboot.config.MyAutoConfiguration
import deepboot.config.MyConfigurationProperties
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.boot.context.properties.bind.Binder
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.AnnotationUtils.findAnnotation
import org.springframework.core.annotation.AnnotationUtils.getAnnotationAttributes
import org.springframework.core.env.Environment

@MyAutoConfiguration
class PropertyPostProcessorConfig {
    @Bean
    fun propertyPostProcessor(env: Environment): BeanPostProcessor {
        return object : BeanPostProcessor {
            override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
                val findAnnotation =
                    findAnnotation(bean::class.java, MyConfigurationProperties::class.java) ?: return bean

                val attr: Map<String, Any> = getAnnotationAttributes(findAnnotation)
                val prefix = attr["prefix"] as String
                return Binder.get(env).bind(prefix, bean::class.java).get()
            }
        }
    }
}