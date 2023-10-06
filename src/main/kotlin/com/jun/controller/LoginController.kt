package com.jun.controller

import com.jun.model.Customer
import com.jun.repository.CustomerRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class LoginController(
    private val customerRepository: CustomerRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/register")
    fun registerUser(@RequestBody customer: Customer): ResponseEntity<String>? {
        var response: ResponseEntity<String>? = null
        try {
            val hashPwd = passwordEncoder.encode(customer.pwd)
            customer.pwd = hashPwd
            customer.createDt = Date(System.currentTimeMillis())
            val savedCustomer = customerRepository.save(customer)
            if (savedCustomer.id > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully")
            }
        } catch (e: Exception) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User creation failed")
        }

        return response
    }

    @RequestMapping("/user")
    fun getUserDetailsAfterLogin(authentication: Authentication): Customer? {
        val customers = customerRepository.findByEmail(authentication.name)
        return if (customers.isNotEmpty()) {
            customers[0]
        } else {
            null
        }
    }
}