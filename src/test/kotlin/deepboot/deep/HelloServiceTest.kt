package deepboot.deep

import deepboot.deep.controller.HelloDecorator
import deepboot.deep.service.SimpleHelloService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class HelloServiceTest {
    @Test
    fun simpleTest() {
        val helloService = SimpleHelloService()

        val ret = helloService.sayHello("Test")
        assertThat(ret).isEqualTo(ret)
    }

    @Test
    fun helloDecorator() {
        val lambdaDecorator = HelloDecorator { name -> "Hello, $name!" }
        assertThat(lambdaDecorator.sayHello("facebook")).isEqualTo("*Hello, facebook!*")
    }
}