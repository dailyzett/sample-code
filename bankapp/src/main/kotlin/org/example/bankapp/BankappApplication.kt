package org.example.bankapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BankappApplication

fun main(args: Array<String>) {
    runApplication<BankappApplication>(*args)
}
