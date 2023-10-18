package com.jun.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder

class AuthoritiesLoggingAfterFilter : Filter {


    private val log = LoggerFactory.getLogger(AuthoritiesLoggingAfterFilter::class.java)

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null) {
            log.info("User: ${authentication.name} Authorities: ${authentication.authorities}")
        } else {
            log.info("User: Anonymous")
        }

        chain?.doFilter(request, response)
    }
}