package org.example.redisexample.controller.members

import org.example.redisexample.service.stat.DauService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/main")
class MainController(
    private val dauService: DauService,
) {

    @GetMapping
    fun mainDetails(@RequestParam userId: Int) {
        dauService.incrementDauCount(userId)
    }

    @GetMapping("/dau")
    fun dauDetails(): ResponseEntity<Long> {
        val dauCount = dauService.getDauCount()
        return ResponseEntity.ok(dauCount)
    }
}