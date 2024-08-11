package org.example.bankapp.controller

import org.example.bankapp.domain.dto.PaybackRequestDto
import org.example.bankapp.service.payback.PaybackPaymentService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/payback")
class PaybackController(
    private val paybackPaymentService: PaybackPaymentService,
) {

    @PostMapping("")
    fun processPayback(@RequestBody paybackRequestDto: PaybackRequestDto) {

    }

    @PostMapping("/cancel")
    fun cancelPayback() {
        TODO()
    }
}