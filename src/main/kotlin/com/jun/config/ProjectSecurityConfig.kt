package com.jun.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration(proxyBeanMethods = false)
class ProjectSecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { request ->
            request
                .requestMatchers("/myAccount/**", "/myBalance/**", "/myLonas/**", "/myCards/**").authenticated()
                .requestMatchers("/notices", "/contact").permitAll()
        }
            .formLogin { formLogin ->
                formLogin
                    .permitAll()
            }
            .httpBasic { httpBasic ->
                httpBasic
                    .disable()
            }
        return http.build()
    }

    @Bean
    fun userDetamilService(): InMemoryUserDetailsManager {
        /* InMemoryUserDetailsManager is deprecated. Use User.withDefaultPasswordEncoder() instead. */
        /*val admin = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("12345")
            .roles("ADMIN")
            .build()

        val genericMember = User.withDefaultPasswordEncoder()
            .username("genericMember")
            .password("12345")
            .roles("read")
            .build()
        return InMemoryUserDetailsManager(admin, genericMember)*/

        val admin = User
            .withUsername("admin")
            .password("12345")
            .roles("ADMIN")
            .build()

        val genericMember = User
            .withUsername("genericMember")
            .password("12345")
            .roles("read")
            .build()
        return InMemoryUserDetailsManager(admin, genericMember)
    }

    /**
     * NoOpPasswordEncoder is deprecated. Use BCryptPasswordEncoder instead.
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance()
    }
}