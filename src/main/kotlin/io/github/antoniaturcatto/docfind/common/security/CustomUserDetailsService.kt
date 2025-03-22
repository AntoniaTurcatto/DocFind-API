package io.github.antoniaturcatto.docfind.common.security

import io.github.antoniaturcatto.docfind.service.UserService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class CustomUserDetailsService(private val userService: UserService) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        userService.findByLogin(username)?.let {
            return User.builder()
                .username(it.login)
                .password(it.pwd)
                .roles(*it.roles.toTypedArray())
                .build()
        }
        throw UsernameNotFoundException("User not found")
    }
}