package deepboot.deep

import org.springframework.stereotype.Service

interface HelloService {
    fun sayHello(name: String): String
    fun countOf(name: String): Int
}

@Service
class SimpleHelloService(
    private val helloRepository: HelloRepository,
) : HelloService {
    override fun sayHello(name: String): String {
        helloRepository.increaseCount(name)
        return "Hello $name"
    }

    override fun countOf(name: String): Int {
        return helloRepository.countOf(name)
    }
}