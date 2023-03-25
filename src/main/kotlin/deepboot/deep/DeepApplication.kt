package deepboot.deep

import deepboot.config.MySpringBootApplication
import org.springframework.boot.runApplication


@MySpringBootApplication
class DeepApplication

fun main(args: Array<String>) {
    runApplication<DeepApplication>(*args)
}