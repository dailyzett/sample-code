package deepboot.deep

import deepboot.deep.annotation.MySpringBootAnnotation
import org.springframework.boot.runApplication


@MySpringBootAnnotation
class DeepApplication

fun main(args: Array<String>) {
    runApplication<DeepApplication>(*args)
}

