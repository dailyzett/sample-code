package com.jun.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CardsController {

    @GetMapping("/myCards")
    fun getCardDetails(): String {
        return "Here are the card details from the DB"
    }
}