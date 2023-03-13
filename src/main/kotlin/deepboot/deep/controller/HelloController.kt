package deepboot.deep.controller

import deepboot.deep.service.HelloService
import org.springframework.context.ApplicationContext
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController(
    private val helloService: HelloService,
    applicationContext: ApplicationContext
) {
    init {
        println("application-context $applicationContext")
    }

    @GetMapping("/hello")
    fun hello(name: String?): String {
        return helloService.sayHello(requireNotNull(name))
    }
}