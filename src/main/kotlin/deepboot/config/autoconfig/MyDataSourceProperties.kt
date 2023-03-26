package deepboot.config.autoconfig

import deepboot.config.MyConfigurationProperties

@MyConfigurationProperties(prefix = "data")
<<<<<<< HEAD
class MyDataSourceProperties(
    var url: String = "",
    var username: String = "",
    var password: String = "",
    var driverClassName: String = "",
)
=======
class MyDataSourceProperties {

    var url: String = ""

    var username: String = ""

    var password: String = ""

    var driverClassName: String = ""
}
>>>>>>> origin/main
