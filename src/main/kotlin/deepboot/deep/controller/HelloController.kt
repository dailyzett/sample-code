package deepboot.deep.controller

import deepboot.deep.service.SimpleHelloService


class HelloController {
    fun hello(name: String?): String {
        val helloService = SimpleHelloService()
        return helloService.sayHello(requireNotNull(name))
    }
}