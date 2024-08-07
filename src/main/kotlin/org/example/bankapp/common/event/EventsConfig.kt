package org.example.bankapp.common.event

import org.springframework.beans.factory.InitializingBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventsConfig(
    private val applicationContext: ApplicationContext
) {
    @Bean
    fun eventsInitializer() = InitializingBean {
        Events.publisher = applicationContext
    }
}