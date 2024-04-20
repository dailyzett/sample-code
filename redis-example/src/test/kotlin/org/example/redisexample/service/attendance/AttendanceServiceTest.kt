package org.example.redisexample.service.attendance

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations
import java.time.Clock
import java.time.LocalDate
import java.time.ZoneId

@ExtendWith(MockitoExtension::class)
class AttendanceServiceTest(
    @Mock private val valueOps: ValueOperations<String, String>,
    @Mock private val stringRedisTemplate: StringRedisTemplate
) {

    @InjectMocks
    lateinit var attendanceService: AttendanceService

    @Test
    fun `addPastAttendance 는 주어진 일자만큼 이전 출석 정보를 추가한다`() {
        //given
        val userID = 1
        val daysBack = 3
        val fixedClock = Clock.fixed(
            LocalDate.of(2024, 3, 23).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant(),
            ZoneId.systemDefault()
        )

        given(stringRedisTemplate.opsForValue()).willReturn(valueOps)

        //when
        val uvKeyList = attendanceService.addPastAttendance(userID, daysBack, fixedClock)

        //then
        val expectedKeyList = listOf("uv:20240323", "uv:20240322", "uv:20240321")

        assertThat(uvKeyList).hasSize(3)
        assertThat(uvKeyList).allSatisfy { uvKey -> assertThat(uvKey).startsWith("uv:") }
        assertThat(uvKeyList).containsExactlyInAnyOrderElementsOf(expectedKeyList)
    }
}