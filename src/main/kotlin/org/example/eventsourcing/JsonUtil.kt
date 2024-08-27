package org.example.eventsourcing

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

object JsonUtil {
    fun toJson(value: Any): String {
        val objectMapper = ObjectMapper()
        val jsonString = objectMapper.writeValueAsString(value)
        return jsonString
    }

    inline fun <reified T> fromJson(json: String, clazz: Class<out T>): T? {
        val mapper = ObjectMapper().apply {
            findAndRegisterModules()
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        }

        return try {
            mapper.readValue(json, clazz)
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
            null
        }
    }
}