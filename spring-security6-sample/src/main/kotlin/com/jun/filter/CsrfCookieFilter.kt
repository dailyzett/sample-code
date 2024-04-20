package com.jun.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.web.filter.OncePerRequestFilter

class CsrfCookieFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val csrfToken = request.getAttribute(CsrfToken::class.java.name) as CsrfToken?
        if (csrfToken?.headerName != null) {
            response.setHeader(csrfToken.headerName, csrfToken.token)
        }
        filterChain.doFilter(request, response)
    }
}