package com.example.dddstart.common.event

import org.springframework.context.ApplicationEventPublisher

class Events private constructor() {
    companion object {
        private var publisher: ApplicationEventPublisher? = null

        fun setPublisher(publisher: ApplicationEventPublisher) {
            Events.publisher = publisher
        }

        fun raise(event: Any) {
            publisher?.publishEvent(event)
        }
    }
}