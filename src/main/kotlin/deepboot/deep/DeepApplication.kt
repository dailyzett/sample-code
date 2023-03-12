package deepboot.deep

import deepboot.deep.controller.HelloController
import deepboot.deep.service.SimpleHelloService
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.context.support.registerBean
import org.springframework.web.context.support.GenericWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet

class DeepApplication

fun main(args: Array<String>) {
    val context = GenericWebApplicationContext()
    context.registerBean("helloController") { HelloController(SimpleHelloService()) }
    context.refresh()

    val serverFactory = TomcatServletWebServerFactory()
    val webServer = serverFactory.getWebServer(ServletContextInitializer {
        it.addServlet("dispatcherServlet", DispatcherServlet(context)).addMapping("/*")
    })

    webServer.start()
}
