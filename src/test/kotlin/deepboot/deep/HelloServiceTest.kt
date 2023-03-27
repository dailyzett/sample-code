package deepboot.deep

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
@Test
annotation class UnitTest {

}

class HelloServiceTest {
    @UnitTest
    fun simpleTest() {
        val helloService = SimpleHelloService(getHelloRepository())
        val ret = helloService.sayHello("Test")
        assertThat(ret).isEqualTo("Hello Test")
    }

    private fun getHelloRepository() = object : HelloRepository {
        override fun findHello(name: String): Hello? {
            return null
        }

        override fun increaseCount(name: String) {
        }
    }
}
