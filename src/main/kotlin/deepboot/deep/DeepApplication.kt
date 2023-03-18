package deepboot.deep

import deepboot.deep.annotation.MySpringBootApplication
import org.springframework.boot.runApplication


@MySpringBootApplication
class DeepApplication

fun main(args: Array<String>) {
    runApplication<DeepApplication>(*args)
}