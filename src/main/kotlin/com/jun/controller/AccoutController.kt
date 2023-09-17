package com.jun.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AccoutController {

    @GetMapping("/myAccount")
    fun getAccountDetails(): String {
        return "Here are the account details from the DB"
    }
}