package deepboot.config.autoconfig

import deepboot.config.MyConfigurationProperties
import org.springframework.stereotype.Component

@Component
@MyConfigurationProperties
class ServerProperties {

    var contextPath: String = ""

    var port: Int = 8080
}