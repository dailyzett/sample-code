package deepboot.deep

import deepboot.deep.controller.HelloController
import deepboot.deep.service.HelloService
import deepboot.deep.service.SimpleHelloService
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet


@Configuration
class DeepApplication {
    @Bean
    fun helloController(helloService: HelloService) = HelloController(helloService)

    @Bean
    fun helloService() = SimpleHelloService()
}

fun main() {

    val applicationContext = object : AnnotationConfigWebApplicationContext() {

        @Suppress("ACCIDENTAL_OVERRIDE")
        override fun setClassLoader(classLoader: ClassLoader) {
            super.setClassLoader(classLoader)
        }

        override fun onRefresh() {
            super.onRefresh()

            val serverFactory = TomcatServletWebServerFactory()
            val webServer = serverFactory.getWebServer(ServletContextInitializer {
                it.addServlet("dispatcherServlet", DispatcherServlet(this))
                    .addMapping("/*")
            })
            webServer.start()
        }
    }

    applicationContext.register(DeepApplication::class.java)
    applicationContext.refresh()
}

