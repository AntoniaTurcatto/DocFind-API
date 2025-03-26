package io.github.antoniaturcatto.docfind.common.security

import io.github.antoniaturcatto.docfind.service.UserService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationProvider(private val userService: UserService,
                                   private val encoder: PasswordEncoder) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        val login = authentication.name
        val typedPwd = authentication.credentials.toString()

        userService.findByLogin(login)?.let {
            if(encoder.matches(typedPwd, it.pwd))
                return CustomAuthentication(it)
        }
        throw UsernameNotFoundException("Username or password not found")

    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken::class.java)
    }
}