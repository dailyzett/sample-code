package deepboot.deep.controller

import deepboot.deep.service.HelloService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody


@RequestMapping("/hello")
class HelloController(
    private val helloService: HelloService
) {
    @GetMapping
    @ResponseBody
    fun hello(name: String?): String {
        return helloService.sayHello(requireNotNull(name))
    }
}