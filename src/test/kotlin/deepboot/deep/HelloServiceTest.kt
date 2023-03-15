package deepboot.deep

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
}