package org.example.eventsourcing

import com.fasterxml.jackson.databind.ObjectMapper

object JsonUtil {
    fun toJson(value: Any): String {
        val objectMapper = ObjectMapper()
        val jsonString = objectMapper.writeValueAsString(value)
        return jsonString
    }
}