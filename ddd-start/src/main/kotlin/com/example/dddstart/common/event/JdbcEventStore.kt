package com.example.dddstart.common.event

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
class JdbcEventStore(
    private val mapper: ObjectMapper,
    private val jdbcTemplate: JdbcTemplate
) : EventStore {
    override fun save(event: Any) {
        val eventEntry = EventEntry(event.javaClass.name, MediaType.APPLICATION_JSON_VALUE, toJson(event))
        jdbcTemplate.update(
            """
            INSERT INTO evententry (type, content_type, payload, timestamp)
            VALUES (?, ?, ?, ?)
            """.trimIndent()
        ) { ps ->
            ps.setString(1, eventEntry.type)
            ps.setString(2, eventEntry.contentType)
            ps.setString(3, eventEntry.payload)
            ps.setLong(4, Timestamp(eventEntry.timestamp).time)
        }
    }

    private fun toJson(event: Any): String {
        return try {
            mapper.writeValueAsString(event)
        } catch (e: JsonParseException) {
            throw RuntimeException()
        }
    }

    override fun get(offset: Long, limit: Long): List<EventEntry> {
        val sql = """select * from evententry order by id asc limit ?, ?""".trimIndent()
        return jdbcTemplate.query(
            sql,
            arrayOf(offset, limit)
        ) { rs, _ ->
            EventEntry(
                id = rs.getLong("id"),
                type = rs.getString("type"),
                contentType = rs.getString("content_type"),
                payload = rs.getString("payload"),
                timestamp = rs.getTimestamp("timestamp").time
            )
        }
    }
}