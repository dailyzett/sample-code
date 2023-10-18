package com.jun.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import org.slf4j.LoggerFactory

class AuthoritiesLoggingAtFilter : Filter {

    private val log = LoggerFactory.getLogger(AuthoritiesLoggingAfterFilter::class.java)
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        log.info("Authentication Validation is in Progress")
        chain?.doFilter(request, response)
    }

}