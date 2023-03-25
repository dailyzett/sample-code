package deepboot.config.autoconfig

import deepboot.config.MyConfigurationProperties

@MyConfigurationProperties(prefix = "server")
class ServerProperties {

    var contextPath: String = ""

    var port: Int = 8080
}