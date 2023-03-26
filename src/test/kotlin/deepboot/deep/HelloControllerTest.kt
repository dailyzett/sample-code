package deepboot.deep

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class HelloControllerTest {
    @Test
    fun test() {
        val helloController = HelloController(object : HelloService {
            override fun sayHello(name: String): String {
                return name
            }
        })

        val ret = helloController.hello("Test")
        assertThat(ret).isEqualTo("Test")
    }

    @Test
    fun exceptionTest() {
        val helloController = HelloController(object : HelloService {
            override fun sayHello(name: String): String {
                return name
            }
        })

        assertThatThrownBy { helloController.hello(null) }
            .isInstanceOf(IllegalArgumentException::class.java)

        assertThatThrownBy { helloController.hello("") }
            .isInstanceOf(IllegalArgumentException::class.java)
    }
}