package org.example.bankapp.controller

import org.example.bankapp.domain.dto.CommonResponse
import org.example.bankapp.domain.dto.PaymentCancelRequestDto
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
    fun processPayment(@RequestBody paymentRequestDto: PaymentRequestDto): ResponseEntity<CommonResponse> {
        val paymentResponseDto = placePaymentService.placePaymentEvent(paymentRequestDto)
        return ResponseEntity.ok(CommonResponse(data = paymentResponseDto))
    }

    @PostMapping("/cancel")
    fun cancelPayment(@RequestBody paymentCancelRequestDto: PaymentCancelRequestDto): ResponseEntity<CommonResponse> {
        val responseDto = placePaymentService.placePaymentCancelEvent(paymentCancelRequestDto)
        return ResponseEntity.ok(CommonResponse(data = responseDto))
    }
}