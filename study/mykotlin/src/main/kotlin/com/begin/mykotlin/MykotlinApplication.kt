package com.begin.mykotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MykotlinApplication




fun main(args: Array<String>) {
    println("hello world!")
    runApplication<MykotlinApplication>(*args)
}
