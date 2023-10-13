package com.jun.config

import com.jun.model.Authority
import com.jun.repository.CustomerRepository
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class EazyBankUsernamePwdAuthenticationProvider(
    private val customerRepository: CustomerRepository,
    private val passwordEncoder: PasswordEncoder
) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication {
        val username = authentication?.name
        val pwd = authentication?.credentials.toString()
        val customerList = customerRepository.findByEmail(username!!)
        if (customerList.isNotEmpty()) {
            if (passwordEncoder.matches(pwd, customerList[0].pwd)) {
                return UsernamePasswordAuthenticationToken(
                    username,
                    pwd,
                    getGrantedAuthority(customerList[0].authorities!!)
                )
            } else {
                throw BadCredentialsException("Invalid password")
            }
        } else {
            throw BadCredentialsException("User not found")
        }
    }

    fun getGrantedAuthority(authroties: Set<Authority>): List<GrantedAuthority> {
        val authorities = mutableListOf<GrantedAuthority>()
        authroties.forEach {
            authorities.add(SimpleGrantedAuthority(it.name))
        }
        return authorities
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication!!)
    }
}