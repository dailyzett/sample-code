package deepboot.deep.config.autoconfig

import deepboot.deep.annotation.MyAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.DispatcherServlet

@MyAutoConfiguration
class DispatcherServletConfig {
    @Bean
    fun dispatcherServlet() = DispatcherServlet()
}