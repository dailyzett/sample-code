package deepboot.config.autoconfig

import deepboot.config.MyConfigurationProperties

@MyConfigurationProperties(prefix = "data")
class MyDataSourceProperties(
    var url: String = "",
    var username: String = "",
    var password: String = "",
    var driverClassName: String = "",
)
