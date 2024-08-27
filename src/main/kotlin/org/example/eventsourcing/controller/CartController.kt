package org.example.eventsourcing.controller

import org.example.eventsourcing.command.CartRequestDto
import org.example.eventsourcing.service.CartService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CartController(
    private val cartService: CartService,
) {
    @PostMapping("/cart")
    fun saveCart(@RequestBody dto: CartRequestDto) {
        cartService.addItem(dto.cartId, dto.productNo, dto.productName, dto.quantity)
    }
}