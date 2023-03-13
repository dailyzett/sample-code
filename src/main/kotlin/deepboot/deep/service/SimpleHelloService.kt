package deepboot.deep.service

import deepboot.deep.annotation.MyAnnotation

interface HelloService {
    fun sayHello(name: String): String
}

@MyAnnotation
class SimpleHelloService : HelloService {
    override fun sayHello(name: String): String {
        return "Hello $name"
    }
}