package com.jun

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
class EazyBankBackendApplication

fun main(args: Array<String>) {
    runApplication<EazyBankBackendApplication>(*args)
}
