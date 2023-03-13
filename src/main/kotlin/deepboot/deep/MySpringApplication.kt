package deepboot.deep

import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.boot.web.servlet.server.ServletWebServerFactory
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet
import kotlin.reflect.KClass

class MySpringApplication(
) {
    companion object {
        fun run(
            applicationClass: KClass<*>,
            args: Array<String>,
        ) {

            val applicationContext = AnnotationConfigWebApplicationContext()
            applicationContext.register(applicationClass.java)
            applicationContext.refresh()

            val serverFactory = applicationContext.getBean(ServletWebServerFactory::class.java)
            val dispatcherServlet = applicationContext.getBean(DispatcherServlet::class.java)

            val webServer = serverFactory.getWebServer(ServletContextInitializer {
                it.addServlet("dispatcherServlet", DispatcherServlet(applicationContext)).addMapping("/*")
            })

            webServer.start()
        }
    }
}