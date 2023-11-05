package com.jun.controller

import com.jun.model.Customer
import com.jun.repository.CustomerRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class LoginController(
    private val customerRepository: CustomerRepository,
) {
    
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