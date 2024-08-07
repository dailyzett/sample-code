package org.example.bankapp.controller

import org.example.bankapp.domain.dto.PaymentRequestDto
import org.example.bankapp.service.payment.PlacePaymentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/payments")
class PaymentsController(
    private val placePaymentService: PlacePaymentService
) {
    @PostMapping
    fun processPayment(@RequestBody paymentRequestDto: PaymentRequestDto): ResponseEntity<String> {
        placePaymentService.placePayment(paymentRequestDto)
        return ResponseEntity.ok("SUCCESS")
    }

    @PostMapping("/cancel")
    fun cancelPayment() {
        TODO()
    }
}