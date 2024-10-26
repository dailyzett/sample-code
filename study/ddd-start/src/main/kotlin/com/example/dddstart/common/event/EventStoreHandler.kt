package com.example.dddstart.common.event

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class EventStoreHandler(
    private val eventStore: EventStore
) {

    @EventListener(Event::class)
    fun handler(event: Event) {
        eventStore.save(event)
    }
}