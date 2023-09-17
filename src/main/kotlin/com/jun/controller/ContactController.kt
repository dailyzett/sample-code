package com.jun.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ContactController {

    @GetMapping("/contact")
    fun saveContactInquiryDetails(): String {
        return "Inquiry details saved to the DB"
    }
}