package com.jun

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EazyBankBackendApplication

fun main(args: Array<String>) {
    runApplication<EazyBankBackendApplication>(*args)
}
