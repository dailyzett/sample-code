package com.jun.filter

import com.jun.repository.CardsRepository
import jakarta.servlet.*
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.context.support.WebApplicationContextUtils

class AuthoritiesLoggingAfterFilter : Filter {


    private val log = LoggerFactory.getLogger(AuthoritiesLoggingAfterFilter::class.java)
    private var filterConfig: FilterConfig? = null

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null) {
            log.info("User: ${authentication.name} Authorities: ${authentication.authorities}")
        } else {
            log.info("User: Anonymous")
        }

        val webApplicationContext = request?.servletContext?.let {
            WebApplicationContextUtils.getWebApplicationContext(it)
        }

        webApplicationContext?.getBean(CardsRepository::class.java)?.let {
            it.findAll().forEach { card ->
                log.info("Card Details: $card")
            }
        }
        log.info("webApplicationContext: $webApplicationContext")
        log.info("filterConfig: $filterConfig")
        chain?.doFilter(request, response)
    }
}