package org.example.redisexample.config

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.data.redis.stream.StreamListener


class StreamListener : StreamListener<String, MapRecord<String, String, String>> {

    private val log = KotlinLogging.logger { }

    override fun onMessage(message: MapRecord<String, String, String>?) {
        log.info { "MessageId: ${message?.id}" }
        log.info { "Stream: ${message?.stream}" }
        log.info { "Body: ${message?.value}" }
    }

}