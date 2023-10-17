package com.jun.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.util.StringUtils
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*

class RequestValidationBeforeFilter : Filter {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val req = request as HttpServletRequest
        val res = response as HttpServletResponse
        var header = req.getHeader(AUTHORIZATION)

        if (header != null) {
            header = header.trim()
            if (StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SHCEME_BASIC)) {
                val base64Token = header.substring(6).toByteArray(Charsets.UTF_8)
                lateinit var decoded: ByteArray
                try {
                    decoded = Base64.getDecoder().decode(base64Token)
                    val token = String(decoded, credentialsCharset)
                    val delim = token.indexOf(":")
                    if (delim == -1) {
                        throw Exception("Invalid basic authentication token")
                    }
                    val email = token.substring(0, delim)
                    if (email.lowercase(Locale.KOREA).contains("test")) {
                        res.status = SC_BAD_REQUEST
                        return
                    }
                } catch (e: IllegalArgumentException) {
                    throw Exception("Failed to decode basic authentication token")
                }
            }
        }
        chain?.doFilter(request, response)
    }

    companion object {
        const val AUTHENTICATION_SHCEME_BASIC = "Basic"
        val credentialsCharset: Charset = StandardCharsets.UTF_8
    }
}