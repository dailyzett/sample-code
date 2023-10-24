package com.jun.filter

import com.jun.constants.SecurityConstants
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.nio.charset.StandardCharsets
import java.util.*

class JwtTokenGeneratorFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val keys = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.toByteArray(StandardCharsets.UTF_8))
        val jwt: String = Jwts.builder().setIssuer("Eazy Bank").setSubject("JWT Token")
            .claim("username", authentication.name)
            .claim("authorities", populateAuthorities(authentication.authorities))
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + 30_000_000))
            .signWith(keys)
            .compact()
        response.setHeader(SecurityConstants.JWT_HEADER, jwt)

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.servletPath != "/user"
    }

    private fun populateAuthorities(collection: Collection<GrantedAuthority>): String {
        val authoritySets = HashSet<String>()
        for (authority in collection) {
            authoritySets.add(authority.authority)
        }
        return authoritySets.joinToString(",")
    }
}