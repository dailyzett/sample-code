package org.example.bankapp.common.event

import org.springframework.context.ApplicationEventPublisher

object Events {
    var publisher: ApplicationEventPublisher? = null

    fun raise(event: Any) {
        publisher?.publishEvent(event)
    }
}