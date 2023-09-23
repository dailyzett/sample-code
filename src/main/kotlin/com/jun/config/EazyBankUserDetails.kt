package com.jun.config

import com.jun.repository.CustomerRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class EazyBankUserDetails(
    private val customerRepository: CustomerRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        var userName: String = ""
        var password: String = ""
        val customerList = customerRepository.findByEmail(username)
        if (customerList.isEmpty()) throw UsernameNotFoundException("User not found")

        userName = customerList[0].email!!
        password = customerList[0].pwd!!

        val authorities = mutableListOf<GrantedAuthority>()
        authorities.add(SimpleGrantedAuthority(customerList[0].role!!))
        return User(userName, password, authorities)
    }
}