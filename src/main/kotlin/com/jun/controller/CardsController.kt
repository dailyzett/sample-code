package com.jun.controller

import com.jun.model.Cards
import com.jun.repository.CardsRepository
import com.jun.repository.CustomerRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CardsController(
    private val cardsRepository: CardsRepository,
    private val customerRepository: CustomerRepository
) {

    @GetMapping("/myCards")
    fun getCardDetails(@RequestParam email: String): List<Cards> {
        val customers = customerRepository.findByEmail(email)
        if (customers.isNotEmpty()) {
            val cardsList = cardsRepository.findByCustomerId(customers[0].id)
            if (cardsList.isNotEmpty()) {
                return cardsList
            }
        }
        return listOf()
    }
}