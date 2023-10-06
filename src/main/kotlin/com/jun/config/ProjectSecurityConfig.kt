package com.jun.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class ProjectSecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { request ->
                request
                    .requestMatchers("/myAccount/**", "/myBalance/**", "/myLoans/**", "/myCards/**", "/user")
                    .authenticated()
                    .requestMatchers("/notices", "/contact", "/register").permitAll()
            }
            .formLogin { formLogin ->
                formLogin
                    .permitAll()
            }
            .httpBasic { httpBasic ->
                httpBasic
                    .disable()
            }
            .csrf { csrf ->
                csrf
                    .disable()
            }
        return http.build()
    }

    /*@Bean
    fun userDetailService(dataSource: DataSource): JdbcUserDetailsManager {
        return JdbcUserDetailsManager(dataSource)
    }*/

    /**
     * NoOpPasswordEncoder is deprecated. Use BCryptPasswordEncoder instead.
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    /*    @Bean
        fun userDetamilService(): InMemoryUserDetailsManager {
             InMemoryUserDetailsManager is deprecated. Use User.withDefaultPasswordEncoder() instead.
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
        }*/
}