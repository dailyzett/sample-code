package deepboot.deep

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Service
@Primary
class HelloDecorator(
    private val helloService: HelloService,
) : HelloService {

    override fun sayHello(name: String): String {
        return "*" + helloService.sayHello(name) + "*"
    }
}