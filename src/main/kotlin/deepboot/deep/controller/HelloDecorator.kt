package deepboot.deep.controller

import deepboot.deep.service.HelloService
import org.springframework.stereotype.Service

@Service
class HelloDecorator(
    private val helloService: (String) -> String,
) : HelloService {

    override fun sayHello(name: String): String {
        return "*" + helloService(name) + "*"
    }
}