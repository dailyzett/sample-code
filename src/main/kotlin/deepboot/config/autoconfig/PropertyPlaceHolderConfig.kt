package deepboot.config.autoconfig

import deepboot.config.MyAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

@MyAutoConfiguration
class PropertyPlaceHolderConfig {
    @Bean
    fun propertySourcesPlaceholderConfigurer(): PropertySourcesPlaceholderConfigurer? {
        return PropertySourcesPlaceholderConfigurer()
    }
}