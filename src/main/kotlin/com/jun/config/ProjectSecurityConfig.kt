package com.jun.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
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
        val admin = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("12345")
            .roles("ADMIN")
            .build()

        val genericMember = User.withDefaultPasswordEncoder()
            .username("genericMember")
            .password("12345")
            .roles("read")
            .build()
        return InMemoryUserDetailsManager(admin, genericMember)
    }
}