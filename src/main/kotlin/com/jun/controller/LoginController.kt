package com.jun.controller

import com.jun.model.Customer
import com.jun.repository.CustomerRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(
    private val customerRepository: CustomerRepository
) {

    @PostMapping("/register")
    fun registerUser(@RequestBody customer: Customer): ResponseEntity<String>? {
        var response: ResponseEntity<String>? = null
        try {
            val savedCustomer = customerRepository.save(customer)
            if (savedCustomer.id!! > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully")
            }
        } catch (e: Exception) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User creation failed")
        }

        return response
    }
}