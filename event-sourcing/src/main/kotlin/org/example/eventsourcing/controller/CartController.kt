package org.example.eventsourcing.controller

import org.example.eventsourcing.command.AddItem
import org.example.eventsourcing.command.ChangeQuantity
import org.example.eventsourcing.command.CreateCart
import org.example.eventsourcing.command.RemoveItem
import org.example.eventsourcing.service.CartService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cart")
class CartController(
    private val cartService: CartService,
) {
    @PostMapping("")
    fun saveCart(@RequestBody command: AddItem) {
        cartService.addItem(command)
    }

    @PutMapping(value = ["/{itemId}"], headers = ["command=ChangeQuantity"])
    fun changeCart(@PathVariable itemId: String, @RequestBody command: ChangeQuantity) {
        cartService.changeQuantity(command)
    }

    @DeleteMapping("")
    fun deleteCart(@RequestBody command: RemoveItem) {
        cartService.removeItem(command)
    }

    @PostMapping("/create")
    fun createCart(@RequestBody command: CreateCart) {
        cartService.createCart(command)
    }
}