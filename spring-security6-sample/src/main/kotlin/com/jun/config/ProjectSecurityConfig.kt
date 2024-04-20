package com.jun.config

import com.jun.filter.CsrfCookieFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler
import org.springframework.web.cors.CorsConfiguration

@Configuration
@EnableWebSecurity
class ProjectSecurityConfig {
    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val requestHandler = CsrfTokenRequestAttributeHandler()

        val jwtServer = JwtAuthenticationConverter()
        jwtServer.setJwtGrantedAuthoritiesConverter(KeyCloakRoleConverter())

        http.securityContext {
            it
                .requireExplicitSave(false)
        }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .cors {
                it.configurationSource {
                    val config = CorsConfiguration()
                    config.allowedOrigins = listOf("http://localhost:4200")
                    config.setAllowedMethods(listOf("*"))
                    config.allowCredentials = true
                    config.allowedHeaders = listOf("*")
                    config.maxAge = 3600L
                    config.exposedHeaders = listOf("Authorization")
                    return@configurationSource config
                }
            }.csrf {
                it
                    .csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/contact", "/register")
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            }
            .addFilterAfter(CsrfCookieFilter(), BasicAuthenticationFilter::class.java)
            .authorizeHttpRequests {
                it
                    .requestMatchers("/myAccount").hasRole("USER")
                    .requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
                    .requestMatchers("/myLoans").authenticated()
                    .requestMatchers("/myCards").hasRole("USER")
                    .requestMatchers("/user").authenticated()
                    .requestMatchers("/notices", "/contact", "/register").permitAll()
            }
            .oauth2ResourceServer {
                it.jwt { jwt ->
                    jwt.jwtAuthenticationConverter(jwtServer)
                    return@jwt
                }
            }

        requestHandler.setCsrfRequestAttributeName("_csrf")
        return http.build()
    }
}