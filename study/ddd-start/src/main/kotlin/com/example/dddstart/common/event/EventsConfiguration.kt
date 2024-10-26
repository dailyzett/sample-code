package com.example.dddstart.common.event

import org.springframework.beans.factory.InitializingBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventsConfiguration(
    private val applicationContext: ApplicationContext
) {
    @Bean
    fun eventsInitializer(): InitializingBean {
        return InitializingBean {
            Events.setPublisher(applicationContext)
        }
    }
}