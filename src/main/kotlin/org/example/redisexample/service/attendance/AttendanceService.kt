package org.example.redisexample.service.attendance

import org.example.redisexample.util.KeyGenerator
import org.example.redisexample.util.getDaysBefore
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Clock

@Service
class AttendanceService(
    private val stringRedisTemplate: StringRedisTemplate,
) {

    fun addPastAttendance(userID: Int, daysBack: Int, clock: Clock): List<String> {
        val dayList = List(daysBack) { getDaysBefore(it, clock) }
        val uvKeyList = dayList.map { KeyGenerator.generateUserAttendanceKey(it) }
        uvKeyList.forEach { stringRedisTemplate.opsForValue().setBit(it, userID.toLong(), true) }
        return uvKeyList
    }
}