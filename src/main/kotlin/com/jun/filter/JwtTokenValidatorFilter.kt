package com.jun.filter

import com.jun.constants.SecurityConstants
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.nio.charset.StandardCharsets

class JwtTokenValidatorFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt: String? = request.getHeader(SecurityConstants.JWT_HEADER)
        if (jwt != null) {
            try {
                val keys = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.toByteArray(StandardCharsets.UTF_8))

                val claims = Jwts.parserBuilder()
                    .setSigningKey(keys)
                    .build()
                    .parseClaimsJws(jwt)
                    .body

                val username: String = claims["username"] as String
                val authorities: String = claims["authorities"] as String
                val auth = UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(authorities)
                )
                SecurityContextHolder.getContext().authentication = auth
            } catch (e: Exception) {
                throw BadCredentialsException("유효하지 않은 토큰")
            }
        }
        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.servletPath == "/user"
    }
}