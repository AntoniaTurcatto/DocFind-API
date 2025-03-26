package io.github.antoniaturcatto.docfind.common.security

import io.github.antoniaturcatto.docfind.common.model.User
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class CustomAuthentication(val user: User): Authentication {
    override fun getName(): String {
        return user.login
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return user.roles.stream().map { SimpleGrantedAuthority(it) }.toList()
    }

    override fun getCredentials(): Any? {
        return null
    }

    override fun getDetails(): Any {
        return user
    }

    override fun getPrincipal(): Any {
        return user
    }

    override fun isAuthenticated(): Boolean {
        return true
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {

    }
}