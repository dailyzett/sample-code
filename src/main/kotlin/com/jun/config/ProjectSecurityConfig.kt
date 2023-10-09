package com.jun.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class ProjectSecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests {
                it
                    .requestMatchers("/myAccount/**", "/myBalance/**", "/myLoans/**", "/myCards/**", "/user")
                    .authenticated()
                    .requestMatchers("/notices", "/contact", "/register").permitAll()
            }
            .formLogin {
                it
                    .permitAll()
            }
            .httpBasic {
                it
                    .disable()
            }
            .csrf {
                it
                    .disable()
            }
            .cors {}
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:4200")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        configuration.allowCredentials = true
        configuration.allowedHeaders = listOf("*")
        configuration.maxAge = 3600L
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
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