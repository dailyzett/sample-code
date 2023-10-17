package com.jun

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@SpringBootApplication
@EnableWebSecurity(debug = true)
class EazyBankBackendApplication

fun main(args: Array<String>) {
    runApplication<EazyBankBackendApplication>(*args)
}
