package deepboot.deep

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory

class DeepApplication

fun main(args: Array<String>) {
	val serverFactory = TomcatServletWebServerFactory()
	val webServer = serverFactory.getWebServer()
	webServer.start()
}
