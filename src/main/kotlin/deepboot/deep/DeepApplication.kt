package deepboot.deep

import deepboot.deep.controller.HelloController
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.registerBean
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class DeepApplication

fun main(args: Array<String>) {
    val context = GenericApplicationContext()
    context.registerBean("helloController") { HelloController() }
    context.refresh()

    val serverFactory = TomcatServletWebServerFactory()
    val webServer = serverFactory.getWebServer(ServletContextInitializer {
        it.addServlet("frontController", object : HttpServlet() {
            override fun service(req: HttpServletRequest?, resp: HttpServletResponse?) {

                if (req?.requestURI == "/hello" && req.method == HttpMethod.GET.name) {
                    resp?.apply {
                        val name = req.getParameter("name")
                        val helloController = context.getBean("helloController") as HelloController
                        val ret = helloController.hello(name)

                        resp.contentType = MediaType.TEXT_PLAIN_VALUE
                        resp.writer.println(ret)
                    }
                } else {
                    resp?.status = HttpStatus.NOT_FOUND.value()
                }
            }
        }).addMapping("/*")
    })

    webServer.start()
}
