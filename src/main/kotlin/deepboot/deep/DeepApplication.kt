package deepboot.deep

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet


@Configuration
@ComponentScan
class DeepApplication {
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

