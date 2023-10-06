package com.jun.controller

import com.jun.model.Cards
import com.jun.repository.CardsRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CardsController(
    private val cardsRepository: CardsRepository,
) {

    @GetMapping("/myCards")
    fun getCardDetails(@RequestParam id: Int): List<Cards> {
        return cardsRepository.findByCustomerId(id)
    }
}