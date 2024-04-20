package org.example.redisexample.service.attendance

import org.example.redisexample.util.KeyGenerator
import org.example.redisexample.util.getDaysBefore
import org.springframework.data.redis.connection.RedisStringCommands.BitOperation
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.LocalDate

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

    /**
     * 특정 기간 연속 출석 체크한 userId 를 찾습니다. 특정 기간의 시작점은 startDate 로부터 startDate + periodDay 까지 입니다.
     * @param startDate 시작일
     * @param periodDay 기간(일 단위)
     * @return 특정 기간동안 연속 출석 체크한 userId 를 반환합니다.
     */
    fun findContinuousCheckedInPeriod(startDate: LocalDate, periodDay: Int): List<Int> {
        val attendanceDates =
            List(periodDay) { "uv:${changeDateToString(startDate.plusDays(it.toLong()))}".toByteArray() }

        val key = "event:${changeDateToString(startDate).dropLast(2)}"
        stringRedisTemplate.execute { connection ->
            connection.stringCommands()
                .bitOp(BitOperation.AND, key.toByteArray(), *attendanceDates.toTypedArray())
        }

        val attendanceResult = stringRedisTemplate.opsForValue().get(key) ?: ""
        val attendanceIntList = convertBitToStringList(attendanceResult)
        return attendanceIntList.withIndex().filter { it.value == 1 }.map { it.index }
    }

    private fun changeDateToString(date: LocalDate): String {
        return date.toString().replace("-", "")
    }

    private fun convertBitToStringList(result: String): List<Int> {
        val bitsList = mutableListOf<Int>()
        result.forEach { char ->
            val bits = (7 downTo 0).map { (char.code shr it) and 1 }
            bitsList.addAll(bits)
        }
        return bitsList
    }
}