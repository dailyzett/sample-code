package org.example.bankapp.controller

import org.example.bankapp.domain.dto.CommonResponse
import org.example.bankapp.domain.dto.PaybackCancelRequestDto
import org.example.bankapp.domain.dto.PaybackRequestDto
import org.example.bankapp.service.payback.PlacePaybackService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/payback")
class PaybackController(
    private val placePaybackService: PlacePaybackService,
) {

    @PostMapping("")
    fun processPayback(@RequestBody paybackRequestDto: PaybackRequestDto): ResponseEntity<CommonResponse> {
        val responseDto = placePaybackService.placePaybackEvent(paybackRequestDto)
        return ResponseEntity.ok(CommonResponse(data = responseDto))
    }

    @PostMapping("/cancel")
    fun cancelPayback(@RequestBody paybackCancelRequestDto: PaybackCancelRequestDto): ResponseEntity<CommonResponse> {
        val responseDto = placePaybackService.placePaybackCancelEvent(paybackCancelRequestDto)
        return ResponseEntity.ok(CommonResponse(data = responseDto))
    }
}