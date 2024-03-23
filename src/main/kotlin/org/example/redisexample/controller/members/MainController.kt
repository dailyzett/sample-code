package org.example.redisexample.controller.members

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.redisexample.service.attendance.AttendanceService
import org.example.redisexample.service.stat.DauService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/main")
class MainController(
    private val dauService: DauService,
    private val attendanceService: AttendanceService,
) {

    private val log = KotlinLogging.logger { }

    @GetMapping
    fun mainDetails(@RequestParam userId: Int) {
        dauService.incrementDauCount(userId)
    }

    @GetMapping("/dau")
    fun dauDetails(): ResponseEntity<Long> {
        val dauCount = dauService.getDauCount()
        return ResponseEntity.ok(dauCount)
    }

    @GetMapping("/attendance")
    fun attendanceDetails(
        @RequestParam @DateTimeFormat(pattern = "yyyyMMdd") startDate: LocalDate,
        @RequestParam periodDay: Int
    ): ResponseEntity<List<Int>> {
        log.info { "startDate: $startDate periodDay: $periodDay" }
        val res = attendanceService.findContinuousCheckedInPeriod(startDate, periodDay)
        return ResponseEntity.ok(res)
    }
}