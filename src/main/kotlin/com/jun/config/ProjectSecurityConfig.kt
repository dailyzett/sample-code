package com.jun.config

import com.jun.filter.CsrfCookieFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler
import org.springframework.web.cors.CorsConfiguration

@Configuration
@EnableWebSecurity
class ProjectSecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val requestHandler = CsrfTokenRequestAttributeHandler()
        requestHandler.setCsrfRequestAttributeName("_csrf")
        http.securityContext { context: SecurityContextConfigurer<HttpSecurity?> ->
            context
                .requireExplicitSave(false)
        }
            .sessionManagement { session: SessionManagementConfigurer<HttpSecurity?> ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.ALWAYS
                )
            }
            .cors { corsCustomizer: CorsConfigurer<HttpSecurity?> ->
                corsCustomizer.configurationSource {
                    val config = CorsConfiguration()
                    config.allowedOrigins = listOf("http://localhost:4200")
                    config.setAllowedMethods(listOf("*"))
                    config.allowCredentials = true
                    config.allowedHeaders = listOf("*")
                    config.maxAge = 3600L
                    return@configurationSource config
                }
            }.csrf { csrf: CsrfConfigurer<HttpSecurity?> ->
                csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/contact", "/register")
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            }
            .addFilterAfter(CsrfCookieFilter(), BasicAuthenticationFilter::class.java)
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards", "/user").authenticated()
                    .requestMatchers("/notices", "/contact", "/register").permitAll()
            }
            .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
        return http.build()
    }


    /**
     * NoOpPasswordEncoder is deprecated. Use BCryptPasswordEncoder instead.
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}