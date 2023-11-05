package com.jun.config

import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt

@Configuration
class KeyCloakRoleConverter : Converter<Jwt, Collection<GrantedAuthority>> {
    override fun convert(jwt: Jwt): Collection<GrantedAuthority>? {
        val realmAccess = jwt.claims["realm_access"] as Map<*, *>?

        if (realmAccess == null || realmAccess.isEmpty()) {
            return arrayListOf()
        }

        return (realmAccess["roles"] as List<*>)
            .map { roleName -> "ROLE_$roleName" }
            .map(::SimpleGrantedAuthority)
            .toList()
    }

}